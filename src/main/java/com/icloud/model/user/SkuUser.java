package com.icloud.model.user;

import java.util.Date;

public class SkuUser {
 
    
    private String id;
	
    private String skuName;
    
    private String skuCompanyName;
    
    private String skuPhone;
    
    private Date createTime;
    
    private String wxUserId;
    
    private String status;
    

    private String nick;

    private String wxHeadImg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuCompanyName() {
		return skuCompanyName;
	}

	public void setSkuCompanyName(String skuCompanyName) {
		this.skuCompanyName = skuCompanyName;
	}

	public String getSkuPhone() {
		return skuPhone;
	}

	public void setSkuPhone(String skuPhone) {
		this.skuPhone = skuPhone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	
    
}