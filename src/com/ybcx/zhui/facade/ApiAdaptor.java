/**
 * 
 */
package com.ybcx.zhui.facade;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;

import com.ybcx.zhui.beans.Case;
import com.ybcx.zhui.beans.Memory;
import com.ybcx.zhui.beans.Order;
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
	
	public String deleteTemplate(String templateId) {
		String res = zhuiService.deleteTemplate(templateId);
		return res;
	}
	
	public String saveShot(String name, String swf, String thumbnail,
			String template, String frame, String bubble, String bubbleSize) {
		String res = zhuiService.saveShot(name,swf,thumbnail,template,frame,bubble,bubbleSize);
		return res;
	}
	
	public String deleteShot(String shotId) {
		String res = zhuiService.deleteShot(shotId);
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
	
	public String getMemoryByUser(String userId, String pageNum, String pageSize) {
		List<Memory> list = zhuiService.getMemoryByUser(userId,pageNum,pageSize);
		return JSONArray.fromCollection(list).toString();
	}
	
	public String saveCase(String name, String description, String swf,
			String thumbnail) {
		String result = zhuiService.saveCase(name,description,swf,thumbnail);
		return result;
	}
	
	public String deleteCase(String caseId) {
		String result = zhuiService.deleteCase(caseId);
		return result;
	}
	
	public int getCaseCount() {
		int result = zhuiService.getCaseCount();
		return result;
	}
	
	public String getCase(String pageNum, String pageSize) {
		List<Case> list = zhuiService.getCase(pageNum,pageSize);
		return JSONArray.fromCollection(list).toString();
	}
	
	public int getTemplateCount(String type) {
		int result = zhuiService.getTemplateCount(type);
		return result;
	}
	
	public String getTemplate(String pageNum, String pageSize) {
		List<Template> list = zhuiService.getTemplate(pageNum,pageSize);
		return JSONArray.fromCollection(list).toString();
	}
	
	public String updateShot(String shotId, String frame, String bubbleSize) {
		String result = zhuiService.updateShot(shotId,frame,bubbleSize);
		return result;
	}
	
	public String deleteMemory(String memoryId) {
		String result = zhuiService.deleteMemory(memoryId);
		return result;
	}
	
	public String getMemory(String pageNum, String pageSize) {
		List<Memory> list = zhuiService.getMemory(pageNum,pageSize);
		return JSONArray.fromCollection(list).toString();
	}
	
	public String addOrder(String person, String category, String template,
			String style, String music, String mins, String tips, String entity, 
			String name, String phone, String email, String address) {
		String result = zhuiService.addOrder(person,category,template,style,music,mins,tips,entity,name,phone,email,address);
		return result;
	}
	
	public String updateOrderState(String orderId, String state) {
		String result = zhuiService.updateOrderState(orderId,state);
		return result;
	}
	
	public String deleteOrder(String orderId) {
		String result = zhuiService.deleteOrder(orderId);
		return result;
	}
	public String getOrder(String pageNum, String pageSize) {
		List<Order> list = zhuiService.getOrder(pageNum,pageSize);
		return JSONArray.fromCollection(list).toString();
	}
	
	public int getOrderCount() {
		int result = zhuiService.getOrderCount();
		return result;
	}
	
	
	
	public String uploadFrame(List<FileItem> fileItems) {
		FileItem imgData = null;
		String memoryId = "";
		String status = "";
		
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem item = fileItems.get(i);
			if (!item.isFormField()) {
				//图片数据
				imgData = item;
			}
			
			if (item.isFormField()) {
				
				if (item.getFieldName().equals("memoryId")) {
					memoryId = item.getString();
				}
				
				if (item.getFieldName().equals("status")) {
					status = item.getString();
				}
				
			}
		}//取参数完成
	
		String result = zhuiService.saveVideoImage(imgData,memoryId,status);
		
		return result;
	}
	
	public String uploadFrames(List<FileItem> fileItems) {
		String result = "false";
		FileItem imgData = null;
		String memoryId = "";
		
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem item = fileItems.get(i);
			if (item.isFormField()) {
				if (item.getFieldName().equals("memoryId")) {
					memoryId = item.getString();
				}
			}
		}
		
		for (int j = 0; j < fileItems.size(); j++) {
			FileItem fileItem = fileItems.get(j);
			if (!fileItem.isFormField()) {
				//图片数据
				imgData = fileItem;
			}
			if(imgData != null && !"".equals(memoryId)){
				//存图片
				zhuiService.saveVideoImages(imgData,memoryId);
			}
		}
		
		if(!"".equals(memoryId)){
			result = zhuiService.convertImagesToVideo(memoryId);
		}
		
		return result;
	}
	
	public String operateWeiboUser(String userId, String accessToken) {
		String result = zhuiService.operateWeiboUser(userId,accessToken);
		return result;
	}
	
	public String shareToWeibo(List<FileItem> fileItems) {
		String result = "false";
		//先保存图片
		String imgPath = this.createImage(fileItems);
		File imgFile = new File(imgPath);
		if(imgFile.exists()){
			//再发微博
			result = this.publishImageWeibo(fileItems,imgPath);
		}else{
			
		}
		return result;
	}
	
	private String createImage(List<FileItem> fileItems) {
		FileItem shotData = null;
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem item = fileItems.get(i);
			if (!item.isFormField()) {
				//图片数据
				shotData = item;
			}
		}
	
		String result = zhuiService.createWeiboImage(shotData);
		return result;
	}
	
	private String publishImageWeibo(List<FileItem> fileItems, String imgPath) {
		String userId = "";
		String content = "";
		
		for (int i = 0; i < fileItems.size(); i++) {
			
			FileItem item = fileItems.get(i);
			if (item.isFormField()) {
				
				if (item.getFieldName().equals("userId")) {
					userId = item.getString();
				}
				
				if (item.getFieldName().equals("content")) {
					try {
						content = item.getString("UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}//取参数完成
		
		String result = zhuiService.shareToWeibo(userId,content,imgPath);
		
		return result;
	}



} // end of class
