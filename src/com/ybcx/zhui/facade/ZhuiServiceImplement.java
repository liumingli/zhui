package com.ybcx.zhui.facade;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.ybcx.zhui.beans.Case;
import com.ybcx.zhui.beans.Dialogue;
import com.ybcx.zhui.beans.Memory;
import com.ybcx.zhui.beans.Order;
import com.ybcx.zhui.beans.Shot;
import com.ybcx.zhui.beans.Template;
import com.ybcx.zhui.dao.DBAccessInterface;
import com.ybcx.zhui.tools.DeleteFile;
import com.ybcx.zhui.tools.FfmpegProcess;
import com.ybcx.zhui.utils.ZhuiUtils;

public class ZhuiServiceImplement implements ZhuiServiceInterface {

	private static Logger log = Logger.getLogger(ZhuiServiceImplement.class);
	
	// 由Spring注入
	private DBAccessInterface dbVisitor;
	
	public void setDbVisitor(DBAccessInterface dbVisitor) {
		this.dbVisitor = dbVisitor;
	}

	private String imagePath;
	
	@Override
	public void saveImagePathToProcessor(String filePath) {
	//	this.imgProcessor.setImagePath(filePath);
		imagePath = filePath;
	}
	
	private Properties systemConfigurer;

	public void setSystemConfigurer(Properties systemConfigurer) {
		this.systemConfigurer = systemConfigurer;
	}
	
	// 设定输出的类型 MIME TYPE
	private static final String GIF = "image/gif;charset=UTF-8";

	private static final String JPG = "image/jpeg;charset=UTF-8";

	private static final String PNG = "image/png;charset=UTF-8";
	
	private static final String SWF = "application/x-shockwave-flash;charset=UTF-8";
		
	private static final String FLV = "flv-application/octet-stream;charset=UTF-8";

