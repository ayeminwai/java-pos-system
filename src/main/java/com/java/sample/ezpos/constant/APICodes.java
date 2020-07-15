package com.java.sample.ezpos.constant;
public enum APICodes {
	
	/********** Common Success Codes *********/
	SUCCESS						("000","00","Successfully Processed"),
	
	/********** Common Codes  101 - 199 *********/
	INTERNAL_SERVER_ERROR		("101","05","Internal Server Error"),
	APPL_ERR					("102","05","Application Error"),
	VALIDATION_ERR				("103","05","Validation Error"),
	API_CALL_ERR				("104","05","API Calling Error"),
	TOKEN_ERROR					("105","05","OAuth Token Error"),
	DATA_NOT_AVAILABLE			("106","05","Requested Data Not Available"),
	REQ_TIME_NOT				("107","05","Req Time is not available on Request"),
	TIME_VALIDATION				("108","05","Time Interval Validation Error"),
	REQ_TIME_FORMAT_ISSUE		("109","05","Format of Request Datetime on Header is Wrong"),
	HEADER_PAYLOAD_TIME_DIFF	("110","05","Header and Payload Req. Datetime different"),
	REQDAETIME_NOT_AVAILABLE	("111","05","Req. Datetime not avalibale on Request"),

	/********** Specific Codes for ezpos 201 - 299 *********/
	DUPLICATE_RECORD			("201","05","Duplicate Record"),
	PRODUCT_NOT_AVAIL			("202","05","Product Not Available"),
	PRODUCT_NOT_ACTIVE			("203","05","Product is NOT Active"),
	
	/********** Specific Codes for Transactions 301 -399 *********/
	NO_RECORDS_FOUND		("301","05","No records found."),
	INVALID_AMT				("302","05","Invalid Amount"),
	DUP_TRANX				("303","05","Duplicate Request."),
	INVALID_TRANX_TYPE		("304","05","Transaction not permitted."),
	REGISTERED				("305","05","Account is already registered."),
	INVALID_PASSCODE		("306","05","Invalid Password"),
	INVALID_USER			("307","05","Invalid user account."),
	TRANX_NOT_FOUND			("308","05","Transaction Not found"),
	NO_REFUND_TRANX_SETTLED	("309","05","Transaction has been settled. Refund not allowed"),
	REFUNDED_TRANX			("310","05","Transaction has been refunded");
	
	
	private final String value;
	private final String responseCode;
	private final String reasonPhrase;

	APICodes(String value, String responseCode, String reasonPhrase) {
		this.value = value;
		this.responseCode = responseCode;
		this.reasonPhrase = reasonPhrase;
	}

	public String code() {
		return this.value;
	}

	public String getReasonPhrase() {
		return this.reasonPhrase;
	}
	
	public String getResponseCode() {
		return responseCode;
	}

}
