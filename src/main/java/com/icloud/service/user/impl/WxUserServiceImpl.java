package com.icloud.service.user.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.icloud.common.dto.UserSession;
import com.icloud.common.dto.vo.CountVo;
import com.icloud.dao.user.WxUserMapper;
import com.icloud.model.user.WxUser;
import com.icloud.service.redis.RedisService;
import com.icloud.service.user.WxUserService;

@Service
@Transactional
public class WxUserServiceImpl implements WxUserService {
	@Autowired
	private WxUserMapper mapper;
	@Autowired
	private RedisService redisService;

	@Override
	public void save(WxUser t) throws Exception {
		mapper.save(t);
	}

	@Override
	public void update(WxUser t) throws Exception {
        mapper.update(t);
	}

	@Override
	public List<WxUser> findList(WxUser t) throws Exception {
		return mapper.findForList(t);
	}

	@Override
	public Integer findCount(WxUser t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public WxUser findByKey(String id) throws Exception {
		return mapper.findForObject(id);
	}

	@Override
	public PageInfo<WxUser> findByPage(int pageNo, int pageSize, WxUser t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WxUser findByOpenId(String openId) {
		// TODO Auto-generated method stub
		return mapper.findByOpenId(openId);
	}

	@Override
	public WxUser getUserFromSession(String sid) {
		UserSession session = (UserSession) redisService.get(sid);
		if(null==session){
			return null;
		}
		String openId = session.getOpenId();
		return mapper.findByOpenId(openId/*"o7_Yb0bR_Td_T3ZVE8ARrHeHOdg0"*/);
	}

	@Override
	public List<CountVo> findCountByDays(Map<String, String> map) {
		List<CountVo> count =  mapper.findCountByDays(map);
       return count;
	}

}
