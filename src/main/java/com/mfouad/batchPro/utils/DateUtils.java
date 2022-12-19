package com.mfouad.batchPro.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	

	static public Date setStartDate(Date date) throws ParseException {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();

	}

	static public Date setEndDate(Date date) throws ParseException {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();

	}

}
