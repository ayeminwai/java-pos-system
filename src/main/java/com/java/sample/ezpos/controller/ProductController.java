package com.java.sample.ezpos.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.java.sample.ezpos.beans.req.ProductReqAPI;
import com.java.sample.ezpos.config.GlobalConfiguration;

import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = GlobalConfiguration.PRODUCT_END_POINT + GlobalConfiguration.API_VERSION, headers = "Accept=application/json")
@Api(value = "Product API", description = "Product System")
@Log4j2
public class ProductController {
	
	@RequestMapping(value = "/getProductInfo", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<HashMap> registration(@Valid @RequestBody ProductReqAPI requests) {
		log.info("In Registration Controller Method starting");
		
		//HashMap result = productService.registration(requests);
		
		HashMap result = new HashMap();
		result.put("data", "Success Request");

		log.info("In Registration Controller Method ending");
		return new ResponseEntity<HashMap>(result, HttpStatus.OK);
	}
}
