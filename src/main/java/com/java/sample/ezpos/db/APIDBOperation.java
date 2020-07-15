package com.java.sample.ezpos.db;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.java.sample.ezpos.config.GlobalConfiguration;
import com.java.sample.ezpos.config.MyBatisDBService;
import com.java.sample.ezpos.constant.APICodes;
import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.mapper.APILogMapper;
import com.java.sample.ezpos.model.APILog;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class APIDBOperation {

	@Autowired
	private MyBatisDBService datasourceMybatis;

	public void logAPIIntoDB(APILog objApiLog) {

		log.error("In logAPIIntoDB");

		try (SqlSession sqlSession = datasourceMybatis.sqlSessionFactory(datasourceMybatis.datasource()).openSession(false);) {

			String isReqPayLoadSkip = "N";
			if (GlobalConfiguration.reqPayLoadOmitUrls.contains(objApiLog.getReqUrl())) {
				objApiLog.setIsReqPayLoadSkip("Y");
				objApiLog.setReqPayLoad("");
			}

			String isResPayLoadSkip = "N";
			if (GlobalConfiguration.resPayLoadOmitUrls.contains(objApiLog.getReqUrl()) && "200".equals(objApiLog.getResponse())) {
				objApiLog.setIsResPayLoadSkip("Y");
				objApiLog.setResPayLoad("");
			}

			objApiLog.setUniqueId(ThreadContext.get("id"));
			objApiLog.setDateTime(new Date());
			objApiLog.setIsResPayLoadSkip(isResPayLoadSkip);
			objApiLog.setIsReqPayLoadSkip(isReqPayLoadSkip);

			APILogMapper apiLoggerMapper = sqlSession.getMapper(APILogMapper.class);
			apiLoggerMapper.insert(objApiLog);

			sqlSession.commit();

		} catch (PersistenceException e) {
			final Throwable cause = e.getCause();
			throw new EzposException(HttpStatus.INTERNAL_SERVER_ERROR, "", APICodes.APPL_ERR, new Exception(cause.toString()));
		} catch (Exception e) {

			log.warn("API Log DB Insert Failed");
			log.warn(e);

			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.warn(errors.toString());
			// throw Fatal Exception and handle it separately and send email
		}

		log.error("Out logAPIIntoDB");

	}

}
