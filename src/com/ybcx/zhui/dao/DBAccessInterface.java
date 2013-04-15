package com.ybcx.zhui.dao;

import java.util.List;

import com.ybcx.zhui.beans.Case;
import com.ybcx.zhui.beans.Dialogue;
import com.ybcx.zhui.beans.Memory;
import com.ybcx.zhui.beans.Order;
import com.ybcx.zhui.beans.Shot;
import com.ybcx.zhui.beans.Template;
import com.ybcx.zhui.beans.User;


public interface DBAccessInterface {

	public int saveTemplate(Template template);

	public int deleteTemplate(String templateId);

	public int deleteShotByTemplate(String templateId);

	public int saveShot(Shot shot);

	public int deleteShot(String shotId);

	public int saveDialogue(Dialogue dialogue);
	
	public List<Template> getTemplateByCategory(String type, int pageNum,
			int pageSize);

	public List<Shot> getShotByTemplate(String templateId);

	public Shot getShotById(String shotId);

	public int saveMemory(Memory memory);

	public Memory getDialogueAnimation(String memoryId);

	public String getTemplateFilePath(String resId);

	public String getTemplateImagePath(String resId);

	public String getShotFilePath(String resId);

	public String getDialogueFilePath(String resId);

	public List<Memory> getMemoryByUser(String userId,  int pageNum,
			int pageSize);

	public int saveCase(Case cas);

	public int deleteCase(String caseId);
	
	public int getCaseCount();

	public List<Case> getCase(int pageNum, int pageSize);
	
	public int getTemplateCount();
	
	public int getTemplateCountByType(String type);

	public List<Template> getTemplate(int pageNum, int pageSize);

	public int updateShot(String shotId, int frame, String bubbleSize, String bubblePosition, String videoImage, int hasBubble);

	public Case getCaseById(String caseId);

	public Template getTemplateById(String templateId);

	public int deleteMemory(String memoryId);

	public Memory getMemoryById(String memoryId);

	public int deleteDialogue(String dialogueId);

	public List<Memory> getMemory(int pageNum, int pageSize);
	
	public int addOrder(Order order);

	public int updateOrderState(String orderId, int state);

	public int deleteOrder(String orderId);

	public List<Order> getOrder(int pageNum, int pageSize);

	public int updateMemoryVideo(String memoryId, String videoAddress);

	public int getOrderCount();

	public int checkUserExist(String userId);

	public int updateUserById(String userId, String accessToken, String nickName);

	public int createNewUser(User user);

	public User getUserById(String userId);



}
