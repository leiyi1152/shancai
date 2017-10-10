package com.icloud.service.user.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.common.dto.vo.CountVo;
import com.icloud.dao.user.UserMapper;
import com.icloud.model.user.User;
import com.icloud.service.user.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;

	@Override
	public void save(User t) throws Exception {
		userMapper.save(t);
	}

	@Override
	public void update(User t) throws Exception {
		userMapper.update(t);
	}

	@Override
	public PageInfo<User> findByPage(int pageNo, int pageSize, User t) throws Exception {
		PageHelper.startPage(pageNo, pageSize);
		List<User> list = userMapper.findForList(t);
		PageInfo<User> page = new PageInfo<User>(list);
		return page;
	}

	@Override
	public User findByKey(String id) throws Exception {
		return userMapper.findForObject(id);
	}

	@Override
	public User findByWxUser(String openId) {
		return userMapper.findByWxUser(openId);
	}

	@Override
	public List<User> findList(User t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer findCount(User t) throws Exception {
		Integer count = userMapper.findCount(t);
		return null==count?0:count;
	}

	@Override
	public User findByPhone(String phone) {
		return userMapper.findByPhone(phone);
	}

	@Override
	public List<CountVo> findCountByDays(Map<String, String> map) {
		List<CountVo> count =  userMapper.findCountByDays(map);
	       return count;
	}

	

	

	
}
