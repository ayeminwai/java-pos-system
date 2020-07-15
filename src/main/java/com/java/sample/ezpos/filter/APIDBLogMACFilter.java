package com.java.sample.ezpos.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.java.sample.ezpos.config.GlobalConfiguration;
import com.java.sample.ezpos.config.MyRequestWrapper;
import com.java.sample.ezpos.config.StaticClass;
import com.java.sample.ezpos.constant.IPOS;
import com.java.sample.ezpos.db.APIDBOperation;
import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.geo.GeoService;
import com.java.sample.ezpos.model.APILog;
import com.java.sample.ezpos.service.HeaderFilterService;
import com.java.sample.ezpos.util.DateTimeUtil;
import com.java.sample.ezpos.util.DateUtil;
import com.java.sample.ezpos.util.ServiceUtil;

import lombok.extern.log4j.Log4j2;

@Component
@Order(3)
@Log4j2
public class APIDBLogMACFilter extends ServiceUtil implements Filter {

	@Autowired
	APIDBOperation apiDBOperation;

	@Autowired
	HeaderFilterService headerFilterService;

	@Autowired
	GeoService geoService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		log.info("In APIDBLogFilter doFilter  Order 3");

		final long start = System.nanoTime();

		MyRequestWrapper requestWrapper = new MyRequestWrapper((HttpServletRequest) request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

		APILog objApiLog = null;

		try {

			// get the Request Body
			String reqPayLoad = requestWrapper.getBody();

			// inialize the DB model and assign the data
			objApiLog = new APILog();

			// set the API log into Thread local
			StaticClass.getThreadLocalApi().set(objApiLog);

			String remoteIp = geoService.getClientIP((HttpServletRequest) request);
			String remoteIpLocation = "{}";
			try {
				remoteIpLocation = geoService.getLocationInfo(remoteIp);
			} catch (Exception e) {
				log.error(e);
				log.info("Invalid Geo Locatoin Info");
			}
			
			objApiLog.setReqTime(requestWrapper.getHeader(IPOS.IHeader.REQ_DATETIME));
			objApiLog.setServerTime(DateUtil.getServerTime());
			objApiLog.setReqMethod(requestWrapper.getMethod());
			objApiLog.setReqUrl(requestWrapper.getServletPath());
			objApiLog.setReqPayLoad(reqPayLoad);
			objApiLog.setRemoteIp(remoteIp);
			objApiLog.setRemoteIpLocation(remoteIpLocation);
			
			// datetime format validation
			headerFilterService.dateTimeFormatValidation(objApiLog);

			// interval calculation of request initiated time and server time
			headerFilterService.timeIntervalValidation(objApiLog);

			// validate the reqDatetime on header and payload
			headerFilterService.headerAndPayloadTimeValidation(objApiLog, reqPayLoad);

			chain.doFilter(requestWrapper, responseWrapper);

		} catch (EzposException e) {

			// get the response details
			getFilterErrorResponse(responseWrapper, e, objApiLog);

		} finally {

			// get the Response Body
			String resPayLoad = new String(responseWrapper.getContentAsByteArray());

			final long end = System.nanoTime();

			DateTimeUtil obj = new DateTimeUtil();

			long nanoSec = end - start;
			long milliSec = obj.nanoSecToMilliSec(nanoSec);
			long sec = obj.nanoSecToSec(nanoSec);

			log.info("response Time in milli Secs in APIDBLogMACFilter :: " + milliSec);
			log.info("response Time in Secs in APIDBLogMACFilter :: " + sec);

			String resStatus = "" + responseWrapper.getStatus();
			log.info("resStatus in APIDBLogMACFilter :: " + resStatus);

			// process time
			objApiLog.setProcessTime(sec);
			objApiLog.setProcessTimeMs(milliSec);
			objApiLog.setResPayLoad(resPayLoad);
			objApiLog.setResponse(resStatus);
			objApiLog.setRemoteAddress(requestWrapper.getHeader("host"));
			objApiLog.setSessionId(requestWrapper.getSession().getId());

			// get the response time from response payload and set to header

			String resTime = DateUtil.getServerTime();
			try {
				JSONObject jsonResObject = new JSONObject(resPayLoad);
				resTime = jsonResObject.getString("resDateTime");
			} catch (JSONException e) {
				log.error("Exception on resDateTime getting from res ponse payload");
			}

			responseWrapper.setHeader(IPOS.IHeader.RES_DATETIME, resTime);

			objApiLog.setResTime(resTime);

			// API Logging into DB
			apiDBOperation.logAPIIntoDB(objApiLog);

			// Do not forget this line after reading response content or actual response
			// will be empty!
			responseWrapper.copyBodyToResponse();

			// remove the object from thread local
			StaticClass.getThreadLocalApi().remove();
		}

		log.info("End APIDBLogFilter doFilter");

	}

}
