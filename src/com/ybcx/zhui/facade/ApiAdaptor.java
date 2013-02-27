/**
 * 
 */
package com.ybcx.zhui.facade;

import javax.servlet.http.HttpServletResponse;



/**
 * Servlet调用服务的参数转换器，用来封装客户端参数并实现服务调用；
 * 
 * @author lwz
 * 
 */

public class ApiAdaptor {
	
	// 由Spring注入
	private ZhuiServiceInterface zhuiService;

	public void setZhuiService(ZhuiServiceInterface zhuiService) {
		this.zhuiService = zhuiService;
	}
	public ApiAdaptor() {

	}

	// 由AppStarter调用
	public void setImagePath(String filePath) {
		this.zhuiService.saveImagePathToProcessor(filePath);
	}
	
	public void getThumbnailFile(String relativePath, HttpServletResponse res) {
		zhuiService.getThumbnailFile(relativePath,res);
	}
	
	public void getAssetFile(String relativePath, HttpServletResponse res) {
		zhuiService.getAssetFile(relativePath,res);
	}
	
	public void dialogueToImage(String dialogue, String fontSize,
		 String isBold, String width, String height, HttpServletResponse res) {
		zhuiService.dialogueToImage(dialogue,fontSize,isBold,width,height,res);
	}
	
	public String saveTemplate(String name, String swf, String thumbnail,
			String type) {
		String res = zhuiService.saveTemplate(name,swf,thumbnail,type);
		return res;
	}
	
	public String deleteTemplate(String id) {
		String res = zhuiService.deleteTemplate(id);
		return res;
	}
	
	public String saveShot(String name, String swf, String thumbnail,
			String template, String frame, String bubble, String bubbleSize) {
		String res = zhuiService.saveShot(name,swf,thumbnail,template,frame,bubble,bubbleSize);
		return res;
	}
	
	public String deleteShot(String id) {
		String res = zhuiService.deleteShot(id);
		return res;
	}
	
	public String saveDialogue(String content, String image, String shot,
			String frame) {
		String res = zhuiService.saveDialogue(content,image,shot,frame);
		return  res;
	}
	
	

} // end of class
