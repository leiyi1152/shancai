package com.icloud.service.demand.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icloud.dao.demand.DemandLogMapper;
import com.icloud.model.demand.DemandLog;
import com.icloud.service.demand.DemandLogService;

@Service
@Transactional
public class DemandLogServiceImpl implements DemandLogService {

	@Autowired
	private DemandLogMapper mapper;
	@Override
	public void save(DemandLog log) {
		mapper.save(log);
	}

	@Override
	public List<DemandLog> findByDemand(String demandId) {
		return mapper.findByDemand(demandId);
	}

}
