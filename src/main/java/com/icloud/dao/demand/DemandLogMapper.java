package com.icloud.dao.demand;

import java.util.List;

import com.icloud.model.demand.DemandLog;

public interface DemandLogMapper {

	void save(DemandLog log);
	List<DemandLog> findByDemand(String demandId);
}
