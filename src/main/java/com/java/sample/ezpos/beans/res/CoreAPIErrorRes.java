package com.java.sample.ezpos.beans.res;

import java.util.Map;

import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoreAPIErrorRes extends CoreAPIRes {

	Map<String, String> errors;

	public CoreAPIErrorRes() {
	}

	public CoreAPIErrorRes(String responseCode, String responseText) {
		super(responseCode, responseText);
	}

	public CoreAPIErrorRes(Map<String, String> errors) {
		this.errors = errors;
	}

	public void getCoreErrorResponse(EzposException cacisEx) {

		this.responseCode = "" + cacisEx.getErrorCode().code();
		this.responseText = cacisEx.getErrorCode().getReasonPhrase();
		this.resDateTime = DateUtil.getServerTime();

		if (cacisEx.getErrors() != null && !cacisEx.getErrors().isEmpty()) {
			this.errors = cacisEx.getErrors();
		}

	}

}
