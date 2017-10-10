package com.icloud.service.demand;

import com.icloud.model.demand.DemandExt;

public interface DemandExtService {
	void save(DemandExt ext) throws Exception;
	DemandExt selectByDemand(String demandId)throws Exception;
	void update(DemandExt ext)throws Exception;
}
