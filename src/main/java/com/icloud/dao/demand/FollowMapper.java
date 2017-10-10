package com.icloud.dao.demand;

import com.icloud.model.demand.Follow;

public interface FollowMapper {
	Integer checkIsFollow(Follow f)throws Exception;
	void unFollow(Follow f)throws Exception;
	void save(Follow f)throws Exception;
}
