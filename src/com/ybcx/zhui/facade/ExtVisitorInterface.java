/**
 * 
 */
package com.ybcx.zhui.facade;

/**
 * 这里定义外部访问接口需要的常量字符串
 *
 */
public interface ExtVisitorInterface {
	
	
	//根据文字生成图片（用于测试，要外加包装）
	public static final String DIALOGUETOIMAGE = "dialogueToImage";
	
	//获取素材
	public static final String GETASSETFILE ="getAssetFile";
	
	//获取素材缩略图
	public static final String GETTHUMBNAIL ="getThumbnail";
	
	
	//动画模板上传保存
	public static final String SAVETEMPLATE = "saveTemplate";
    
	//动画模板删除 (删除模板的同时也要删除它的分镜头)
	public static final String DELETETEMPLATE = "deleteTemplate";
	
	//分镜头上传保存
	public static final String SAVESHOT = "saveShot";
	
	//删除某一分镜头
	public static final String DELETESHOT = "deleteShot";
	
	//保存对白（用于测试，要外加包装）
	public static final String SAVEDIALOGUE = "saveDialogue";
	
}
