package com.java.sample.ezpos.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.java.sample.ezpos.config.GlobalConfiguration;
import com.java.sample.ezpos.constant.APICodes;
import com.java.sample.ezpos.constant.IPOS;
import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.model.APILog;
import com.java.sample.ezpos.service.HeaderFilterService;
import com.java.sample.ezpos.util.DateUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@PropertySource("classpath:application.properties")
public class HeaderFilterServiceImpl implements HeaderFilterService {

	@Value("${timevalidation.interval}")
	private String timeValidationInterval;

	@Override
	public void timeIntervalValidation(APILog objApiLog) {

		log.info("in timeIntervalValidation");

		try {

			if (!GlobalConfiguration.timeIntervalSkip.contains(objApiLog.getReqUrl())) {

				log.info("objApiLog.getReqTime() --> '" + objApiLog.getReqTime() + "'");
				log.info("objApiLog.getServerTime() --> '" + objApiLog.getServerTime() + "'");

				if (objApiLog.getReqTime() == null || "".equals(objApiLog.getReqTime())) {
					objApiLog.setApiCode(APICodes.REQ_TIME_NOT.code());
					throw new EzposException(HttpStatus.UNAUTHORIZED, "", APICodes.REQ_TIME_NOT);
				} else {

					Date reqDateTime = DateUtil.convertAPIDateStringToDate(objApiLog.getReqTime());

					if (reqDateTime == null) {
						objApiLog.setApiCode(APICodes.TIME_VALIDATION.code());
						throw new EzposException(HttpStatus.UNAUTHORIZED, "", APICodes.REQ_TIME_FORMAT_ISSUE);
					}

					long diffMin = DateUtil.calDifferenceInMin(reqDateTime, DateUtil.convertAPIDateStringToDate(objApiLog.getServerTime()));
					log.info("diffMin --> '" + diffMin + "'");

					diffMin = Math.abs(diffMin);
					log.info("diffMin after abs --> '" + diffMin + "'");
					log.info("timeValidationInterval --> '" + Long.valueOf(timeValidationInterval).longValue() + "'");

					if (diffMin > Long.valueOf(timeValidationInterval).longValue()) {
						objApiLog.setApiCode(APICodes.TIME_VALIDATION.code());
						throw new EzposException(HttpStatus.UNAUTHORIZED, "", APICodes.TIME_VALIDATION);
					}

				}

			}

		} catch (EzposException e) {
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new EzposException(HttpStatus.INTERNAL_SERVER_ERROR, "Error on timeIntervalValidation", APICodes.VALIDATION_ERR, e);

		}

	}

	@Override
	public void headerAndPayloadTimeValidation(APILog objApiLog, String reqPayLoad) {

		try {

			if (!GlobalConfiguration.timeIntervalSkip.contains(objApiLog.getReqUrl())) {

				String reqDateTimeOnPayLoad = "";

				if (reqPayLoad != null && !"".equals(reqPayLoad)) {

					try {

						JSONObject jsonReqObject = new JSONObject(reqPayLoad);
						reqDateTimeOnPayLoad = jsonReqObject.getString(IPOS.IPayLoad.REQ_DATETIME);

						log.info("reqDateTimeOnPayLoad in headerAndPayloadTimeValidation :: " + reqDateTimeOnPayLoad);
						log.info("objApiLog.getReqTime() in headerAndPayloadTimeValidation :: " + objApiLog.getReqTime());

						if (!objApiLog.getReqTime().equals(reqDateTimeOnPayLoad)) {
							throw new EzposException(HttpStatus.UNAUTHORIZED, "", APICodes.HEADER_PAYLOAD_TIME_DIFF);
						}

					} catch (JSONException e) {
						log.info("reqDateTimeOnPayLoad is not present on request payload. Field Name might be wrong");
						throw new EzposException(HttpStatus.UNAUTHORIZED, "", APICodes.REQDAETIME_NOT_AVAILABLE);
					}

				} else {
					log.error("reqPayLoad is null or Empty");
				}
			}

		} catch (EzposException e) {
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new EzposException(HttpStatus.INTERNAL_SERVER_ERROR, "Error on timeIntervalValidation", APICodes.VALIDATION_ERR, e);

		}

	}

	@Override
	public void dateTimeFormatValidation(APILog objApiLog) {
		try {

			if (!GlobalConfiguration.timeIntervalSkip.contains(objApiLog.getReqUrl())) {

				if (objApiLog.getReqTime().length() != IPOS.IDateTime.LENGTH) {

					// set the Max length
					if (objApiLog.getReqTime().length() > IPOS.IDateTime.LENGTH) {
						objApiLog.setReqTime(objApiLog.getReqTime().substring(0, IPOS.IDateTime.LENGTH));
					}

					objApiLog.setApiCode(APICodes.REQ_TIME_FORMAT_ISSUE.code());
					throw new EzposException(HttpStatus.UNAUTHORIZED, "", APICodes.REQ_TIME_FORMAT_ISSUE);
				}

				DateFormat sdf = new SimpleDateFormat(IPOS.IDateTime.FORMAT);
				sdf.setLenient(false);
				try {
					sdf.parse(objApiLog.getReqTime());
				} catch (ParseException e) {
					objApiLog.setApiCode(APICodes.REQ_TIME_FORMAT_ISSUE.code());
					throw new EzposException(HttpStatus.UNAUTHORIZED, "", APICodes.REQ_TIME_FORMAT_ISSUE);
				}

			}

		} catch (EzposException e) {
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new EzposException(HttpStatus.INTERNAL_SERVER_ERROR, "Error on dateTimeValidation", APICodes.VALIDATION_ERR, e);

		}

	}

}
