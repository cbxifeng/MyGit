package com.data.code.util;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("SimpleDateFormat")
public class Time {

	public static String TimeFor() {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日   HH:mm:ss     ");
		Date curDate = new Date(java.lang.System.currentTimeMillis());// 获取当前时间

		String str = formatter.format(curDate);
		return str;

	}

}
