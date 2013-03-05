/**
 * 
 */
package com.ybcx.zhui.facade;

/**
 * 这里定义外部访问接口需要的常量字符串
 *
 */
public interface ExtVisitorInterface {
	
    //登录后台管理系统
    public static final String LOGINSYSTEM = "loginSystem";
	
	//根据文字生成图片
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
	
	//根据分类取模板
	public static final String GETTEMPLATEBYCATEGORY = "getTemplateByCategory";
	
	//根据模板取分镜头
	public static final String GETSHOTBYTEMPLATE = "getShotByTemplate";
	
	//保存分镜头对白
	public static final String SAVESHOTDIALOGUE = "saveShotDialogue";
	
	//取成品动画
	public static final String GETDIALOGUEANIMATION = "getDialogueAnimation";
	
	//根据资源resId和type得图片流 
	public static final String GETRESOURCE = "getResource";
}
