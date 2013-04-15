package com.ybcx.zhui.facade;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
			String userId = req.getParameter("userId");
			String dialogue = req.getParameter("dialogue");
			dialogue=URLEncoder.encode(dialogue,"ISO-8859-1");   
			dialogue=URLDecoder.decode(dialogue, "UTF-8");  
			String width = req.getParameter("width");
			String height = req.getParameter("height");
			apiAdaptor.dialogueToImage(userId,dialogue,width,height,res);
			
		}else if(action.equals(AppStarter.SAVETEMPLATE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String name = req.getParameter("name");
			String swf = req.getParameter("swfPath");
			String thumbnail = req.getParameter("thumbnailPath");
			String type = req.getParameter("type");
			String result = apiAdaptor.saveTemplate(name,swf,thumbnail,type);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.DELETETEMPLATE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String templateId = req.getParameter("templateId");
			String result = apiAdaptor.deleteTemplate(templateId);
			pw.print(result);
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
			String bubblePosition = req.getParameter("bubblePosition");
			String videoImage = req.getParameter("videoImage");
			String result = apiAdaptor.saveShot(name,swf,thumbnail,template,frame,bubble,bubbleSize,bubblePosition,videoImage);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.DELETESHOT)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String shotId = req.getParameter("shotId");
			String result = apiAdaptor.deleteShot(shotId);
			pw.print(result);
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
			
		}else if(action.equals(AppStarter.GETTEMPLATEBYCATEGORY)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String type = req.getParameter("type");
//			type = URLEncoder.encode(type,"ISO-8859-1");   
//			type =URLDecoder.decode(type, "UTF-8");  
			String pageNum = req.getParameter("pageNum");
			String pageSize = req.getParameter("pageSize");
			String result = apiAdaptor.getTemplateByCateogry(type,pageNum,pageSize);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.GETSHOTBYTEMPLATE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String templateId = req.getParameter("templateId");
			String result = apiAdaptor.getShotByTemplate(templateId);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.SAVESHOTDIALOGUE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String userId = req.getParameter("userId");
			String templateId = req.getParameter("templateId");
			String content = req.getParameter("content");
			String result = apiAdaptor.saveShotDialogue(userId,templateId,content);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.GETDIALOGUEANIMATION)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String memoryId = req.getParameter("memoryId");
			String result = apiAdaptor.getDialogueAnimation(memoryId);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.GETMEMORYBYUSER)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String userId = req.getParameter("userId");
			String pageNum = req.getParameter("pageNum");
			String pageSize = req.getParameter("pageSize");
			String result = apiAdaptor.getMemoryByUser(userId,pageNum,pageSize);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.SAVECASE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String name = req.getParameter("name");
			String description = req.getParameter("description");
			String swf = req.getParameter("swfPath");
			String thumbnail = req.getParameter("thumbnailPath");
			String result = apiAdaptor.saveCase(name,description,swf,thumbnail);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.DELETECASE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String caseId = req.getParameter("caseId");
			String result = apiAdaptor.deleteCase(caseId);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.GETCASECOUNT)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			int result = apiAdaptor.getCaseCount();
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.GETCASE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String pageNum = req.getParameter("pageNum");
			String pageSize = req.getParameter("pageSize");
			String result = apiAdaptor.getCase(pageNum,pageSize);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.GETTEMPLATECOUNT)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String type = req.getParameter("type");
//			type = URLEncoder.encode(type,"ISO-8859-1");   
//			type =URLDecoder.decode(type, "UTF-8");
			int result = apiAdaptor.getTemplateCount(type);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.GETTEMPLATE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String pageNum = req.getParameter("pageNum");
			String pageSize = req.getParameter("pageSize");
			String result = apiAdaptor.getTemplate(pageNum,pageSize);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.UPDATESHOT)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String shotId = req.getParameter("shotId");
			String frame = req.getParameter("frame");
			String bubbleSize = req.getParameter("bubbleSize");
			String bubblePosition = req.getParameter("bubblePosition");
			String videoImage = req.getParameter("videoImage");
			String result = apiAdaptor.updateShot(shotId,frame,bubbleSize,bubblePosition,videoImage);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.DELETEMEMORY)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String memoryId = req.getParameter("memoryId");
			String result = apiAdaptor.deleteMemory(memoryId);
			pw.print(result);
			pw.close();
			
			
		}else if(action.equals(AppStarter.GETMEMORY)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String pageNum = req.getParameter("pageNum");
			String pageSize = req.getParameter("pageSize");
			String result = apiAdaptor.getMemory(pageNum,pageSize);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.GETMEMORY)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String pageNum = req.getParameter("pageNum");
			String pageSize = req.getParameter("pageSize");
			String result = apiAdaptor.getMemory(pageNum,pageSize);
			pw.print(result);
			pw.close();
			
		//FIXME 定制相关api待联调测试	
		}else if(action.equals(AppStarter.ADDORDER)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String person = req.getParameter("person");
			String category = req.getParameter("category");
			String template = req.getParameter("template");
			String style = req.getParameter("style");
			String music = req.getParameter("music");
			String mins = req.getParameter("mins");
			String tips = req.getParameter("tips");
			String entity = req.getParameter("entity");
			String name = req.getParameter("name");
			String phone = req.getParameter("phone");
			String email = req.getParameter("email");
			String address = req.getParameter("address");
			String result = apiAdaptor.addOrder(person,category,template,style,music,mins,tips,entity,name,phone,email,address);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.UPDATEORDERSTATE)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String orderId = req.getParameter("orderId");
			String state = req.getParameter("state");
			String result = apiAdaptor.updateOrderState(orderId,state);
			pw.print(result);
			pw.close();
			
		}else if(action.equals(AppStarter.DELETEORDER)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String orderId = req.getParameter("orderId");
			String result = apiAdaptor.deleteOrder(orderId);
			pw.print(result);
			pw.close();	
			
		}else if(action.equals(AppStarter.GETORDERCOUNT)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			int result = apiAdaptor.getOrderCount();
			pw.print(result);
			pw.close();	
			
			
		}else if(action.equals(AppStarter.GETORDER)){
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String pageNum = req.getParameter("pageNum");
			String pageSize = req.getParameter("pageSize");
			String result = apiAdaptor.getOrder(pageNum,pageSize);
			pw.print(result);
			pw.close();
			
		}else{
			
		}

	}

	public void setApiAdaptor(ApiAdaptor apiAdaptor) {
		this.apiAdaptor = apiAdaptor;
	}

}
