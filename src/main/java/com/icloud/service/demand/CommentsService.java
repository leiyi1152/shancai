package com.icloud.service.demand;

import com.github.pagehelper.PageInfo;
import com.icloud.model.demand.Comments;

public interface CommentsService {

	void save(Comments comments)throws Exception;
	PageInfo<Comments> findByDemand(int pageNo,int pageSize,String demandId);
}
