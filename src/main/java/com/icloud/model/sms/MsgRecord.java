package com.icloud.model.sms;

import java.util.Date;

/**
 * 消息通知记录
 * @author leiyi
 *
 */
public class MsgRecord {
	
	private String id;
	private Date createTime;
	private String status;//0发送失败 1成功
	private String msgContent;//消息内容
	private String type;//0模板消息 1短信
	private String receiveObj;//openid+nick 或者手机号 
	private Date modifyTime;
	private String url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReceiveObj() {
		return receiveObj;
	}
	public void setReceiveObj(String receiveObj) {
		this.receiveObj = receiveObj;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
