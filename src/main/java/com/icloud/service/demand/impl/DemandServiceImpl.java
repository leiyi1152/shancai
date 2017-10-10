package com.icloud.service.demand.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.common.dto.vo.CountVo;
import com.icloud.dao.demand.DemandMapper;
import com.icloud.model.demand.Demand;
import com.icloud.service.demand.DemandService;

@Service
@Transactional
public class DemandServiceImpl implements DemandService {
	
	@Autowired
	private DemandMapper mapper;

	@Override
	public void save(Demand t) throws Exception {
        mapper.save(t);
	}

	@Override
	public void update(Demand t) throws Exception {
       mapper.update(t);
	}

	@Override
	public List<Demand> findList(Demand t) throws Exception {
		return mapper.findForList(t);
	}

	@Override
	public Integer findCount(Demand t) throws Exception {
		return mapper.findCount(t);
	}

	@Override
	public void delete(String id) throws Exception {
          mapper.deleteByKey(id);
	}

	@Override
	public Demand findByKey(String id) throws Exception {
		return mapper.findForObject(id);
	}

	@Override
	public PageInfo<Demand> findByPage(int pageNo, int pageSize, Demand t)
			throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        List<Demand> list = mapper.findForList(t);
		return new PageInfo<Demand>(list);
	}

	@Override
	public PageInfo<Demand> findFollowList(int pageNo,int pageSize,Demand demand) {
		 PageHelper.startPage(pageNo, pageSize);
		 List<Demand> list = mapper.findFollowList(demand);
		 return new PageInfo<Demand>(list);
	}
	

	public List<Demand> findFollowListAll(Demand demand) {
		 List<Demand> list = mapper.findFollowList(demand);
		 return list;
	}

	@Override
	public List<CountVo> countByStatusAndCatrgory(Map<String, Object> map) {
		return mapper.countByStatusAndCatrgory(map);
	}

	@Override
	public PageInfo<Demand> getUnResponseList(int pageNo, int pageSize, Demand demand) {
		 PageHelper.startPage(pageNo, pageSize);
		 List<Demand> list = mapper.getUnResponseList(demand);
		 return new PageInfo<Demand>(list);
	}

	@Override
	public List<CountVo> countByCategory(Demand demand) {
		return mapper.countByCategory(demand);
	}

	@Override
	public List<CountVo> countByStatus(Demand demand) {
		return mapper.countByStatus(demand);
	}

	@Override
	public Integer responseTimeData(Map<String, Object> map) {
		Integer count = mapper.responseTimeData(map);
		return null==count?0:count;
	}

	@Override
	public Long averageTime(Demand demand) {
		Long time = mapper.averageTime(demand);
		return null==time?0:time;
	}

	@Override
	public Integer countResponse(Demand demand) {
		Integer count = mapper.countResponse(demand); 
		return null==count?0:count;
	}

	@Override
	public List<CountVo> selectRank(Demand demand) {
		return mapper.selectRank(demand);
	}

}
