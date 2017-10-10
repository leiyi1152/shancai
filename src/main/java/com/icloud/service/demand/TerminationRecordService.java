package com.icloud.service.demand;

import com.github.pagehelper.PageInfo;
import com.icloud.model.demand.TerminationRecord;

public interface TerminationRecordService {
	void save(TerminationRecord record)throws Exception;
	TerminationRecord findByDemand(String demandId)throws Exception;
	void update(TerminationRecord record)throws Exception;
	PageInfo<TerminationRecord>findForList(int pageNo,int pageSize,TerminationRecord record)throws Exception;
}
