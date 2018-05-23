package com.linekong.union.charge.consume.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

	
	public static final Date convertString2DateInMiType(String strDate) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.parse(strDate);
	}
	
	public static final Date getCurrentDateInMiType() throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return sf.parse(sf.format(date));
	}
	
	public static void main(String[] args) throws ParseException {
		getCurrentDateInMiType();
	}


	/**
	 *
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static final Date convertString2DateInMiType1(String strDate) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.parse(strDate);
	}

	/**
	 * 将Date转换成特定的字符串格式
	 * @param date
	 * @return
	 */
	public static String getTimeString1(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}
}
