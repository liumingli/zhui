package com.ybcx.zhui.facade;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AssistProcess {
	
	// 由Spring注入
	private ApiAdaptor apiAdaptor;


//	private Logger log = Logger.getLogger(AssistProcess.class);
	
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
			String dialogue = req.getParameter("dialogue");
			String fontSize = req.getParameter("fontSize");
			String isBold = req.getParameter("isBold");
			String width = req.getParameter("width");
			String height = req.getParameter("height");
			apiAdaptor.dialogueToImage(dialogue,fontSize, isBold,width,height,res);
			
		}else if(action.equals(AppStarter.SAVETEMPLATE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String name = req.getParameter("name");
			String swf = req.getParameter("swfPath");
			String thumbnail = req.getParameter("thumbnailPath");
			String type = req.getParameter("type");
			String result = apiAdaptor.saveTemplate(name,swf,thumbnail,type);
			pw.write(result);
			pw.close();
			
		}else if(action.equals(AppStarter.DELETETEMPLATE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String id = req.getParameter("id");
			String result = apiAdaptor.deleteTemplate(id);
			pw.write(result);
			pw.close();
		
		}else if(action.equals(AppStarter.SAVESHOT)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String name = req.getParameter("name");
			String swf = req.getParameter("swfPath");
			String thumbnail = req.getParameter("thumbnailPath");
			String template = req.getParameter("template");
			String frame = req.getParameter("frame");
			String bubble = req.getParameter("bubble");
			String bubbleSize = req.getParameter("bubbleSize");
			String result = apiAdaptor.saveShot(name,swf,thumbnail,template,frame,bubble,bubbleSize);
			pw.write(result);
			pw.close();
			
		}else if(action.equals(AppStarter.DELETESHOT)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String id = req.getParameter("id");
			String result = apiAdaptor.deleteShot(id);
			pw.write(result);
			pw.close();
			
		}else if(action.equals(AppStarter.SAVEDIALOGUE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String content = req.getParameter("content");
			String image = req.getParameter("imagePath");
			String shot = req.getParameter("shot");
			String frame = req.getParameter("frame");
			String result = apiAdaptor.saveDialogue(content,image,shot,frame);
			pw.write(result);
			pw.close();
			
		}else if (action.equals(AppStarter.LOGINSYSTEM)) {
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String account = req.getParameter("account");
			String password = req.getParameter("password");
		    String result= apiAdaptor.loginSystem(account,password);
		    if(result.equals("true")){
		    	HttpSession session = req.getSession(false);
				session.setAttribute("user", account);
				session.setMaxInactiveInterval(2*60*60);
		    }
			pw.print(result);
			pw.close();
			
		}else{
			
		}

	}

	public void setApiAdaptor(ApiAdaptor apiAdaptor) {
		this.apiAdaptor = apiAdaptor;
	}

}
