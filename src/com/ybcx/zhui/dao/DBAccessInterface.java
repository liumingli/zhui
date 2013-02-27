package com.ybcx.zhui.dao;

import com.ybcx.zhui.beans.Dialogue;
import com.ybcx.zhui.beans.Shot;
import com.ybcx.zhui.beans.Template;


public interface DBAccessInterface {

	public int saveTemplate(Template template);

	public int deleteTemplate(String id);

	public int deleteShotByTemplate(String id);

	public int saveShot(Shot shot);

	public int deleteShot(String id);

	public int saveDialogue(Dialogue dialogue);

		
}
