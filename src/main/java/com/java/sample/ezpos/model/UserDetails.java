package com.java.sample.ezpos.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {
	private Long userId;
	private String name;
	private Date dob;
	private String gender;
	private String mobileNo;
	private String primaryAddress;
	private String currentAddress;
	private String status;
	private Date datetime;
}
