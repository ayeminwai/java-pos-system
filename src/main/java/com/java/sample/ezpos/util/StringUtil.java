package com.java.sample.ezpos.util;

public class StringUtil {
	public static String checkNullStr(String str) {
		if (str == null || str.trim().equals("")) {
			str = " ";
			return str;
		} else {
			return str.trim();
		}
	}

	public static String checkNull(String str) {
		if (str == null || str.trim().equals("")) {
			str = "";
			return str;
		} else {
			return str.trim();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(checkNull(""));
	}
}
