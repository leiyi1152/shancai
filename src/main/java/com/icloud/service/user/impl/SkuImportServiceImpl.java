package com.icloud.service.user.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.common.excel.ReadExcel;
import com.icloud.dao.user.SkuImportMapper;
import com.icloud.model.user.SkuImport;
import com.icloud.service.user.SkuImportService;

@Service
@Transactional
public class SkuImportServiceImpl implements SkuImportService{

	@Autowired
	private SkuImportMapper mapper;
	@Override
	public Integer findCount(SkuImport sku) {
		Integer count = mapper.findCount(sku);
		return null==count?0:count;
	}

	@Override
	public SkuImport findForObject(SkuImport sku) {
		return mapper.findForObject(sku);
	}
	@Override
	public SkuImport findForKey(String id) {
		return mapper.findForKey(id);
	}
	
	

	@Override
	public void delete(SkuImport sku) {
        mapper.delete(sku);		
	}

	@Override
	public void bathInsert(List<SkuImport> list) {
       mapper.bathInsert(list);		
	} 

	@Override
	public void update(SkuImport sku) {
		mapper.update(sku);
	}

	@Override
	public PageInfo<SkuImport> findForList(int pageNo, int pageSize,
			SkuImport sku) {
		PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<SkuImport>(mapper.findForList(sku));
	}

	@Override
	public int importSku(File file) throws Exception {
		List<List<Object>> list = ReadExcel.readExcel(file, 0, 1, 0, 1);
		
		if(null!=list&&list.size()>0){
			List<SkuImport> skuList = new ArrayList<SkuImport>();
			SkuImport sku = null;
			for(List<Object> objs:list){
				sku = new SkuImport();
				sku.setName(objs.get(0).toString());
				sku.setPhone(objs.get(1).toString());
				sku.setIsBind("0");
				skuList.add(sku);
			}
			mapper.bathInsert(skuList);
			return list.size();
		}
		return 0;
	}

	@Override
	public void deleteByKey(String id) {
		mapper.deleteByKey(id);		
	}

	
}
