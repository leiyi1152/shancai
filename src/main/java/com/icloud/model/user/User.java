package com.icloud.model.user;

import java.util.Date;

public class User {
    private String id;

    private String phone;
    
    private String companyName;
    
    private String name;
    
    private String wxUserId;
    
    private Date createTime;

    private String status;
    

    private String nick;

    private String wxHeadImg;


	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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