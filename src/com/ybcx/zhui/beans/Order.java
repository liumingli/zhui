package com.ybcx.zhui.beans;
/**
 * 
 * @author lml
 *	订单表
 */
public class Order {

	private String id;
	private String person;
	private String category;
	private String template;
	private String style;
	private int mins;
	private int music;
	private int entity;
	private String tips;
	private int state;
	private String owner;
	private String email;
	private String phone;
	private String address;
	private String createTime;
	private int enable;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public int getMins() {
		return mins;
	}
	public void setMins(int mins) {
		this.mins = mins;
	}
	public int getMusic() {
		return music;
	}
	public void setMusic(int music) {
		this.music = music;
	}
	public int getEntity() {
		return entity;
	}
	public void setEntity(int entity) {
		this.entity = entity;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getEnable() {
		return enable;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	
}
