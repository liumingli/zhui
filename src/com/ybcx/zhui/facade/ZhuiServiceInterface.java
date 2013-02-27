package com.ybcx.zhui.facade;


import javax.servlet.http.HttpServletResponse;



public interface ZhuiServiceInterface {

	// 设置图片文件保存路径，由ApiAdaptor赋值
	public void saveImagePathToProcessor(String filePath);

	public void getThumbnailFile(String relativePath, HttpServletResponse res);

	public void getAssetFile(String relativePath, HttpServletResponse res);

	public void dialogueToImage(String dialogue, String fontSize,
		String isBold, String width, String height, HttpServletResponse res);

	public String saveTemplate(String name, String swf, String thumbnail,
			String type);

	public String deleteTemplate(String id);

	public String saveShot(String name, String swf, String thumbnail,
			String template, String frame, String bubble, String bubbleSize);

	public String deleteShot(String id);

	public String saveDialogue(String content, String image, String shot,
			String frame);


}
