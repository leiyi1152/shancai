package com.icloud.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.icloud.dao.bms.TmenuMapper;
import com.icloud.model.bms.Tadmin;
import com.icloud.model.bms.Tmenu;
import com.icloud.service.bms.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private TmenuMapper menuMapper;
	@Override
	public List<Tmenu> selectMenuByUser(Tadmin admin) {
		// TODO Auto-generated method stub
		return menuMapper.selectMenuByUser(admin);
	}
	
	@Override
	public List<Tmenu> selectParentMenu() {
		// TODO Auto-generated method stub
		return menuMapper.selectParentMenu();
	}
	
	@Override
	public int insertSelective(Tmenu record) {
		// TODO Auto-generated method stub
		return menuMapper.insertSelective(record);
	}
	@Override
	public int selectCountByName(String menuName) {
		// TODO Auto-generated method stub
		return menuMapper.selectCountByName(menuName);
	}
	@Override
	public List<Tmenu> selectAllList() {
		// TODO Auto-generated method stub
		return menuMapper.selectAllList();
	}
	
	@Override
	public int countByParent(String id) {
		// TODO Auto-generated method stub
		return menuMapper.countByParent(id);
	}
	
	@Override
	public List<Tmenu> selectByRole(String roleId) {
		// TODO Auto-generated method stub
		return menuMapper.selectByRole(roleId);
	}

	@Override
	public void save(Tmenu t) throws Exception {
		// TODO Auto-generated method stub
		menuMapper.save(t);
		
	}

	@Override
	public void update(Tmenu t) throws Exception {
		// TODO Auto-generated method stub
		menuMapper.update(t);
	}

	@Override
	public List<Tmenu> findList(Tmenu t) throws Exception {
		// TODO Auto-generated method stub
		t.setTotalNum(menuMapper.findCount(t));
		return menuMapper.findForList(t);
	}

	@Override
	public Integer findCount(Tmenu t) throws Exception {
		// TODO Auto-generated method stub
		return menuMapper.findCount(t);
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		menuMapper.deleteByKey(id);
	}

	@Override
	public Tmenu findByKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return menuMapper.findForObject(id);
	}

	@Override
	public PageInfo<Tmenu> findByPage(int pageNo, int pageSize, Tmenu t)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
