package com.icloud.dao.demand;

import java.util.List;

import com.icloud.model.demand.OperationLog;

public interface OperationLogMapper {
	void save(OperationLog log);
	OperationLog findByObj(String objectId);
	List<OperationLog> findForList(OperationLog log);
	/**用户对当前需求是否有未读留言**/
	Integer hasNewLog(OperationLog log);
}
