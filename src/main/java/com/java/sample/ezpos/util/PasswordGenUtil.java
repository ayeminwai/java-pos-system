package com.java.sample.ezpos.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenUtil {
	
	public static String generatePassword(String password) {
		BCryptPasswordEncoder bp = new BCryptPasswordEncoder();
		return bp.encode(password);
	}

	public static void main(String[] args) {

		BCryptPasswordEncoder bp = new BCryptPasswordEncoder();
		System.out.println(bp.encode("jwtuserpass"));
		System.out.println(bp.matches("jwtuserpass", "$2a$10$HNOaQxXB9hzMoVIH6hODR.LYRzfKEJ5ln.UqdaCgeQQRngTKHmVbG"));
	}

}
