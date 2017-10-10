package com.icloud.service.demand.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.dao.demand.TerminationMapper;
import com.icloud.model.demand.TerminationRecord;
import com.icloud.service.demand.TerminationRecordService;

@Service
@Transactional
public class TerminationRecordServiceImpl implements TerminationRecordService {

	@Autowired
	private TerminationMapper terminationMapper;
	@Override
	public void save(TerminationRecord record) throws Exception {
		terminationMapper.save(record);
	}

	@Override
	public TerminationRecord findByDemand(String demandId) throws Exception {
		return terminationMapper.findByDemand(demandId);
	}

	@Override
	public void update(TerminationRecord record) throws Exception {
		terminationMapper.update(record);
	}

	@Override
	public PageInfo<TerminationRecord> findForList(int pageNo, int pageSize,
			TerminationRecord record) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<TerminationRecord>(terminationMapper.findForList(record));
	}

}
