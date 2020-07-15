package com.java.sample.ezpos.geo;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public interface GeoService {

	String getLocationInfo(String ip) throws Exception;

	File loadGeo() throws Exception;

	String getClientIP(HttpServletRequest request);

}
