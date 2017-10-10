package com.icloud.dao.demand;

import com.icloud.model.demand.DemandExt;

public interface DemandExtMapper {
	void save(DemandExt ext) throws Exception;
	DemandExt selectByDemand(String demandId)throws Exception;
	void update(DemandExt ext)throws Exception;

}
