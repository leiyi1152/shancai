package com.icloud.dao.demand;

import java.util.List;

import com.icloud.model.demand.TerminationRecord;

public interface TerminationMapper {
    
	void save(TerminationRecord record)throws Exception;
	TerminationRecord findByDemand(String demandId)throws Exception;
	void update(TerminationRecord record)throws Exception;
	List<TerminationRecord>findForList(TerminationRecord record)throws Exception;
}
