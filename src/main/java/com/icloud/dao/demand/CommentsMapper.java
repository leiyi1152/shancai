package com.icloud.dao.demand;

import java.util.List;

import com.icloud.model.demand.Comments;

public interface CommentsMapper {
	void save(Comments comments);
	List<Comments> findByDemand(String demandId);
}
