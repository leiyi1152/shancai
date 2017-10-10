package com.icloud.service.category.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.dao.category.CategoryMapper;
import com.icloud.model.category.Category;
import com.icloud.model.category.SkuCategory;
import com.icloud.service.category.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper mapper;
	@Override
	public void save(Category t) throws Exception {
		mapper.save(t);
	}

	@Override
	public void update(Category t) throws Exception {
       mapper.update(t);
	}

	@Override
	public List<Category> findList(Category t) throws Exception {
		return mapper.findForList(t);
	}

	@Override
	public Integer findCount(Category t) throws Exception {
		return null;
	}

	@Override
	public void delete(String id) throws Exception {
             mapper.deleteByKey(id);
	}

	@Override
	public Category findByKey(String id) throws Exception {
		
		return mapper.findForObject(id);
	}

	@Override
	public PageInfo<Category> findByPage(int pageNo, int pageSize, Category t)
			throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        List<Category> list = mapper.findForList(t);
		return new PageInfo<Category>(list);
	}

	@Override
	public List<Category> selectSkuCategory(String skuId) {
		return mapper.selectSkuCategory(skuId);
	}

	@Override
	public void addSkuRelated(List<SkuCategory> list) {
            mapper.addSkuRelated(list);		
	}

	@Override
	public void deleteSkuRelated(String skuId) {
           mapper.deleteSkuRelated(skuId);		
	}

	@Override
	public Category checkName(String categoryName) {
		 
		return mapper.checkName(categoryName);
	}

}
