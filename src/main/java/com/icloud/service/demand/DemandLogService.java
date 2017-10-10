package com.icloud.service.demand;

import java.util.List;

import com.icloud.model.demand.DemandLog;

public interface DemandLogService {

	void save(DemandLog log);
	List<DemandLog> findByDemand(String demandId);
}
