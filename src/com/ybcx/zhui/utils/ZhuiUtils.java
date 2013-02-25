package com.ybcx.zhui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ZhuiUtils {

	private static final  SimpleDateFormat simpledDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String generateUID(){
		String uid = UUID.randomUUID().toString().replace("-", "").substring(16);
		return uid;
	}
	
	public static String formatDate(Date date){
		return simpledDateFormat.format(date);
	}
	
}
