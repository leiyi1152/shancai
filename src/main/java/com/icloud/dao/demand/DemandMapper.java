package com.icloud.dao.demand;

import java.util.List;
import java.util.Map;

import com.icloud.common.dto.vo.CountVo;
import com.icloud.dao.DAO;
import com.icloud.model.demand.Demand;

public interface DemandMapper extends DAO<Demand>{
	List<Demand> findFollowList(Demand demand);
	
	List<CountVo> countByStatusAndCatrgory(Map<String,Object> map);

	List<Demand> getUnResponseList(Demand demand);
	
	List<CountVo> countByCategory(Demand demand);
	
	List<CountVo> countByStatus(Demand demand);
	
	Integer responseTimeData(Map<String,Object> map);
	
	Long averageTime(Demand demand);
	
	Integer countResponse(Demand demand);
	
	List<CountVo> selectRank(Demand demand);
}
