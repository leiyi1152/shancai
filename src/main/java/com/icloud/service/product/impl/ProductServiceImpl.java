package com.icloud.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icloud.dao.product.ProductMapper;
import com.icloud.model.product.Product;
import com.icloud.service.product.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper productMapper;

	@Override
	public void save(Product t) throws Exception {
		productMapper.save(t);
	}

	@Override
	public void update(Product t) throws Exception {
       productMapper.update(t);
	}

	@Override
	public List<Product> findList(Product t) throws Exception {
		
		
		return null;
	}

	@Override
	public Integer findCount(Product t) throws Exception {
		return null;
	}

	@Override
	public void delete(String id) throws Exception {
         productMapper.deleteByKey(id);
	}

	@Override
	public Product findByKey(String id) throws Exception {
		return productMapper.findForObject(id);
	}

	@Override
	public PageInfo<Product> findByPage(int pageNo, int pageSize, Product t) throws Exception {
		PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<Product>(productMapper.findForList(t));
	}

}
