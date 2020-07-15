package com.java.sample.ezpos;

import javax.annotation.security.RunAs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.sample.ezpos.beans.req.CreateUserReqAPI;
import com.java.sample.ezpos.constant.APICodes;
import com.java.sample.ezpos.constant.IPOS;
import com.java.sample.ezpos.util.DateUtil;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class UserCreateTests extends MockMvcCall {
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@BeforeEach
	public void setup() {
		System.out.println("Before setup");
		RESOURCE_URL = "auth/v1.0/createUser";

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();

		try {

			DATE_TIME = DateUtil.getServerTime();
			TOKEN = obtainAccessToken();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCaseA_Success() throws Exception {
		CreateUserReqAPI request = new CreateUserReqAPI();

		request.setReqDateTime(DATE_TIME);
		request.setName("ayeminwai1");
		request.setDob("04/06/1991");
		request.setGender(IPOS.IUser.Gender.MALE);
		request.setMobileNo("+959972784660");
		request.setPrimaryAddress("mayangone township, Yangon");
		request.setCurrentAddress("shwethaung township, Pathein");
		request.setLoginUserName("ayeminwai");
		request.setPassword("ayeminwai");

		System.out.println("Create User Success Call API");
		String response = postCall(new ObjectMapper().writeValueAsString(request), APICodes.SUCCESS);

		System.out.println(response);
	}


}
