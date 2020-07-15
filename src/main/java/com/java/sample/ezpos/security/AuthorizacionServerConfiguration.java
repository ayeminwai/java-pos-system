package com.java.sample.ezpos.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.java.sample.ezpos.config.GlobalConfiguration;

@Configuration
@EnableAuthorizationServer
@PropertySource("classpath:application.properties")
public class AuthorizacionServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite;

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	@Value("${token.validity.time}")
	private String tokenValidityTime;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private TokenEnhancer accessTokenEnhancer;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer 
		.inMemory() 
		.withClient(clientId)
		.secret(passwordEncoder.encode(clientSecret))
		.scopes(scopeRead, scopeWrite)
		.resourceIds(resourceIds)
		.accessTokenValiditySeconds(Integer.valueOf(tokenValidityTime).intValue() * 60); // mins to seconds
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpointsConfig) throws Exception {

		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenEnhancer));
		
		endpointsConfig
		.pathMapping("/oauth/token", GlobalConfiguration.TOKEN_URL) // change oauth token url
		.tokenStore(tokenStore)
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(enhancerChain)
		.authenticationManager(authenticationManager)
		;
	}
}
