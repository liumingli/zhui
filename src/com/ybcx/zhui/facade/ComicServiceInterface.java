package com.ybcx.zhui.facade;


import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;


public interface ComicServiceInterface {

	// 设置图片文件保存路径，由ApiAdaptor赋值
	public void saveImagePathToProcessor(String filePath);

	public String createAdImg(FileItem sourceData);

	public String createAnimation(FileItem shotData, String userId,
			String name, String content);

	public void getThumbnailFile(String relativePath, HttpServletResponse res);

	public void getAssetFile(String relativePath, HttpServletResponse res);

	public void dialogueToImage(String dialogue, String fontSize,
		String isBold, String width, String height, HttpServletResponse res);



}
