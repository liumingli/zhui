package com.ybcx.zhui.dao;

import java.util.List;

import com.ybcx.zhui.beans.Case;
import com.ybcx.zhui.beans.Dialogue;
import com.ybcx.zhui.beans.Memory;
import com.ybcx.zhui.beans.Shot;
import com.ybcx.zhui.beans.Template;


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

	public String getShotFilePath(String resId);

	public String getDialogueFilePath(String resId);

	public List<Memory> getMemoryByUser(String userId,  int pageNum,
			int pageSize);

	public int saveCase(Case cas);

	public int deleteCase(String caseId);

	public List<Case> getCase(int pageNum, int pageSize);

		
}
