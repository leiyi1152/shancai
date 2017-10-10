package com.icloud.service;

import java.util.List;
import com.github.pagehelper.PageInfo;

public interface BaseService<T> {
	/**
	 * 保存
	 * @param t
	 */
	public void save(T t) throws Exception;
	
	/**
	 * 更新
	 * @param t
	 * @throws Exception
	 */
	public void update(T t) throws Exception;
	
	/**
	 * 查找列表
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<T> findList(T t) throws Exception;
	
	/**
	 * 查找条数
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public Integer findCount(T t) throws Exception;
	
	
	/**
	 * 通过Id删除
	 * @param id
	 */
	public void delete(String id) throws Exception;
	
	/**
	 * 通过主键查找
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T findByKey(String id)throws Exception;
	
	/**
	 * 分页查找
	 * @return
	 * @throws Exception
	 */
	public PageInfo<T> findByPage(int pageNo, int pageSize,T t)throws Exception;

	
	
} 