	@Override
	public void getThumbnailFile(String relativePath, HttpServletResponse res) {
		try {
			//默认
			File defaultImg = new File(imagePath + File.separator + "default.png");
			InputStream defaultIn = new FileInputStream(defaultImg);
			
			String type = relativePath.substring(relativePath.lastIndexOf(".") + 1);
			File file = new File(imagePath+File.separator+relativePath);
			
			if (file.exists()) {
				InputStream imageIn = new FileInputStream(file);
				if (type.toLowerCase().equals("jpg") || type.toLowerCase().equals("jpeg")) {
					writeJPGImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("png")) {
					writePNGImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("gif")) {
					writeGIFImage(imageIn, res, file);
				} else {
					writePNGImage(defaultIn, res, file);
				}
			} else {
				writePNGImage(defaultIn, res, defaultImg);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void writeJPGImage(InputStream imageIn, HttpServletResponse res, File file) {
		try {
//			res.addHeader("content-length",String.valueOf(file.length()));
			res.setContentType(JPG);
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
			// 得到编码后的图片对象
			BufferedImage image = decoder.decodeAsBufferedImage();
			// 得到输出的编码器
			OutputStream out = res.getOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			// 对图片进行输出编码
			imageIn.close();
			// 关闭文件流
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writePNGImage(InputStream imageIn, HttpServletResponse res, File file) {
//		res.addHeader("content-length",String.valueOf(file.length()));
		res.setContentType(PNG);
		getOutInfo(imageIn, res);
	}

	private void writeGIFImage(InputStream imageIn, HttpServletResponse res, File file) {
//		res.addHeader("content-length",String.valueOf(file.length()));
		res.setContentType(GIF);
		getOutInfo(imageIn, res);
	}

	private void getOutInfo(InputStream imageIn, HttpServletResponse res) {
		try {
			OutputStream out = res.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(imageIn);
			// 输入缓冲流
			BufferedOutputStream bos = new BufferedOutputStream(out);
			// 输出缓冲流
			byte data[] = new byte[4096];
			// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();
			// 清空输出缓冲流
			bos.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	@Override
	public void getAssetFile(String relativePath, HttpServletResponse res) {
		try {
			//默认
			File defaultImg = new File(imagePath + File.separator + "default.png");
			InputStream defaultIn = new FileInputStream(defaultImg);
			
			String type = relativePath.substring(relativePath.lastIndexOf(".") + 1);
			File file = new File(imagePath+File.separator +relativePath);
			
			if (file.exists()) {
				InputStream imageIn = new FileInputStream(file);
				if(type.toLowerCase().equals("swf")){
					writeSWF(imageIn,res,file);
				}else if (type.toLowerCase().equals("jpg") || type.toLowerCase().equals("jpeg")) {
					writeJPGImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("png")) {
					writePNGImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("gif")) {
					writeGIFImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("flv")) {
					writeFlvVideo(imageIn, res, file);
				} else {
					writePNGImage(defaultIn, res, file);
				}
			} else {
				writePNGImage(defaultIn, res, defaultImg);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeFlvVideo(InputStream imageIn, HttpServletResponse res,
			File file) {
		res.setContentType(FLV);
		res.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\""); 
		getOutInfo(imageIn, res);
	}

	private void writeSWF(InputStream imageIn, HttpServletResponse res, File file) {
//		res.addHeader("content-length",String.valueOf(file.length()));
		res.setContentType(SWF);
		getOutInfo(imageIn, res);
	}
	
	@Override
	public void getResource(String resId, String type, HttpServletResponse res) {
		//先根据资源类型查询出图片路径
		String filePath = "";
		if(("templateSwf").equals(type)){
			filePath = dbVisitor.getTemplateFilePath(resId);
		}else if(("templateImage").equals(type)){
			filePath = dbVisitor.getTemplateImagePath(resId);
		}else if(("shot").equals(type)){//只取swf
			filePath = dbVisitor.getShotFilePath(resId);
		}else if(("dialogue").equals(type)){//只取image
			filePath = dbVisitor.getDialogueFilePath(resId);
		}else{
			filePath= "default.png";
		}
		
		//根据路径查询图片
		try {
			String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
			File file = new File(imagePath+File.separator +filePath);
			if (file.exists()) {
				InputStream imageIn = new FileInputStream(file);
				if(fileType.toLowerCase().equals("swf")){
					writeSWF(imageIn,res,file);
				}else if (fileType.toLowerCase().equals("jpg") || type.toLowerCase().equals("jpeg")) {
					writeJPGImage(imageIn, res, file);
				} else if (fileType.toLowerCase().equals("png")) {
					writePNGImage(imageIn, res, file);
				} else if (fileType.toLowerCase().equals("gif")) {
					writeGIFImage(imageIn, res, file);
				} 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void dialogueToImage(String userId,String dialogue, String width, String height,HttpServletResponse res) {
		//将文字生成png图片并返回
		res.setContentType(PNG);
		//创建预览临时保存路径
		String previewPath = imagePath +File.separator +"template"+File.separator+userId;
		File fp = new File(previewPath);
		if (!fp.exists()){
			fp.mkdir();
		} 
		String filePath = createDialogueImage(previewPath,dialogue,Integer.parseInt(width),Integer.parseInt(height));
		//log.info("The new image path is "+filePath);
		File file = new File(filePath);
		if (file.exists()) {
			try {
				InputStream imageIn = new FileInputStream(file);
				writeGIFImage(imageIn, res, file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
		}
	}

	
	private String createDialogueImage(String savePath,String dialogue, int width, int height ) {
		//命名新图片文件
		String fileName = System.currentTimeMillis() + ".png";
		String path = savePath + File.separator + fileName;
		File file = new File(path);

		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 设置背景透明
		Graphics2D g2 = bi.createGraphics();
		bi = g2.getDeviceConfiguration().createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		g2.dispose();
		g2 = bi.createGraphics();

		/** 设置生成图片的文字样式 * */
		String fontName =  systemConfigurer.getProperty("fontName").toString();
		int fontSize = Integer.parseInt( systemConfigurer.getProperty("fontSize").toString());
		Font font = new Font(fontName, Font.BOLD, fontSize);
		g2.setFont(font);
		//转换十六进制色值为十进制
		// Color color = new Color(3,3,3);
		g2.setPaint(Color.black);
		
		/**文字拆分换行*/
		//一行最长宽度
		int maxWidth = width;
		//存放文字拆分后的数组
		List<String> strList = new ArrayList<String>();
		
		FontMetrics metrics = g2.getFontMetrics(g2.getFont()); 
	    int strHeight = metrics.getHeight(); 
		
		int currWidth  = 0;
		StringBuffer sb = new StringBuffer();
		//记录是否超过一行
		boolean hasSave = false;
		
		for(int i=0; i<dialogue.length(); i++) {
			char s = dialogue.charAt(i);
			int sWidth = metrics.stringWidth(String.valueOf(s));
			currWidth = currWidth+sWidth;
			sb.append(s);
			
			//如果超过了一行了，就保存 4是左右间距
			if(currWidth <= maxWidth-4 && currWidth >=maxWidth-4-sWidth ) {
				currWidth = 0;
				String rowStr = sb.toString();
				strList.add(rowStr);
				sb.setLength(0);
				hasSave = true;
			}
		}
		
		//如果不到一行，直接保存
		if(!hasSave){
			strList.add(dialogue);
		} else{ //把剩下的不够一行的保存下来
			String rowStr2 = sb.toString();
			strList.add(rowStr2);
		}
		
		/** 防止生成的文字带有锯齿 * */
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		/** 在图片上生成文字 * */
		for(int j=0; j<strList.size(); j++) {
			g2.drawString(strList.get(j), 4, (j+1)*strHeight);
		}

		try {
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getPath();
	}

	@Override
	public String saveTemplate(String name, String swf, String thumbnail,
			String type) {
		boolean flag = false;
		String templateId = ZhuiUtils.generateUID();
		Template template = this.generateTemplate(templateId,name,swf,thumbnail,type);
		int res = dbVisitor.saveTemplate(template);
		if(res > 0){
			return templateId;
		}else{
			return String.valueOf(flag);
		}
	}

	private Template generateTemplate(String templateId,String name, String swf,
			String thumbnail, String type) {
		Template template = new Template();
		template.setId(templateId);
		template.setName(name);
		template.setSwf(swf);
		template.setThumbnail(thumbnail);
		template.setType(type);
		template.setEnable(1);
		template.setCreateTime(ZhuiUtils.getFormatNowTime());
		return template;
	}

	@Override
	public String deleteTemplate(String templateId) {
		boolean flag = false;
		//删除模板
		int rows = dbVisitor.deleteTemplate(templateId);
		//删除分镜头
		int dels = dbVisitor.deleteShotByTemplate(templateId);
		
		//删除磁盘目录下的模板文件
		deleteTemplateFile(templateId);
		
		if(rows ==1 &&  dels > 0){
			flag = true;
		}
		return String.valueOf(flag);
	}
	
	private void deleteTemplateFile(String templateId){
		List<String> pathList = new ArrayList<String>();
		//删除模板文件
		Template template = dbVisitor.getTemplateById(templateId);
		String tSwfPath =  imagePath +File.separator+template.getSwf();
		String tImgPath =  imagePath +File.separator+ template.getThumbnail();
		pathList.add(tSwfPath);
		pathList.add(tImgPath);
		 //删除分镜头
		List<Shot> shotList = dbVisitor.getShotByTemplate(templateId);
		for(int i=0;i<shotList.size();i++){
			Shot sh = shotList.get(i);
			String swfPath = sh.getSwf();
			String imgPath = sh.getThumbnail();
			pathList.add(swfPath);
			pathList.add(imgPath);
		}
		
		ZhuiUtils.deleteFileByPath(pathList);
	}

	@Override
	public String saveShot(String name, String swf, String thumbnail,
			String template, String frame, String bubble, String bubbleSize) {
		boolean flag = false;
		Shot shot = this.generateShot(name,swf,thumbnail,template,frame,bubble,bubbleSize);
		int res = dbVisitor.saveShot(shot);
		if(res > 0){
			flag = true;
		}
		return String.valueOf(flag);
	}

	private Shot generateShot(String name, String swf, String thumbnail,
			String template, String frame, String bubble, String bubbleSize) {
		Shot shot = new Shot();
		shot.setId(ZhuiUtils.generateUID());
		shot.setName(name);
		shot.setSwf(swf);
		shot.setThumbnail(thumbnail);
		shot.setTemplate(template);
		shot.setFrame(Integer.parseInt(frame));
		shot.setBubble(Integer.parseInt(bubble));
		shot.setBubbleSize(bubbleSize);
		shot.setCreateTime(ZhuiUtils.getFormatNowTime());
		shot.setEnable(1);
		return shot;
	}

	@Override
	public String deleteShot(String shotId) {
		boolean flag = false;
		int rows = dbVisitor.deleteShot(shotId);
		
		//删除磁盘目录下的分镜头文件
		deleteShotFile(shotId);
		
		if(rows ==1){
			flag = true;
		}
		return String.valueOf(flag);
	}
	
	private void deleteShotFile(String shotId){
		List<String> list = new ArrayList<String>();
		Shot shot = dbVisitor.getShotById(shotId);
		String swfPath = imagePath +File.separator+shot.getSwf();
		String imgPath = imagePath +File.separator+shot.getThumbnail();
		list.add(swfPath);
		list.add(imgPath);
		ZhuiUtils.deleteFileByPath(list);
	}

	@Override
	public String loginSystem(String account, String password) {
		boolean flag = false;
		String user = systemConfigurer.getProperty("account");
		String pwd = systemConfigurer.getProperty("password");
		if(account.equals(user) && password.equals(pwd)){
			flag = true;
		}
		return String.valueOf(flag);
	}

	@Override
	public List<Template> getTemplateByCategory(String type, String pageNum,
			String pageSize) {
		List<Template> list = dbVisitor.getTemplateByCategory(type,Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		return list;
	}

	@Override
	public List<Shot> getShotByTemplate(String templateId) {
		List<Shot> list = dbVisitor.getShotByTemplate(templateId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveShotDialogue(String userId,String templateId,String content) {
		StringBuffer dialogueIds = new StringBuffer();
		StringBuffer frames = new StringBuffer();
		//content {"shotId": "dialogue", "key": "value", ....}
		//解析content，首先取出shotId及dialogue，
		 JSONObject jo = JSONObject.fromObject(content);
		 Iterator<String> iter = jo.keys();
         while(iter.hasNext()) {  
        	 //分镜头id---shotId
            String shotId = iter.next().toString();  
            Shot shot = dbVisitor.getShotById(shotId);
            //再根据shotId取出size和frame等信息
            if(shot.getId() !=null && shot.getBubble() == 1){
            	int width = 0;
            	int height = 0;
        		String[] arr = shot.getBubbleSize().split("[*]");
        		width = Integer.parseInt(arr[0]);
        		height = Integer.parseInt(arr[1]);
            	
        		//该分镜头对应的对白内容
            	String dialogue = jo.get(shot.getId()).toString();
            	
            	//生成文字图片，并存相对路径
            	String dialogueImgPath = imagePath+File.separator+"template"+File.separator+"dialogue";
            	File fp = new File(dialogueImgPath);
            	if(!fp.exists()){
            		fp.mkdir();
            	}
            	String imgPath = this.createDialogueImage(dialogueImgPath, dialogue, width, height);
    			String relativePath =  ZhuiUtils.processFilepath(imgPath);
            	
            	int frame = shot.getFrame();
            	if (frames.length() > 0) {
            		frames.append(",");
				}
            	frames.append(String.valueOf(frame));
            	
            	//生成对白，并将多个对白id以逗号分隔用于保存
            	String dialogueId = this.saveDialogue(dialogue, relativePath, shotId, frame);
            	if (dialogueIds.length() > 0) {
            		dialogueIds.append(",");
				}
            	dialogueIds.append(dialogueId);
            }
         }  
         
         //保存成品
        boolean saveResult = this.saveMemory(userId,templateId,dialogueIds.toString(),frames.toString());
      //这里保存成功后，需删除以id命名用来保存预览图片的临时文件夹
        if(saveResult){
        	String tempPath = imagePath + File.separator +"template"+ File.separator +userId;
        	deleteTempFile(tempPath);
        }
		return String.valueOf(saveResult);
	}
	
	private static boolean deleteTempFile(String tempPath){
		boolean result = true;
		DeleteFile delFile = new DeleteFile();
		File targetFolder = new File(tempPath);
		if (targetFolder.exists() && targetFolder.isDirectory()) {
			//文件夹，
			result = delFile.delFolder(tempPath);
			if(result) log.info ("folder deleted: "+tempPath);
		}else{
			//文件直接删除
			result = delFile.delAllFile(tempPath);
			if(result) log.info("file deleted: "+tempPath);
		}
		return result;
	}

	//保存成品 
	private boolean saveMemory(String userId, String templateId, String dialogueIds, String frames) {
		boolean flag = false;
		Memory memory = this.generateMemory(userId,templateId,dialogueIds,frames);
		int res = dbVisitor.saveMemory(memory);
		if(res > 0){
			flag = true;
		}
		return flag;
	}
	
	private Memory generateMemory(String userId, String templateId,
			String dialogueIds, String frames) {
		Memory memory = new Memory();
		memory.setId(ZhuiUtils.generateUID());
		memory.setUser(userId);
		memory.setTemplate(templateId);
		memory.setDialogues(dialogueIds);
		memory.setFrames(frames);
		memory.setCreateTime(ZhuiUtils.getFormatNowTime());
		memory.setEnable(1);
		memory.setVideo("");
		return memory;
	}

	//保存对白
	private String saveDialogue(String content, String image, String shot,
			int frame) {
		Dialogue dialogue = this.generateDialogue(content,image,shot,frame);
		int res = dbVisitor.saveDialogue(dialogue);
		if(res > 0){
			return dialogue.getId();
		}else{
			return "false";
		}
	}

	private Dialogue generateDialogue(String content, String image,
			String shot, int frame) {
		Dialogue dialogue = new Dialogue();
		dialogue.setId(ZhuiUtils.generateUID());
		dialogue.setContent(content);
		dialogue.setImage(image);
		dialogue.setShot(shot);
		dialogue.setFrame(frame);
		dialogue.setCreateTime(ZhuiUtils.getFormatNowTime());
		return dialogue;
	}

	@Override
	public Memory getDialogueAnimation(String memoryId) {
		Memory memory = dbVisitor.getDialogueAnimation(memoryId);
		return memory;
	}

	@Override
	public List<Memory> getMemoryByUser(String userId, String pageNum,
			String pageSize) {
		List<Memory> list = dbVisitor.getMemoryByUser(userId,Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		return list;
	}

	@Override
	public String saveCase(String name, String description, String swf,
			String thumbnail) {
		boolean flag = false;
		Case cas = this.generateCase(name,description,swf,thumbnail);
		int res = dbVisitor.saveCase(cas);
		if(res > 0){
			flag = true;
		}
		return String.valueOf(flag);
	}

	private Case generateCase(String name, String description, String swf,
			String thumbnail) {
		Case cas = new Case();
		cas.setId(ZhuiUtils.generateUID());
		cas.setName(name);
		cas.setDescription(description);
		cas.setSwf(swf);
		cas.setThumbnail(thumbnail);
		cas.setDeliverTime(ZhuiUtils.getFormatNowTime());
		cas.setEnable(1);
		return cas;
	}

	@Override
	public String deleteCase(String caseId) {
		boolean flag = false;
		int res = dbVisitor.deleteCase(caseId);
		//删除磁盘目录下的案例原文件
		deleteCaseFile(caseId);
		
		if(res > 0){
			flag = true;
		}
		return String.valueOf(flag);
	}

	private void deleteCaseFile(String caseId){
		List<String> list = new ArrayList<String>();
		//完整磁盘路径
		Case ca= dbVisitor.getCaseById(caseId);
		String swfPath =  imagePath +File.separator+ca.getSwf();
		String imgPath =  imagePath +File.separator+ca.getThumbnail();
		list.add(swfPath);
		list.add(imgPath);
		ZhuiUtils.deleteFileByPath(list);
	}
	
	@Override
	public int getCaseCount() {
		int result = dbVisitor.getCaseCount();
		return result;
	}


	@Override
	public List<Case> getCase(String pageNum, String pageSize) {
		List<Case> list = dbVisitor.getCase(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		return list;
	}

	@Override
	public int getTemplateCount(String type) {
		int result = 0;
		if("all".equals(type)){
			result = dbVisitor.getTemplateCount();
		}else{
			result = dbVisitor.getTemplateCountByType(type);
		}
		return result;
	}

	@Override
	public List<Template> getTemplate(String pageNum, String pageSize) {
		List<Template> list = dbVisitor.getTemplate(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		return list;
	}

	@Override
	public String updateShot(String shotId, String frame, String bubbleSize) {
		boolean flag = false;
		int res = dbVisitor.updateShot(shotId,Integer.parseInt(frame),bubbleSize);
		if(res > 0){
			flag = true;
		}
		return String.valueOf(flag);
	}

	@Override
	public String deleteMemory(String memoryId) {
		boolean flag = false;
		int rows = dbVisitor.deleteMemory(memoryId);
		
		//删除磁盘目录下的对白图片文件
		deleteDialogueByMemory(memoryId);
		
		if(rows ==1){
			flag = true;
		}
		return String.valueOf(flag);
	}

	private void deleteDialogueByMemory(String memoryId) {
		Memory memory = dbVisitor.getMemoryById(memoryId);
		String dialogueIds = memory.getDialogues();
		String[] arr = dialogueIds.split(",");
		for(int i=0;i<arr.length;i++){
			String dialogueId = arr[i];
			//删除磁盘文件
			String imgPath = dbVisitor.getDialogueFilePath(dialogueId);
			File file = new File(imagePath+File.separator+imgPath);
			if(file.exists()){
				boolean  res =file.delete();
				if(res){
					log.info("delete dialogue image success");	
				}
			}
			//删除数据库记录
			int rows = dbVisitor.deleteDialogue(dialogueId);
			if(rows > 0){
				log.info("delete dialogue db record success");
			}
		}
		
	}

	@Override
	public List<Memory> getMemory(String pageNum, String pageSize) {
		List<Memory> list = dbVisitor.getMemory(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		return list;
	}

	@Override
	public String addOrder(String person, String category, String template,
			String style, String music, String mins, String tips,
			String entity, String name, String phone, String email,
			String address) {
		boolean flag = false;
		Order order = this.generateOrder(person,category,template,style,music,mins,tips,entity,name,phone,email,address);
		int rows = dbVisitor.addOrder(order);
		if(rows > 0){
			flag = true;
		}
		return String.valueOf(flag);
	}

	private Order generateOrder(String person, String category,
			String template, String style, String music, String mins,String tips, 
			String entity, String name, String phone,String email, String address) {
		Order order = new Order();
		order.setId(ZhuiUtils.generateUID());
		order.setPerson(person);
		order.setCategory(category);
		order.setTemplate(template);
		order.setStyle(style);
		order.setMins(Integer.parseInt(mins));
		order.setMusic(Integer.parseInt(music));
		order.setEntity(Integer.parseInt(entity));
		order.setState(-1);
		order.setTips(tips);
		order.setOwner(name);
		order.setEmail(email);
		order.setPhone(phone);
		order.setAddress(address);
		order.setCreateTime(ZhuiUtils.getFormatNowTime());
		order.setEnable(1);
		return order;
	}

	@Override
	public String updateOrderState(String orderId, String state) {
		boolean flag = false;
		int res = dbVisitor.updateOrderState(orderId,Integer.parseInt(state));
		if(res > 0){
			flag = true;
		}
		return String.valueOf(flag);
	}

	@Override
	public String deleteOrder(String orderId) {
		boolean flag = false;
		int res = dbVisitor.deleteOrder(orderId);
		if(res > 0){
			flag = true;
		}
		return String.valueOf(flag);
	}

	@Override
	public List<Order> getOrder(String pageNum, String pageSize) {
		List<Order> list = dbVisitor.getOrder(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		return list;
	}

	@Override
	public String saveVideoImage(FileItem imgData, String memoryId, String status) {

			//先初始化存储位置
			String imgFolder = imagePath+File.separator+"template"+File.separator+"video"+File.separator+memoryId;
			File fp = new File(imgFolder);
			if(!fp.exists()){
				fp.mkdir();
			}
			String fileName = imgData.getName();
			String path = imgFolder +File.separator+fileName;
			try {
				BufferedInputStream in = new BufferedInputStream(imgData.getInputStream());
				// 获得文件输入流
				BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(path)));// 获得文件输出流
				Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			if("complete".equals(status)){
				String videoAddress = this.convertImageToVideo(imgFolder,memoryId);
				//更新数据库
				if(!"false".equals(videoAddress)){
					dbVisitor.updateMemoryVideo(memoryId,videoAddress);
				}
				return videoAddress;
			}else{
				//上传成功，则插入数据库
				if (new File(path).exists()) {
					return ZhuiUtils.processFilepath(path);
				}else{
					return "false";
				}
			}
	}
	
	//根据图片生成video
	private String convertImageToVideo(String imgFolder,String memeoryId){
		String ffmpegPath =systemConfigurer.getProperty("ffmpegPath");
		//最后一张则去调用生成视频，并返回拼好的地址
		String videoPath = imagePath+File.separator+"template"+File.separator+"video"+ File.separator+memeoryId+".flv";
		
		FfmpegProcess.imageToVideo(ffmpegPath, imgFolder, videoPath);
		
		if(new File(videoPath).exists()){
			//删除图片文件夹
			DeleteFile delFile = new DeleteFile();
			delFile.delFolder(imgFolder);
			
			return ZhuiUtils.processFilepath(videoPath);
		}else{
			return "false";
		}
		
	}

}
