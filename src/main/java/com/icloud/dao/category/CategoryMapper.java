package com.icloud.dao.category;

import java.util.List;

import com.icloud.dao.DAO;
import com.icloud.model.category.Category;
import com.icloud.model.category.SkuCategory;

public interface CategoryMapper extends DAO<Category> {
	List<Category> selectSkuCategory(String skuId);
	void addSkuRelated(List<SkuCategory> list);
	void deleteSkuRelated(String skuId);
	Category checkName(String categoryName);
}
