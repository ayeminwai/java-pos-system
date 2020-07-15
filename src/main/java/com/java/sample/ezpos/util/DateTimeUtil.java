package com.java.sample.ezpos.util;

import java.util.concurrent.TimeUnit;

public class DateTimeUtil {

	public long nanoSecToSec(long nanoSec) {
		long secs = TimeUnit.SECONDS.convert(nanoSec, TimeUnit.NANOSECONDS);
		return secs;
	}

	public long nanoSecToMilliSec(long nanoSec) {
		long milliSecs = TimeUnit.MILLISECONDS.convert(nanoSec, TimeUnit.NANOSECONDS);
		return milliSecs;
	}

}