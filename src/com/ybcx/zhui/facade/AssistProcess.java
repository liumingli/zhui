package com.ybcx.zhui.facade;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class AssistProcess {
	
	// 由Spring注入
	private ApiAdaptor apiAdaptor;


	private Logger log = Logger.getLogger(AssistProcess.class);
	
	/**
	 * 处理正常用户登录post的请求
	 * 
	 * @param action
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPostProcess(String action, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {

		if(!GlobalController.isDebug){
		}

		//将文字对白转成图片存储
		if (action.equals(AppStarter.DIALOGUETOIMAGE)) {
			
			res.setContentType("text/plain;charset=UTF-8");
			String dialogue = req.getParameter("dialogue");
			String fontSize = req.getParameter("fontSize");
			String isBold = req.getParameter("isBold");
			String width = req.getParameter("width");
			String height = req.getParameter("height");
			apiAdaptor.dialogueToImage(dialogue,fontSize, isBold,width,height,res);
			
		}else{
			
		}

	}

	public void setApiAdaptor(ApiAdaptor apiAdaptor) {
		this.apiAdaptor = apiAdaptor;
	}

}
