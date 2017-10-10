package com.icloud.model.bms;

import java.util.Date;

import com.icloud.model.BaseModel;

public class Tadmin extends BaseModel{
    private String id;

    private String account;

    private String passWord;

    private String isLock;

    private String passwordPrompt;
    
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock == null ? null : isLock.trim();
    }

    public String getPasswordPrompt() {
        return passwordPrompt;
    }

    public void setPasswordPrompt(String passwordPrompt) {
        this.passwordPrompt = passwordPrompt == null ? null : passwordPrompt.trim();
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
    
}