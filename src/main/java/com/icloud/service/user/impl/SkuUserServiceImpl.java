package com.icloud.service.user.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.common.dto.vo.CountVo;
import com.icloud.dao.user.SkuUserMapper;
import com.icloud.model.user.SkuUser;
import com.icloud.service.user.SkuUserService;

@Service
@Transactional
public class SkuUserServiceImpl implements SkuUserService {
	@Autowired
	private SkuUserMapper mapper;

	@Override
	public void save(SkuUser t) throws Exception {
         mapper.save(t);
	}

	@Override
	public void update(SkuUser t) throws Exception {
            mapper.update(t);
	}

	@Override
	public List<SkuUser> findList(SkuUser t) throws Exception {
		
		return mapper.findForList(t);
	}

	@Override
	public Integer findCount(SkuUser t) throws Exception {
		return mapper.findCount(t);
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public SkuUser findByKey(String id) throws Exception {
		return mapper.findForObject(id);
	}

	@Override
	public PageInfo<SkuUser> findByPage(int pageNo, int pageSize, SkuUser t) throws Exception {
		PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<SkuUser>(mapper.findForList(t));
	}

	@Override
	public SkuUser findByPhone(String phone) {
		return mapper.findByPhone(phone);
	}

	@Override
	public SkuUser findByWxUser(String wxUserId) {
		return mapper.findByWxUser(wxUserId);
	}

	@Override
	public List<CountVo> findCountByDays(Map<String, String> map) {
		List<CountVo> count =  mapper.findCountByDays(map);
	    return count;
	}

}
