package com.java.sample.ezpos;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.MultiValueMap;

import com.java.sample.ezpos.constant.APICodes;

public class MockMvcCall {
	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String password;

	protected MockMvc mockMvc;

	protected static final String CONTENT_TYPE = "application/json;charset=UTF-8";
	protected static final String GRANT_TYPE = "password";
	protected static final String USER_NAME = "admin";
	protected static final String PASSWORD = "jwtpass";

	protected static String RESOURCE_URL = "";

	protected static String TOKEN = "";
	protected static String REF_NO = "";
	protected static String DATE_TIME = "";
    
	public String postCall(String requestJSON, APICodes apiCodes) throws Exception {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", CONTENT_TYPE);
		httpHeaders.add("ReqDatetime", DATE_TIME);
		httpHeaders.add("Authorization", "Bearer " + TOKEN);
		
	    ResultActions result
		= mockMvc.perform(post(RESOURCE_URL).contentType(CONTENT_TYPE)
		.headers(httpHeaders)
		.content(requestJSON))
		.andExpect(content().contentType(CONTENT_TYPE))
		.andExpect(jsonPath("$.response.responseCode").value(apiCodes.code()));
		
		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("response").toString();
	}
	
	public String obtainAccessToken() throws Exception {
		  
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", GRANT_TYPE);
		params.add("username", USER_NAME);
		params.add("password", PASSWORD);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", MimeTypeUtils.TEXT_PLAIN_VALUE);
		httpHeaders.add("ReqDatetime", DATE_TIME);
	 
	    ResultActions result 
	      = mockMvc.perform(post("/EzPosAPI/v1.0/token")
	        .params(params)
	        .headers(httpHeaders)
	        .with(httpBasic(clientId,password))
	        .accept(CONTENT_TYPE))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(CONTENT_TYPE));
	 
		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
}
