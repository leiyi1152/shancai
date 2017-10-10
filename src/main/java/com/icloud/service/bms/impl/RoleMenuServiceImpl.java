package com.icloud.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icloud.dao.bms.TroleMenuMapper;
import com.icloud.model.bms.TroleMenu;
import com.icloud.service.bms.RoleMenuService;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	@Autowired
	private TroleMenuMapper roleMenuMapper;
	@Override
	public int insert(TroleMenu record) {
		// TODO Auto-generated method stub
		return roleMenuMapper.insert(record);
	}

	@Override
	public int insertSelective(TroleMenu record) {
		// TODO Auto-generated method stub
		return roleMenuMapper.insert(record);
	}

	@Override
	public void delete(TroleMenu record) {
		// TODO Auto-generated method stub
        roleMenuMapper.delete(record);
	}

	@Override
	public void batchInsert(List<TroleMenu> list) {
		// TODO Auto-generated method stub
		roleMenuMapper.batchInsert(list);
	}

}
