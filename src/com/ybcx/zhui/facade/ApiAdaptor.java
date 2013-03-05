/**
 * 
 */
package com.ybcx.zhui.facade;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ybcx.zhui.beans.Memory;
import com.ybcx.zhui.beans.Shot;
import com.ybcx.zhui.beans.Template;



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
	
	public void getResource(String resId, String type, HttpServletResponse res) {
		zhuiService.getResource(resId,type,res);
	}
	
	public void dialogueToImage(String userId, String dialogue, String width, String height, HttpServletResponse res) {
		zhuiService.dialogueToImage(userId,dialogue,width,height,res);
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
	
	public String loginSystem(String account, String password) {
		String result = zhuiService.loginSystem(account,password);
		return result;
	}
	
	public String getTemplateByCateogry(String type, String pageNum,
			String pageSize) {
		List<Template> list = zhuiService.getTemplateByCategory(type,pageNum,pageSize);
		return JSONArray.fromCollection(list).toString();
	}
	
	public String getShotByTemplate(String templateId) {
		List<Shot> list = zhuiService.getShotByTemplate(templateId);
		return JSONArray.fromCollection(list).toString();
	}
	
	public String saveShotDialogue(String userId, String templateId, String content) {
		String result = zhuiService.saveShotDialogue(userId, templateId, content);
		return result;
	}
	
	public String getDialogueAnimation(String memoryId) {
		Memory memory = zhuiService.getDialogueAnimation(memoryId);
		return JSONObject.fromBean(memory).toString();
	}
	
	

} // end of class
