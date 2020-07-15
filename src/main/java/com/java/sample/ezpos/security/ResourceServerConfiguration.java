package com.java.sample.ezpos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import com.java.sample.ezpos.config.GlobalConfiguration;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		
	private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            GlobalConfiguration.AUTH_END_POINT,
            GlobalConfiguration.PRODUCT_END_POINT
            //add other public endpoint
    };

	@Autowired
	private ResourceServerTokenServices tokenServices;

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources
		.resourceId(resourceIds)
		.tokenServices(tokenServices)
		.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
		.anonymous().disable()
		.requestMatchers()
		.antMatchers("/ezposadmin/**")
		.antMatchers(GlobalConfiguration.AUTH_END_POINT + GlobalConfiguration.API_VERSION + "/**")
		.antMatchers(GlobalConfiguration.PRODUCT_END_POINT + GlobalConfiguration.API_VERSION + "/**")
		.and().authorizeRequests()
		.antMatchers("/ezposadmin/**").authenticated()
		.antMatchers(GlobalConfiguration.AUTH_END_POINT + GlobalConfiguration.API_VERSION + "/**").authenticated()
		.antMatchers(GlobalConfiguration.PRODUCT_END_POINT + GlobalConfiguration.API_VERSION + "/**").authenticated()
		.antMatchers(AUTH_WHITELIST).permitAll()  // white list Swagger UI resources
		;

	}

}
