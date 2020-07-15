package com.java.sample.ezpos.security;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.sample.ezpos.beans.res.CoreAPIRes;
import com.java.sample.ezpos.constant.APICodes;
import com.java.sample.ezpos.constant.IPOS;
import com.java.sample.ezpos.util.DateUtil;

@SuppressWarnings({"rawtypes","unchecked"})
public class CustomAuthenticationEntryPoint extends AbstractOAuth2SecurityExceptionHandler implements AuthenticationEntryPoint {

	@Autowired
	ObjectMapper objectMapper;

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

		response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		// add the server response time
		String resTime = DateUtil.getServerTime();
		response.setHeader(IPOS.IHeader.RES_DATETIME, resTime);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		HashMap result= new HashMap();
		CoreAPIRes objError = new CoreAPIRes(APICodes.TOKEN_ERROR.code(), APICodes.TOKEN_ERROR.getReasonPhrase());
		result.put(IPOS.IKey.IResponse.RESPONSE,objError);
		
		response.getWriter().write(objectMapper.writeValueAsString(result));

		response.getWriter().flush();

	}

}
