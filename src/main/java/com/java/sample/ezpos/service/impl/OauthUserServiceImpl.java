package com.java.sample.ezpos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.sample.ezpos.config.MyBatisDBService;
import com.java.sample.ezpos.constant.APICodes;
import com.java.sample.ezpos.constant.IMessages;
import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.mapper.OauthUserMapper;
import com.java.sample.ezpos.model.OauthUser;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OauthUserServiceImpl implements UserDetailsService {
	@Autowired
	private MyBatisDBService datasourceMybatis;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = null;

		try (SqlSession sqlSession = datasourceMybatis.sqlSessionFactory(datasourceMybatis.datasource()).openSession(false);) {
			OauthUserMapper oauthUserMapper = sqlSession.getMapper(OauthUserMapper.class);

			OauthUser oauthUser = oauthUserMapper.findUserByUsername(username);
			if (oauthUser == null)
				throw new UsernameNotFoundException("User username is wrong");

			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(oauthUser.getRoleName()));

			userDetails = new org.springframework.security.core.userdetails.User(username, oauthUser.getPassword(), authorities);

		} catch (EzposException e) {
			throw e;
		} catch (Exception e) {
			log.info("In loadUserByUsername Method ending in Exception");
			log.error(e);
			throw new EzposException(HttpStatus.INTERNAL_SERVER_ERROR, IMessages.IError.INTERNAL_ERROR, APICodes.INTERNAL_SERVER_ERROR, e);
		}

		return userDetails;
	}

}
