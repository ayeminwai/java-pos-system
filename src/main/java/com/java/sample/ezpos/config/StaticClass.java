package com.java.sample.ezpos.config;

import com.java.sample.ezpos.model.APILog;

public class StaticClass {
	static private ThreadLocal<APILog> threadLocalApi = new ThreadLocal<>();

	public static ThreadLocal<APILog> getThreadLocalApi() {
		return threadLocalApi;
	}
}
