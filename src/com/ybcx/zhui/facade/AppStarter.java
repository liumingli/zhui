package com.ybcx.zhui.facade;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 真正处理客户端传参到服务端的逻辑，与适配器打交道，适配器再与服务打交道；
 * 
 * 
 */
@SuppressWarnings("rawtypes")
public class AppStarter extends HttpServlet implements ApplicationListener,
		ExtVisitorInterface {

	private Logger log = Logger.getLogger(AppStarter.class);

	private static final long serialVersionUID = 1L;

	// 由Spring注入
	private ApiAdaptor apiAdaptor;
	
	private AssistProcess assistProcess;

	// 最大文件上传尺寸设置
	private int fileMaxSize = 20 * 1024 * 1024;
	// 上传组件
	private ServletFileUpload upload;

	public AppStarter() {
		// do nothing...
	}

	public void setApiAdaptor(ApiAdaptor apiAdaptor) {
		this.apiAdaptor = apiAdaptor;
	}

	public void setAssistProcess(AssistProcess assistProcess) {
		this.assistProcess = assistProcess;
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		log.debug(">>> appstater start to analyze form...");

		// 这里将客户端参数解析出来传给apiAdaptor,由apiAdaptor组装参数给服务
		String action = req.getParameter("method");
		log.debug("method:" + action);

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		log.debug("isMultipart value is:" + isMultipart);

		if (action == null && isMultipart == false) {
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			pw.write("请求无效！");
			pw.close();
			return;
		}

		if (action == null && isMultipart) {
			// 因上传文件enctype的特殊处理，所以得不到参数，故只判断isMultipart
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			// 授理上传图片的请求
			processMultiPart(req, pw);
			pw.close();
			return;
		}
		
		//微博接入有关api
		if(action.equals(AppStarter.OPERATEWEIBOUSER)){
			
			weiboProcess(action,req,res);
			return;
		}
			
			//获取图片，将图片流写到response
		if (action.equals(AppStarter.GETTHUMBNAIL)
				|| action.equals(AppStarter.GETASSETFILE)
				|| action.equals(AppStarter.GETRESOURCE)) {
			
				doGetProcess(action, req, res);
				return;
				
		}else {
			assistProcess.doPostProcess(action,req,res);
			return;
		}
		
	}
	
	
	/**
	 * 处理微博相关API
	 * @param action
	 * @param req
	 * @param res
	 * @throws IOException 
	 */

	private void weiboProcess(String action, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		
		if (action.equals(AppStarter.OPERATEWEIBOUSER)) {
			//新浪微博~判断用户是存在，存在更新，否则新建
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter pw = res.getWriter();
			String userId =req.getParameter("userId");
			String accessToken = req.getParameter("accessToken");
			String result = apiAdaptor.operateWeiboUser(userId,accessToken);
			pw.print(result);
			pw.close();
			
		}
	}

	/**
	 * 处理get方式的用户请求
	 * 
	 * @param action
	 * @param req
	 * @param res
	 * @throws IOException 
	 * @throws FileUploadException 
	 */
	private void doGetProcess(String action, HttpServletRequest req,
			HttpServletResponse res) throws IOException{
		
		if (action.equals(AppStarter.GETTHUMBNAIL)) {
			// 根据相对路径得到相应图片/asset/thumbnail/abc.jpg
			String relativePath = req.getParameter("relativePath");
			apiAdaptor.getThumbnailFile(relativePath, res);

		}else if(action.equals(AppStarter.GETASSETFILE)){
			// 根据相对路径得到相应图片/asset/123.swf
			String relativePath = req.getParameter("relativePath");
			apiAdaptor.getAssetFile(relativePath, res);
			
		}else if(action.equals(AppStarter.GETRESOURCE)){
			String resId = req.getParameter("resId");
			String type = req.getParameter("type");
			apiAdaptor.getResource(resId,type,res);
		}
	}

	/**
	 * 处理微博接入相关api
	 * @param action
	 * @param req
	 * @param res
	 * @throws IOException 
	 */
//	private void weiboProcess(String action, HttpServletRequest req,
//			HttpServletResponse res) throws IOException {
//		
//	}

	
	@SuppressWarnings("unchecked")
	private void processMultiPart(HttpServletRequest req, PrintWriter pw) {
		try {
			log.debug(">>> Starting uploading...");
			List<FileItem> fileItems = (List<FileItem>) upload
					.parseRequest(req);
			log.debug("<<< Uploading complete!");
			
			if(GlobalController.isDebug){
//				String method = getMethod(fileItems);
//				if(method.equals("upload")){
//					apiAdaptor.createThumbnail(fileItems);
//				}
			}else{
				
		    	String method = getMethod(fileItems);
		    	
		    	//根据上传图片生成视频
			    if(method.equals(AppStarter.UPLOADFRAME)){
					String result = apiAdaptor.uploadFrame(fileItems);
					pw.write(result);
					
				//根据批量上传图片生成视频
			    }else  if(method.equals(AppStarter.UPLOADFRAMES)){
					String result = apiAdaptor.uploadFrames(fileItems);
					pw.write(result);
				
				//发图片到新浪微博
			    }else  if(method.equals(AppStarter.SHARETOWEIBO)){
					String result = apiAdaptor.shareToWeibo(fileItems);
					pw.write(result);
					
				//发图片到腾讯微博
			    }else  if(method.equals(AppStarter.SHARETOTENCENT)){
					String result = apiAdaptor.shareToTencent(fileItems);
					pw.write(result);
					
					
				 }else{
					
				 }
			}
			
		} catch (SizeLimitExceededException e) {
			log.debug(">>> File size exceeds the limit, can not upload!");
			pw.print(">>> File size exceeds the limit, can not upload!");
			return;

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	
	private String getMethod(List<FileItem> fileItems) {
		String method = "";
		Iterator<FileItem> iter = fileItems.iterator();
		while (iter.hasNext()) {
			FileItem item = iter.next();
			if (item.isFormField()) {
				if (item.getFieldName().equals("method")) {
					method = item.getString();
				}
			}
		}
		return method;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

		// ApplicationContext 已经准备好，Spring配置初始化完成，可以启动任务了
		if (event instanceof ContextRefreshedEvent) {

			log.debug(">>>>>>>> Server startup complete, automatic task started <<<<<<<");

			// 上传文件保存路径
			String filePath = System.getProperty("filePath");
			// 初始化文件上传组件参数
			String tempPath = System.getProperty("tempPath");
			if (tempPath != null) {
				log.debug(">>>>> init file upload component...");
				initUploadComponent(tempPath);
			} else {
				log.warn(">>>>> !!! File upload path tempPath environment variable is null, can not initialize the upload component!");
			}

			if (apiAdaptor != null) {
				log.debug(">>> apiAdaptor is ready to use...");
				// 将磁盘文件保存路径传进来
				if (filePath != null) {
					apiAdaptor.setImagePath(filePath);
				} else {
					log.warn(">>>>> !!! File upload path filePath environment variable is null, can not initialize the upload component!");
				}
			}
			
//			//为flash增加843的socket端口 socketFlash.jar
//			ServerFlex serverFlex = new ServerFlex();
//			serverFlex.runServerFlex();

		}

		// 处理关闭时发布的事件，停止所有的任务
		if (event instanceof ContextClosedEvent) {
			
		}

//		if(GlobalController.isDebug){
//			log.info("*********************************************");
//			log.info(">>>This app running in debug mode!!!");
//			log.info("*********************************************");
//		}else{
//			log.info("*********************************************");
//			log.info(">>>This app running in release mode!!!");
//			log.info("*********************************************");
//		}
		
	} // end of onApplicationEvent

	private void initUploadComponent(String tempPath) {
		DiskFileItemFactory diskFactory = new DiskFileItemFactory();
		// threshold 极限、临界值，即内存缓存 空间大小
		diskFactory.setSizeThreshold(fileMaxSize);
		// repository 贮藏室，即临时文件目录
		diskFactory.setRepository(new File(tempPath));
		upload = new ServletFileUpload(diskFactory);
		// 设置允许上传的最大文件大小 4M
		upload.setSizeMax(fileMaxSize);
	}

}
