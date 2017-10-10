package com.icloud.service.demand.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.dao.demand.CommentsMapper;
import com.icloud.model.demand.Comments;
import com.icloud.service.demand.CommentsService;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService{

	@Autowired
	private CommentsMapper mapper;
	@Override
	public void save(Comments comments) throws Exception {
       mapper.save(comments);		
	}

	@Override
	public PageInfo<Comments> findByDemand(int pageNo,int pageSize,String demandId) {
        PageHelper.startPage(pageNo,pageSize);
        return new PageInfo<Comments>(mapper.findByDemand(demandId));
	}

}
