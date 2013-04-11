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
	
	//获取素材，包括flv
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
	
	//根据id取模板
	public static final String GETTEMPLATEBYID = "getTemplateById";
	
	//根据模板取分镜头
	public static final String GETSHOTBYTEMPLATE = "getShotByTemplate";
	
	//保存分镜头对白
	public static final String SAVESHOTDIALOGUE = "saveShotDialogue";
	
	//取成品动画，播放用
	public static final String GETDIALOGUEANIMATION = "getDialogueAnimation";
	
	//根据资源resId和type得图片流 
	public static final String GETRESOURCE = "getResource";
	
	//根据用户取成品
	public static final String GETMEMORYBYUSER = "getMemoryByUser";
	
	//删除某个成品动画
	public static final String DELETEMEMORY = "deleteMemory";
	
	//根据分页取成品
	public static final String GETMEMORY = "getMemory";
	
	//添加精彩案例
	public static final String SAVECASE = "saveCase";
	
	//删除精彩案例
	public static final String DELETECASE = "deleteCase";
	
	//获取案例数目
	public static final String GETCASECOUNT = "getCaseCount";
	
	//分页取案例
	public static final String GETCASE = "getCase";
	
	//获取模板的总数
	public static final String GETTEMPLATECOUNT = "getTemplateCount";
	
	//分页获取模板
	public static final String GETTEMPLATE = "getTemplate";
	
	//更新分镜头
	public static final String UPDATESHOT = "updateShot";
	
	//根据上传图片生成视频以供下载
	public static final String UPLOADFRAME = "uploadFrame";
	
	//根据批量上传图片生成视频以供下载
	public static final String UPLOADFRAMES = "uploadFrames";
	
	
	//---------------订制API------------------------------------------------------
	
	//添加订单
	public static final String ADDORDER = "addOrder";
	
	//修改订单状态
	public static final String UPDATEORDERSTATE = "updateOrderState";
	
	//删除一个订单
	public static final String DELETEORDER = "deleteOrder";
	
	//获取订单总数
	public static final String GETORDERCOUNT = "getOrderCount";
	
	//分页取订单
	public static final String GETORDER = "getOrder";
	
	
	//微博相关api----------------------------------------------------
	
	//操作微博账号
	public static final String OPERATEWEIBOUSER = "operateWeiboUser";
	
	//发送长图片到微博
	public static final String SHARETOWEIBO = "shareToWeibo";
	
	//发腾讯微博
	public static final String SHARETOTENCENT = "shareToTencent";
	
}
