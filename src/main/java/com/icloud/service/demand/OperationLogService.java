package com.icloud.service.demand;

import com.github.pagehelper.PageInfo;
import com.icloud.model.demand.OperationLog;

public interface OperationLogService {
	void save(OperationLog log);
	OperationLog findByObj(String objectId);
	PageInfo<OperationLog> findForList(int pageNo,int pageSize,OperationLog log);
	Integer hasNewLog(OperationLog log);
}
