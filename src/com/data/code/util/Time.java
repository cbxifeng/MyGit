package com.data.code.util;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("SimpleDateFormat")
public class Time {

	public static String TimeFor() {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy��MM��dd��   HH:mm:ss     ");
		Date curDate = new Date(java.lang.System.currentTimeMillis());// ��ȡ��ǰʱ��

		String str = formatter.format(curDate);
		return str;

	}

}
