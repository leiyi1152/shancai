package com.icloud.model.demand;

import java.util.Date;

import com.icloud.model.category.Category;
/**
 * 需求基本信息表
 * @author leiyi
 *
 */
public class Demand {
    private String id;

    private String demandDesc;

    private String demandPics;

    private String productName;

    private String categoryId;

    private String publishedUser;

    private String publishedPhone;

    private String publishedName;

    private Date pushlishedTime;

    private Date modifyTime;

    private String demandStatus;

    private String responseId;

    private Date responseTime;
    
    private Category category;
    
    private String terminationStatus;//终止时的状态，当需求不允许终止时 状态回滚记录
    
    
    
    //查询使用
    private Date pushlishedStartTime;
    private Date pushlishedEndTime;
    //是否有新留言
    private boolean newComment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc == null ? null : demandDesc.trim();
    }

    public String getDemandPics() {
        return demandPics;
    }

    public void setDemandPics(String demandPics) {
        this.demandPics = demandPics == null ? null : demandPics.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getPublishedUser() {
        return publishedUser;
    }

    public void setPublishedUser(String publishedUser) {
        this.publishedUser = publishedUser == null ? null : publishedUser.trim();
    }

    public String getPublishedPhone() {
        return publishedPhone;
    }

    public void setPublishedPhone(String publishedPhone) {
        this.publishedPhone = publishedPhone == null ? null : publishedPhone.trim();
    }

    public String getPublishedName() {
        return publishedName;
    }

    public void setPublishedName(String publishedName) {
        this.publishedName = publishedName == null ? null : publishedName.trim();
    }

    public Date getPushlishedTime() {
        return pushlishedTime;
    }

    public void setPushlishedTime(Date pushlishedTime) {
        this.pushlishedTime = pushlishedTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDemandStatus() {
        return demandStatus;
    }

    public void setDemandStatus(String demandStatus) {
        this.demandStatus = demandStatus == null ? null : demandStatus.trim();
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId == null ? null : responseId.trim();
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

	public Date getPushlishedStartTime() {
		return pushlishedStartTime;
	}

	public void setPushlishedStartTime(Date pushlishedStartTime) {
		this.pushlishedStartTime = pushlishedStartTime;
	}

	public Date getPushlishedEndTime() {
		return pushlishedEndTime;
	}

	public void setPushlishedEndTime(Date pushlishedEndTime) {
		this.pushlishedEndTime = pushlishedEndTime;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTerminationStatus() {
		return terminationStatus;
	}

	public void setTerminationStatus(String terminationStatus) {
		this.terminationStatus = terminationStatus;
	}

	public boolean isNewComment() {
		return newComment;
	}

	public void setNewComment(boolean newComment) {
		this.newComment = newComment;
	}

	
    
}