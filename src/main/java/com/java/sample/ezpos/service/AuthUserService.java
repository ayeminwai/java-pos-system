package com.java.sample.ezpos.service;

import java.util.HashMap;

import com.java.sample.ezpos.beans.req.CreateUserReqAPI;

@SuppressWarnings("rawtypes")
public interface AuthUserService {
	public HashMap createUser(CreateUserReqAPI request);
}
