package com.java.sample.ezpos.config;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfiguration {

	// end point start
	public static final String END_POINT = "/ezposadmin";
	public static final String AUTH_END_POINT = "/auth";
	public static final String PRODUCT_END_POINT = "/product";

	// api versioning
	public static final String API_VERSION = "/v1.0";

	// token URL
	public static final String TOKEN_URL = END_POINT + API_VERSION + "/token";

	// authorize URL
	public static final String AUTH_URL = END_POINT + API_VERSION + "/authorize";
	//

	// Request payload
	public static final List<String> reqPayLoadOmitUrls = new ArrayList<String>();

	// Response payload
	public static final List<String> resPayLoadOmitUrls = new ArrayList<String>();
	
	// time Interval validation skip URIs
	public static final List<String> timeIntervalSkip = new ArrayList<String>();

	// GeoLocation file loaded status
	public static boolean geoFileLoaded = false;

	static {
		/*** insert the response payload skip URIs ***/
		resPayLoadOmitUrls.add("/swagger-resources");
		resPayLoadOmitUrls.add("/swagger-resources/configuration/security");
		resPayLoadOmitUrls.add("/swagger-resources/configuration/ui");
		resPayLoadOmitUrls.add("/swagger-ui.html");
		resPayLoadOmitUrls.add("/v2/api-docs");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/springfox.css");
		resPayLoadOmitUrls.add("/swagger-ui.html/swagger-resources/configuration/security");
		resPayLoadOmitUrls.add("/swagger-ui.html/swagger-resources");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/fonts/open-sans-v15-latin-700.woff2");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/fonts/source-code-pro-v7-latin-300.woff2");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/springfox.js");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/fonts/titillium-web-v6-latin-700.woff2");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/fonts/open-sans-v15-latin-regular.woff2");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/fonts/source-code-pro-v7-latin-300.woff2");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/swagger-ui.css");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/swagger-ui-bundle.js");
		resPayLoadOmitUrls.add("/webjars/springfox-swagger-ui/swagger-ui-standalone-preset.js");
		resPayLoadOmitUrls.add("/actuator/info");
		resPayLoadOmitUrls.add("/favicon.ico");

		resPayLoadOmitUrls.add("/oauth/token");
		resPayLoadOmitUrls.add(TOKEN_URL);
		
		
		/*** insert the time interval skip URIs ***/
		timeIntervalSkip.add("/swagger-resources");
		timeIntervalSkip.add("/swagger-resources/configuration/security");
		timeIntervalSkip.add("/swagger-resources/configuration/ui");
		timeIntervalSkip.add("/swagger-ui.html");
		timeIntervalSkip.add("/v2/api-docs");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/springfox.css");
		timeIntervalSkip.add("/swagger-ui.html/swagger-resources/configuration/security");
		timeIntervalSkip.add("/swagger-ui.html/swagger-resources");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/fonts/open-sans-v15-latin-700.woff2");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/fonts/source-code-pro-v7-latin-300.woff2");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/springfox.js");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/fonts/titillium-web-v6-latin-700.woff2");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/fonts/open-sans-v15-latin-regular.woff2");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/fonts/source-code-pro-v7-latin-300.woff2");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/swagger-ui.css");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/swagger-ui-bundle.js");
		timeIntervalSkip.add("/webjars/springfox-swagger-ui/swagger-ui-standalone-preset.js");
		timeIntervalSkip.add("/actuator/info");
		timeIntervalSkip.add("/favicon.ico");

	}

}
