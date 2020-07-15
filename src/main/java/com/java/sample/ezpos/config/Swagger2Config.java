package com.java.sample.ezpos.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.java.sample.ezpos.constant.IPOS;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class Swagger2Config {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	private String AUTH_SERVER = "http://localhost:8083";

	@Bean
	public Docket api() {

		// Adding Header
		List<Parameter> aParameters = new ArrayList<Parameter>();

		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("ReqDatetime").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
		aParameters.add(aParameterBuilder.build());

		aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("Sign").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
		aParameters.add(aParameterBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.wirecard.cacis.qrcode.controller")).paths(PathSelectors.regex("/.*"))
				.build().apiInfo(apiInfo()).securitySchemes(Collections.singletonList(securitySchema())).securityContexts(Collections.singletonList(securityContext()))
				.useDefaultResponseMessages(false).globalOperationParameters(aParameters);

	}

	private OAuth securitySchema() {

		List<GrantType> grantTypes = new ArrayList<GrantType>();
		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(AUTH_SERVER + "/oauth/token");

		grantTypes.add(creGrant);

		return new OAuth(IPOS.ISwagger.REFERENCE, Arrays.asList(scopes()), grantTypes);

	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(Collections.singletonList(new SecurityReference(IPOS.ISwagger.REFERENCE, scopes())))
				.forPaths(PathSelectors.ant("/**")).build();
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().clientId(clientId).clientSecret(clientSecret).scopeSeparator(" ").useBasicAuthenticationWithAccessCodeGrant(true).build();
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("CACIS eWallet API").description("CACIS eWallet REST APIs")
				.contact(new Contact("Wirecard Singapore Pvt Ltd", "https://www.wirecard.asia/", "nishandan.gobalakris@wirecard.com")).license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0").build();

	}

	private AuthorizationScope[] scopes() {

		AuthorizationScope[] scopes = { new AuthorizationScope("read", "for read operations"), new AuthorizationScope("write", "for write operations"),
				new AuthorizationScope("CACIS", "Access CACIS API") };

		return scopes;
	}

}
