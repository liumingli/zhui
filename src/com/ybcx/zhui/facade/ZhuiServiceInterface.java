package com.ybcx.zhui.facade;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.ybcx.zhui.beans.Case;
import com.ybcx.zhui.beans.Memory;
import com.ybcx.zhui.beans.Order;
import com.ybcx.zhui.beans.Shot;
import com.ybcx.zhui.beans.Template;



public interface ZhuiServiceInterface {

	// 设置图片文件保存路径，由ApiAdaptor赋值
	public void saveImagePathToProcessor(String filePath);

	public void getThumbnailFile(String relativePath, HttpServletResponse res);

	public void getAssetFile(String relativePath, HttpServletResponse res);

	public void getResource(String resId, String type, HttpServletResponse res);

	public void dialogueToImage(String userId, String dialogue, String width, String height, HttpServletResponse res);

	public String saveTemplate(String name, String swf, String thumbnail,
			String type);

	public String deleteTemplate(String templateId);

	public String saveShot(String name, String swf, String thumbnail,
			String template, String frame, String bubble, String bubbleSize);

	public String deleteShot(String shotId);

	public String loginSystem(String account, String password);
	
	public List<Template> getTemplateByCategory(String type, String pageNum,
			String pageSize);

	public List<Shot> getShotByTemplate(String templateId);

	public String saveShotDialogue(String userId, String templateId, String content);

	public Memory getDialogueAnimation(String memoryId);

	public List<Memory> getMemoryByUser(String userId, String pageNum,
			String pageSize);

	public String saveCase(String name, String description, String swf,
			String thumbnail);

	public String deleteCase(String caseId);
	
	public int getCaseCount();

	public List<Case> getCase(String pageNum, String pageSize);

	public int getTemplateCount(String type);

	public List<Template> getTemplate(String pageNum, String pageSize);

	public String updateShot(String shotId, String frame, String bubbleSize);

	public String deleteMemory(String memoryId);

	public List<Memory> getMemory(String pageNum, String pageSize);

	public String addOrder(String person, String category, String template,
			String style, String music, String mins, String tips,
			String entity, String name, String phone, String email,
			String address);

	public String updateOrderState(String orderId, String state);

	public String deleteOrder(String orderId);

	public List<Order> getOrder(String pageNum, String pageSize);

	public String saveVideoImage(FileItem imgData, String memoryId, String status);

	public boolean saveVideoImages(FileItem imgData, String memoryId);

	public String convertImagesToVideo(String memoryId);

}
