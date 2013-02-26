package com.ybcx.zhui.beans;
/**
 * 
 * @author lml
 *
 *	分镜头
 */
public class Shot {
	
	private String id;
	private String swf;
	private String thumbnail;
	private String template;
	private int frame;
	private int bubble;
	private String bubbleSize;
	private String createTime;
	private int enable;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSwf() {
		return swf;
	}
	public void setSwf(String swf) {
		this.swf = swf;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public int getFrame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
	public int getBubble() {
		return bubble;
	}
	public void setBubble(int bubble) {
		this.bubble = bubble;
	}
	public String getBubbleSize() {
		return bubbleSize;
	}
	public void setBubbleSize(String bubbleSize) {
		this.bubbleSize = bubbleSize;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	
	
}
