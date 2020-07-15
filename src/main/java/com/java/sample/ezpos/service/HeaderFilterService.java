package com.java.sample.ezpos.service;

import com.java.sample.ezpos.model.APILog;

public interface HeaderFilterService {

	public void dateTimeFormatValidation(APILog objApiLog);

	public void timeIntervalValidation(APILog objApiLog);

	public void headerAndPayloadTimeValidation(APILog objApiLog, String reqPayLoad);

}
