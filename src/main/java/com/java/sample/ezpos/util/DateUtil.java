package com.java.sample.ezpos.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	public static final DateTimeFormatter SERVER_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
	public static final DateTimeFormatter STANDARD_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static String getServerTime() {
		return LocalDateTime.now().format(SERVER_DATE_TIME_FORMATTER);
	}

	public static Date convertAPIDateStringToDate(String dateStr) {
		return java.sql.Timestamp.valueOf(LocalDateTime.parse(dateStr, SERVER_DATE_TIME_FORMATTER));
	}

	public static long calDifferenceBetweenTwoDates(Date startDate, Date endDate) {
		long diff = endDate.getTime() - startDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
	}

	public static long calDiffInMinBetweenTwoDates(Date startDate, Date endDate) {
		long diff = endDate.getTime() - startDate.getTime();
		long diffMin = diff / (60 * 60 * 1000);
		return diffMin;
	}

	public static long calDifferenceInMin(Date startDate, Date endDate) {
		long diff = endDate.getTime() - startDate.getTime();
		long diffMin = diff / (60 * 1000);
		return diffMin;
	}
	
	public static Date convertDateStringToDate(String dateStr) {
		return java.sql.Date.valueOf(LocalDate.parse(dateStr, STANDARD_DATE_FORMAT));
	}
	
	public static Date getCurrentDateNoTime(){
		return java.sql.Date.valueOf(LocalDate.now());
	}
	
	public static Date getCurrentDateTime(){
		return java.sql.Timestamp.valueOf(LocalDateTime.now());
	}
}
