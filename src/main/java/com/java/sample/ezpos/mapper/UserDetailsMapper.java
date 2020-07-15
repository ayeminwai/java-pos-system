package com.java.sample.ezpos.mapper;

import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.model.UserDetails;

public interface UserDetailsMapper {
	void insert(UserDetails userDetails) throws EzposException;
}
