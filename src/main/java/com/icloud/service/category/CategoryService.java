package com.icloud.service.category;

import java.util.List;

import com.icloud.model.category.Category;
import com.icloud.model.category.SkuCategory;
import com.icloud.service.BaseService;

public interface CategoryService extends BaseService<Category> {
	List<Category> selectSkuCategory(String skuId);
   
	void addSkuRelated(List<SkuCategory> list);
	
	void deleteSkuRelated(String skuId);
	
	Category checkName(String categoryName);
}
