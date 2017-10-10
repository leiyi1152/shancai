package com.icloud.service.user;

import java.io.File;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.icloud.model.user.SkuImport;

public interface SkuImportService {
	Integer findCount(SkuImport sku);
	SkuImport findForObject(SkuImport sku);
	SkuImport findForKey(String id);
	void delete(SkuImport sku);
	void bathInsert(List<SkuImport> list);
	void update(SkuImport sku);
	PageInfo<SkuImport>findForList(int pageNo,int pageSize,SkuImport sku);
	
	int importSku(File file)throws Exception; 
	void deleteByKey(String id);
	
}
