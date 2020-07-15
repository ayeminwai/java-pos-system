package com.java.sample.ezpos.service.impl;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.sample.ezpos.beans.req.CreateUserReqAPI;
import com.java.sample.ezpos.beans.res.CreateUserResAPI;
import com.java.sample.ezpos.config.MyBatisDBService;
import com.java.sample.ezpos.constant.APICodes;
import com.java.sample.ezpos.constant.IMessages;
import com.java.sample.ezpos.constant.IPOS;
import com.java.sample.ezpos.exception.EzposException;
import com.java.sample.ezpos.mapper.OauthUserMapper;
import com.java.sample.ezpos.mapper.UserDetailsMapper;
import com.java.sample.ezpos.model.OauthUser;
import com.java.sample.ezpos.model.UserDetails;
import com.java.sample.ezpos.service.AuthUserService;
import com.java.sample.ezpos.util.DateUtil;
import com.java.sample.ezpos.util.PasswordGenUtil;

import lombok.extern.log4j.Log4j2;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
@Log4j2
public class AuthUserServiceImpl implements AuthUserService {
	@Autowired
	private MyBatisDBService datasourceMybatis;

	@Override
	public HashMap createUser(CreateUserReqAPI request) {
		log.info("In createUser service method starting");
		CreateUserResAPI response = new CreateUserResAPI();
		
		try(SqlSession sqlSession = datasourceMybatis.sqlSessionFactory(datasourceMybatis.datasource()).openSession(false);) {
			OauthUserMapper oauthUserMapper = sqlSession.getMapper(OauthUserMapper.class);
			UserDetailsMapper userDetailsMapper = sqlSession.getMapper(UserDetailsMapper.class);
			
			OauthUser oauthUser = new OauthUser();
			oauthUser.setPassword(PasswordGenUtil.generatePassword(request.getPassword()));
			oauthUser.setUsername(request.getLoginUserName());
			oauthUser.setRoleName(IPOS.IUser.Role.USER);
			
			oauthUserMapper.insert(oauthUser);
			
			UserDetails userDetails = new UserDetails();
			userDetails.setUserId(oauthUser.getUserId());
			userDetails.setDatetime(DateUtil.getCurrentDateTime());
			userDetails.setDob(DateUtil.convertDateStringToDate(request.getDob()));
			userDetails.setGender(request.getGender());
			userDetails.setMobileNo(request.getMobileNo());
			userDetails.setName(request.getName());
			userDetails.setStatus(request.getGender());
			userDetails.setPrimaryAddress(request.getPrimaryAddress());
			userDetails.setCurrentAddress(request.getCurrentAddress());
			
			userDetailsMapper.insert(userDetails);
			
			response.getCoreSuccessResponse();
			
			sqlSession.commit();
			
		} catch (EzposException e) {
			log.info("In createUser service method ending in CacisException");
			throw e;
		} catch (Exception e) {
			log.info("In createUser service method ending in Exception");
			log.error(e);
			throw new EzposException(HttpStatus.INTERNAL_SERVER_ERROR, IMessages.IError.INTERNAL_ERROR , APICodes.INTERNAL_SERVER_ERROR, e);
		}

		HashMap returnMap = new HashMap();
		returnMap.put(IPOS.IKey.IResponse.RESPONSE, response);
		log.info("In createUser service method ending");
		return returnMap;
	}
	
}
