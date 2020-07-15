package com.java.sample.ezpos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OauthUser {
	private Long userId;

	private String username;

	private String password;

	private String roleName;

}