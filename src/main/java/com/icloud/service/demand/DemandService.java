package com.icloud.service.demand;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.icloud.common.dto.vo.CountVo;
import com.icloud.model.demand.Demand;
import com.icloud.service.BaseService;

public interface DemandService extends BaseService<Demand> {
	PageInfo<Demand> findFollowList(int pageNo,int pageSize,Demand demand) throws Exception;
	List<CountVo> countByStatusAndCatrgory(Map<String,Object> map);
	public List<Demand> findFollowListAll(Demand demand) ;
	PageInfo<Demand> getUnResponseList(int pageNo,int pageSize,Demand demand);
    //统计
	List<CountVo> countByCategory(Demand demand);
	List<CountVo> countByStatus(Demand demand);
	Integer responseTimeData(Map<String,Object> map);
	//平均时间
    Long averageTime(Demand demand);
	//已被响应的总数
	Integer countResponse(Demand demand);
	//响应排行 接单排行
	List<CountVo> selectRank(Demand demand);
}
