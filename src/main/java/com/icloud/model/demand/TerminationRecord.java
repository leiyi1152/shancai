package com.icloud.model.demand;

import java.util.Date;

import com.icloud.model.user.SkuUser;
import com.icloud.model.user.User;
/**
 * 需求终止申请记录表
 * @author leiyi
 *
 */
public class TerminationRecord {
    private String id;

    private String demandId;

    private Date createTime;

    private String applyReson;

    private String picCredentials;

    private String applyUser;

    private String status;

    private String refuseReason;

    private Date modifyTime;
    
    private String applyRole;
    
    private User user;
    
    private SkuUser skuUser;
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId == null ? null : demandId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApplyReson() {
        return applyReson;
    }

    public void setApplyReson(String applyReson) {
        this.applyReson = applyReson == null ? null : applyReson.trim();
    }

    public String getPicCredentials() {
        return picCredentials;
    }

    public void setPicCredentials(String picCredentials) {
        this.picCredentials = picCredentials == null ? null : picCredentials.trim();
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser == null ? null : applyUser.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

	public String getApplyRole() {
		return applyRole;
	}

	public void setApplyRole(String applyRole) {
		this.applyRole = applyRole;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SkuUser getSkuUser() {
		return skuUser;
	}

	public void setSkuUser(SkuUser skuUser) {
		this.skuUser = skuUser;
	}
	
	
    
}