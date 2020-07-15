package com.java.sample.ezpos.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.java.sample.ezpos.beans.res.CoreAPIErrorRes;
import com.java.sample.ezpos.config.StaticClass;
import com.java.sample.ezpos.constant.APICodes;
import com.java.sample.ezpos.constant.IMessages;
import com.java.sample.ezpos.constant.IPOS;
import com.java.sample.ezpos.model.APILog;

import lombok.extern.log4j.Log4j2;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
@Log4j2
public class EzposExceprionHandler extends ResponseEntityExceptionHandler {

	/*
	 * TranxExceptionDBOperation tranxDBOperation;
	 * 
	 * @Autowired public void setTranxDBOperation(TranxExceptionDBOperation
	 * tranxDBOperation) { this.tranxDBOperation = tranxDBOperation; }
	 * 
	 * TranxQRMappingExceptionDBOperation tranxQRMappingDBOperation;
	 * 
	 * @Autowired public void
	 * setTranxQRMappingDBOperation(TranxQRMappingExceptionDBOperation
	 * tranxQRMappingDBOperation) { this.tranxQRMappingDBOperation =
	 * tranxQRMappingDBOperation; }
	 */

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

		HashMap errorResponse = getErrorMap(ex);

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EzposException.class)
	public final ResponseEntity<Object> handleEzposException(EzposException cacisEx, WebRequest request) {

		HashMap errorResponse = getErrorMap(cacisEx);

		// call the TranxLog insert if present
		/*
		 * TranxLog tranxLog = StaticClass.getThreadLocalTranxLog().get(); if (tranxLog
		 * != null && tranxLog.getIsInsert()) {
		 * tranxDBOperation.logTranxIntoDB(tranxLog, cacisEx); }
		 */

		return new ResponseEntity<>(errorResponse, cacisEx.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		EzposException cacisEx = new EzposException(HttpStatus.BAD_REQUEST, IMessages.IError.VALIDATION_FAILED, APICodes.VALIDATION_ERR, errors);

		HashMap errorResponse = getErrorMap(cacisEx);

		return new ResponseEntity<>(errorResponse, cacisEx.getStatus());

	}

	private HashMap getErrorMap(Object ex) {

		HashMap result = new HashMap();
		String errorDetail = "";

		CoreAPIErrorRes objCoreAPIErrorRes = new CoreAPIErrorRes();
		objCoreAPIErrorRes.getCoreErrorResponse();

		if (ex instanceof EzposException) {

			objCoreAPIErrorRes = new CoreAPIErrorRes();
			objCoreAPIErrorRes.getCoreErrorResponse((EzposException) ex);

			if (((EzposException) ex).getE() != null) {

				errorDetail = ((EzposException) ex).getE().getStackTrace()[0] + "-" + ((EzposException) ex).getE().getMessage();
				log.error("errorDetail :: " + errorDetail);
				log.error("errorDetail Length :: " + errorDetail.length());

				if (errorDetail.length() > 200) {
					errorDetail = errorDetail.substring(0, 200);
				}
			}

		}

		APILog apiLog = StaticClass.getThreadLocalApi().get();
		if (apiLog == null)
			apiLog = new APILog();
		apiLog.setApiCode(objCoreAPIErrorRes.getResponseCode());

		if (!"".equals(errorDetail)) {
			apiLog.setErrorDetail(errorDetail);
		}

		result.put(IPOS.IKey.IResponse.RESPONSE, objCoreAPIErrorRes);

		return result;

	}

}
