package com.java.sample.ezpos.util;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.sample.ezpos.beans.res.CoreAPIRes;
import com.java.sample.ezpos.config.StaticClass;
import com.java.sample.ezpos.constant.IPOS;
import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.model.APILog;

import lombok.extern.log4j.Log4j2;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Log4j2
public class ServiceUtil {

	@Autowired
	ObjectMapper objectMapper;

	public void getFilterErrorResponse(ContentCachingResponseWrapper responseWrapper, EzposException e, APILog objApiLog) throws IOException {

		responseWrapper.setHeader("Content-Type", "application/json");

		// add the server response time
		String resTime = DateUtil.getServerTime();
		responseWrapper.setHeader(IPOS.IHeader.RES_DATETIME, resTime);

		objApiLog.setResTime(resTime);
		objApiLog.setErrorDetail(e.getMessage());

		responseWrapper.setContentType("application/json");
		responseWrapper.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		responseWrapper.getWriter().write(getFilterErrorMap(e).toString());

		APILog apiLog = StaticClass.getThreadLocalApi().get();
		apiLog.setApiCode(e.getErrorCode().code());

		responseWrapper.getWriter().flush();

	}

	public String getFilterErrorMap(EzposException cacisEx) {

		String res = "{\"responseCode\":\"401\",\"responseText\":\"Internal Server Error\"}";

		try {
			HashMap result = new HashMap();
			CoreAPIRes objError = new CoreAPIRes(cacisEx.getErrorCode().code(), cacisEx.getErrorCode().getReasonPhrase());
			result.put(IPOS.IKey.IResponse.RESPONSE, objError);

			log.error("getFilterErrorMap Error :: " + cacisEx.getReason());

			res = objectMapper.writeValueAsString(result);

		} catch (Exception e) {
			log.error("Exception on getFilterErrorMap :: " + e);
		}

		return res;

	}
}
