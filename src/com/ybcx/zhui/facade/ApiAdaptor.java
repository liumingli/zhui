/**
 * 
 */
package com.ybcx.zhui.facade;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;





/**
 * Servlet调用服务的参数转换器，用来封装客户端参数并实现服务调用；
 * 
 * @author lwz
 * 
 */

public class ApiAdaptor {
	
	// 由Spring注入
	private ComicServiceInterface comicService;

	public void setComicService(ComicServiceInterface comicService) {
		this.comicService = comicService;
	}
	public ApiAdaptor() {

	}

	// 由AppStarter调用
	public void setImagePath(String filePath) {
		this.comicService.saveImagePathToProcessor(filePath);
	}

	public String createThumbnail(List<FileItem> fileItems) {
		FileItem sourceData = null;
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem item = fileItems.get(i);
			if (!item.isFormField()) {
				//图片数据
				sourceData = item;
			}
		}
		String imgPath = comicService.createAdImg(sourceData);
		return imgPath;
	}
	
	public String createAnimation(List<FileItem> fileItems) {
		FileItem shotData = null;
		String userId = "";
		String name = "";
		String content = "";
		
		for (int i = 0; i < fileItems.size(); i++) {
			FileItem item = fileItems.get(i);
			if (!item.isFormField()) {
				//图片数据
				shotData = item;
			}
			
			if (item.isFormField()) {
				
				if (item.getFieldName().equals("userId")) {
					userId = item.getString();
				}
				
				if (item.getFieldName().equals("name")) {
					try {
						name = item.getString("UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
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
	
		String result = comicService.createAnimation(shotData,userId,name,content);
		
		return result;
		
	}
	
	public void getThumbnailFile(String relativePath, HttpServletResponse res) {
		comicService.getThumbnailFile(relativePath,res);
	}
	
	public void getAssetFile(String relativePath, HttpServletResponse res) {
		comicService.getAssetFile(relativePath,res);
	}
	
	public void dialogueToImage(String dialogue, String fontSize,
		 String isBold, String width, String height, HttpServletResponse res) {
		comicService.dialogueToImage(dialogue,fontSize,isBold,width,height,res);
	}
	
	

} // end of class
