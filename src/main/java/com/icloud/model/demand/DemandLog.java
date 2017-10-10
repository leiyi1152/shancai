package com.icloud.model.demand;

import java.util.Date;
/**
 * 需求变更日志
 * @author leiyi
 *
 */
public class DemandLog {
    private String id;

    private String demandId;

    private String logUser;
    private String logInfo;

    private Date createTime;

    private String demandStatus;
    

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


    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo == null ? null : logInfo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDemandStatus() {
        return demandStatus;
    }

    public void setDemandStatus(String demandStatus) {
        this.demandStatus = demandStatus == null ? null : demandStatus.trim();
    }

	public String getLogUser() {
		return logUser;
	}

	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

    
    
}