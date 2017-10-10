package com.icloud.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.icloud.dao.bms.TroleMapper;
import com.icloud.model.bms.Trole;
import com.icloud.service.bms.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private TroleMapper roleMapper;
	
	

	@Override
	public int insertSelective(Trole record) {
		// TODO Auto-generated method stub
		return roleMapper.insertSelective(record);
	}


	@Override
	public int selectCountByName(String roleName) {
		// TODO Auto-generated method stub
		return roleMapper.selectCountByName(roleName);
	}

	@Override
	public List<Trole> selectByAdmin(String adminId) {
		// TODO Auto-generated method stub
		return roleMapper.selectByAdmin(adminId);
	}

	@Override
	public List<Trole> selectAll() {
		// TODO Auto-generated method stub
		return roleMapper.selectAll();
	}


	@Override
	public void save(Trole t) throws Exception {
		// TODO Auto-generated method stub
		 roleMapper.save(t);
	}


	@Override
	public void update(Trole t) throws Exception {
		// TODO Auto-generated method stub
		 roleMapper.update(t);
	}


	@Override
	public List<Trole> findList(Trole t) throws Exception {
		// TODO Auto-generated method stub
		t.setTotalNum(roleMapper.findCount(t));
		return roleMapper.findForList(t);
	}


	@Override
	public Integer findCount(Trole t) throws Exception {
		// TODO Auto-generated method stub
		return roleMapper.findCount(t);
	}


	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		roleMapper.deleteByKey(id);
	}


	@Override
	public Trole findByKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return roleMapper.findForObject(id);
	
	}


	

	@Override
	public PageInfo<Trole> findByPage(int pageNo, int pageSize, Trole t)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
