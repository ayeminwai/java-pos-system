package com.java.sample.ezpos.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.java.sample.ezpos.beans.req.CreateUserReqAPI;
import com.java.sample.ezpos.beans.res.CreateUserResAPI;
import com.java.sample.ezpos.config.GlobalConfiguration;
import com.java.sample.ezpos.constant.IMessages;
import com.java.sample.ezpos.service.AuthUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = GlobalConfiguration.AUTH_END_POINT + GlobalConfiguration.API_VERSION, headers = "Accept=application/json")
@Api(value = "Authentication API", description = "Application Authentication System")
@Log4j2
@SuppressWarnings("rawtypes")
public class AuthController {
	
	@Autowired
	AuthUserService authUserService;

	@ApiOperation(value = "Create User", response = CreateUserResAPI.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = IMessages.ISuccess.CREATE_SUCCESS),
			@ApiResponse(code = 401, message = IMessages.IError.UNAUTHORIZED),
			@ApiResponse(code = 400, message = IMessages.IError.BAD_REQUEST),
			@ApiResponse(code = 500, message = IMessages.IError.INTERNAL_ERROR) })
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HashMap> createUser(@Valid @RequestBody CreateUserReqAPI requests) {
		log.info("In Create User Method starting");
		
		HashMap result = authUserService.createUser(requests);

		log.info("In Create User Method ending");
		return new ResponseEntity<HashMap>(result, HttpStatus.OK);
	}
}
