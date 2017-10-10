package com.icloud.service.demand;

import com.icloud.model.demand.Follow;

public interface FollowService {
	Integer checkIsFollow(Follow f)throws Exception;
	void unFollow(Follow f)throws Exception;
	void save(Follow f)throws Exception;
}
