package com.java.sample.ezpos.beans.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.java.sample.ezpos.validation.custom.DOB;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserReqAPI extends CoreAPIReq {
	
	@NotEmpty(message = "{name.not.empty}")
	@Size(max = 45, message = "{name.size}")
	private String name;
	
	@NotEmpty(message = "{dob.not.empty}")
	@DOB
	private String dob;
	
	@NotEmpty(message = "{gender.not.empty}")
	private String gender;
	
	@NotEmpty(message = "{mobileNo.not.empty}")
	private String mobileNo;
	
	@NotEmpty(message = "{primaryAddress.not.empty}")
	private String primaryAddress;
	
	@NotEmpty(message = "{currentAddress.not.empty}")
	private String currentAddress;
	
	@NotEmpty(message = "{loginUserName.not.empty}")
	private String loginUserName;
	
	@NotEmpty(message = "{password.not.empty}")
	private String password;
	
}