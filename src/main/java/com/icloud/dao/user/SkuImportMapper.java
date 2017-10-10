package com.icloud.dao.user;

import java.util.List;

import com.icloud.model.user.SkuImport;

public interface SkuImportMapper {
	Integer findCount(SkuImport sku);
	SkuImport findForKey(String id);
	public SkuImport findForObject(SkuImport sku);
	void delete(SkuImport sku);
	void bathInsert(List<SkuImport> list);
	void update(SkuImport sku);
	List<SkuImport>findForList(SkuImport sku);
	void deleteByKey(String id);
}
