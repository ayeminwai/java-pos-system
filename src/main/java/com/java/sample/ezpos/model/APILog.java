package com.java.sample.ezpos.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class APILog {
	
    private String apiLogId;
    private String uniqueId;
    private Date dateTime;
    private String reqUrl;
    private Long processTime;
    private Long processTimeMs;
    private String response;
    private String reqPayLoad;
    private String resPayLoad;
    private String remoteAddress;
    private String sessionId;
    private String reqMethod;
    private String isReqPayLoadSkip;
    private String isResPayLoadSkip;
    private String reqTime;
    private String serverTime;
    private String resTime;
    private String apiCode;
    private String errorDetail;
    private String remoteIp;
    private String remoteIpLocation;
    
	@Override
	public String toString() {
		return "APILog [apiLogId=" + apiLogId + ", uniqueId=" + uniqueId + ", dateTime=" + dateTime + ", reqUrl=" + reqUrl + ", processTime=" + processTime + ", response="
				+ response + ", reqPayLoad=" + reqPayLoad + ", resPayLoad=" + resPayLoad + ", remoteAddress=" + remoteAddress + ", sessionId=" + sessionId + ", reqMethod="
				+ reqMethod + ", isReqPayLoadSkip=" + isReqPayLoadSkip + ", isResPayLoadSkip=" + isResPayLoadSkip + ", reqTime=" + reqTime + ", serverTime=" + serverTime
				+ ", resTime=" + resTime + ", apiCode=" + apiCode + ", errorDetail=" + errorDetail + ", remoteIp=" + remoteIp + ", remoteIpLocation=" + remoteIpLocation + "]";
	}
}
