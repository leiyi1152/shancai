package com.icloud.common.dto;

public class UserSession {

//	private Member member;//登录用户对象
	private String session_key;//
	private Long loginTime;//登录时间
	private Long expairtime=300000L;//  有效时间长度(5分钟)
	private String openId;
	
	
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	
	public Long getExpairtime() {
		return expairtime;
	}
	public void setExpairtime(Long expairtime) {
		this.expairtime = expairtime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
	
}
