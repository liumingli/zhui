package com.ybcx.zhui.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ZhuiUtils {

	private static final  SimpleDateFormat simpledDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String generateUID(){
		String uid = UUID.randomUUID().toString().replace("-", "").substring(16);
		return uid;
	}
	
	public static String getFormatNowTime(){
		String now = simpledDateFormat.format(new Date().getTime());
		return now;
	}
		
	public static String formatDate(Date date){
		return simpledDateFormat.format(date);
	}
	
	//把绝对路径转成相对路径 
	public static String processFilepath(String absolutePath){
		int position = absolutePath.lastIndexOf("uploadFile");
		String relativePath = absolutePath.substring(position+11);
		return relativePath;
	}
	
	//根据路径删除指定文件
	public static boolean deleteFileByPath(List<String> absolutePathList){
		boolean flag = false;
		for(int i=0;i<absolutePathList.size();i++){
			String absolutePath = absolutePathList.get(i);
			File file = new File(absolutePath);
			if(file.exists()){
				flag = file.delete();
			}
		}
		return flag;
	}
}
