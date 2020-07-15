package com.java.sample.ezpos.mapper;

import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.model.APILog;

public interface APILogMapper {

	int insert(APILog apiLog) throws EzposException;

}
