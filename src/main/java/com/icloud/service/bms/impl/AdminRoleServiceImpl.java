package com.icloud.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icloud.dao.bms.TadminRoleMapper;
import com.icloud.model.bms.TadminRole;
import com.icloud.service.bms.AdminRoleService;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {

	@Autowired
	private TadminRoleMapper adminRoleMapper;
	@Override
	public int insert(TadminRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TadminRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delele(String adminId) {
		// TODO Auto-generated method stub
		adminRoleMapper.delele(adminId);
	}

	@Override
	public void batchInsert(List<TadminRole> list) {
		// TODO Auto-generated method stub
		adminRoleMapper.batchInsert(list);
	}

}
