package com.icloud.service.demand.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.dao.demand.OperationLogMapper;
import com.icloud.model.demand.OperationLog;
import com.icloud.service.demand.OperationLogService;

@Service
@Transactional
public class OperationLogServiceImpl implements OperationLogService{

	@Autowired
	private OperationLogMapper mapper;
	@Override
	public void save(OperationLog log) {
          mapper.save(log);		
	}

	@Override
	public OperationLog findByObj(String objectId) {
		return mapper.findByObj(objectId);
	}

	@Override
	public PageInfo<OperationLog> findForList(int pageNo, int pageSize,
			OperationLog log) {
		PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<OperationLog>(mapper.findForList(log));
	}

	@Override
	public Integer hasNewLog(OperationLog log) {
        Integer count = mapper.hasNewLog(log);
		return null==count?0:count;
	}

}
