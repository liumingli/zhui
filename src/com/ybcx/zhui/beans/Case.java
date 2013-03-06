package com.ybcx.zhui.beans;
/**
 * 
 * @author lml
 *
 *	精彩案例
 */
public class Case {
	private String id;
	private String name;
	private String description;
	private String swf;
	private String thumbnail;
	private String deliverTime;
	private int enable;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	
}
