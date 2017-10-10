package com.icloud.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.dao.bms.TadminMapper;
import com.icloud.model.bms.Tadmin;
import com.icloud.service.bms.AdminService;
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	TadminMapper adminMapper;
	
	@Override
	public Tadmin login(Tadmin tadmin) throws Exception {
		// TODO Auto-generated method stub
		List<Tadmin> list = adminMapper.findForConditions(tadmin);
		if(null!=list&&0<list.size()){
			return list.get(0);
		}
		return null;
		
	}

	@Override
	public int selectCountByAccount(String account) {
		// TODO Auto-generated method stub
		return adminMapper.findCountByAccount(account);
	}

	@Override
	public void save(Tadmin t) throws Exception {
		// TODO Auto-generated method stub
		adminMapper.save(t);
	}

	@Override
	public void update(Tadmin t) throws Exception {
		// TODO Auto-generated method stub
		adminMapper.update(t);
	}

	@Override
	public List<Tadmin> findList(Tadmin t) throws Exception {
		// TODO Auto-generated method stub
		t.setTotalNum(adminMapper.findCount(t));
		return adminMapper.findForList(t);
	}

	@Override
	public Integer findCount(Tadmin t) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.findCount(t);
	}

	

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		adminMapper.deleteByKey(id);
	}

	@Override
	public Tadmin findByKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.findForObject(id);
	}

	@Override
	public PageInfo<Tadmin> findByPage(int pageNo, int pageSize,Tadmin admin) throws Exception {
		// TODO Auto-generated method stub
		 PageHelper.startPage(pageNo, pageSize);
		 PageInfo<Tadmin> page = new PageInfo<Tadmin>(adminMapper.findByPage(admin));
		 return page;
		
	}

	

}
