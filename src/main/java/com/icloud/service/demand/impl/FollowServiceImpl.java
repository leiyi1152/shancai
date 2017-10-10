package com.icloud.service.demand.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icloud.dao.demand.FollowMapper;
import com.icloud.model.demand.Follow;
import com.icloud.service.demand.FollowService;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowMapper mapper;
	@Override
	public Integer checkIsFollow(Follow f) throws Exception {
		Integer count = mapper.checkIsFollow(f);
		return null==count?0:count;
	}

	@Override
	public void unFollow(Follow f) throws Exception {
         mapper.unFollow(f);
	}

	@Override
	public void save(Follow f) throws Exception {
          mapper.save(f);
	}

}
