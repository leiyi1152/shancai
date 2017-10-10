package com.icloud.service.demand.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icloud.dao.demand.DemandExtMapper;
import com.icloud.model.demand.DemandExt;
import com.icloud.service.demand.DemandExtService;

@Service
@Transactional
public class DemandExtServiceImpl implements DemandExtService {

	@Autowired
	private DemandExtMapper demandExtMapper;
	@Override
	public void save(DemandExt ext) throws Exception {
		demandExtMapper.save(ext);
	}

	@Override
	public DemandExt selectByDemand(String demandId) throws Exception {
		return demandExtMapper.selectByDemand(demandId);
	}

	@Override
	public void update(DemandExt ext) throws Exception {
		demandExtMapper.update(ext);

	}

}
