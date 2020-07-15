package com.java.sample.ezpos.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Order(1)
@Log4j2
public class LogMDCFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.info("In LogMDCFilter doFilter Order 1");
		try {	
			
			HttpServletRequest httpReq = (HttpServletRequest) request;
			
		    ThreadContext.put("id", UUID.randomUUID().toString());
		    ThreadContext.put("sessionId", httpReq.getSession().getId());
		    ThreadContext.put("servletPath", httpReq.getServletPath());
		    
			log.info("LogMDCFilter do filter after assign the MDC records");
			
			chain.doFilter(request, response);
			
		} finally {
			// clean ThreadLocal data
			ThreadContext.clearAll();
		}

		log.info("End LogMDCFilter doFilter");

	}

}
