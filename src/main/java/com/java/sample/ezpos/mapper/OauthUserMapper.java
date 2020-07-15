package com.java.sample.ezpos.mapper;

import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.model.OauthUser;

public interface OauthUserMapper {

	OauthUser findUserByUsername(String userName) throws EzposException;

	void insert(OauthUser oauthUser) throws EzposException;

}