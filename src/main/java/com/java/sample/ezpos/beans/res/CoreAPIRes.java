package com.java.sample.ezpos.beans.res;

import com.java.sample.ezpos.constant.APICodes;
import com.java.sample.ezpos.util.DateUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoreAPIRes {

	@ApiModelProperty(notes = "API Response Code", example = "000", position = 1)
	String responseCode;

	@ApiModelProperty(notes = "API Response Text", example = "Successfully Processed", position = 2)
	String responseText;

	@ApiModelProperty(notes = "Response Date Time", example = "30082019180900", position = 3)
	String resDateTime;

	public void getCoreSuccessResponse() {
		this.responseCode = APICodes.SUCCESS.code();
		this.responseText = APICodes.SUCCESS.getReasonPhrase();
		this.resDateTime = DateUtil.getServerTime();
	}

	public void getCoreErrorResponse() {
		this.responseCode = APICodes.INTERNAL_SERVER_ERROR.code();
		this.responseText = APICodes.INTERNAL_SERVER_ERROR.getReasonPhrase();
		this.resDateTime = DateUtil.getServerTime();
	}

	public CoreAPIRes(String responseCode, String responseText) {
		this.responseCode = responseCode;
		this.responseText = responseText;
		this.resDateTime = DateUtil.getServerTime();
	}

	public CoreAPIRes() {
	}

}
