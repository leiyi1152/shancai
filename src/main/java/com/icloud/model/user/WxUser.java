package com.icloud.model.user;

import java.util.Date;

public class WxUser {
    private String id;

    private String openId;

    private String nick;

    private String wxHeadImg;
    
    
    private String currentType;
    
    private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getWxHeadImg() {
		return wxHeadImg;
	}

	public void setWxHeadImg(String wxHeadImg) {
		this.wxHeadImg = wxHeadImg;
	}

	public String getCurrentType() {
		return currentType;
	}

	public void setCurrentType(String currentType) {
		this.currentType = currentType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    
}