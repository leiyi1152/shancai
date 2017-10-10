package com.icloud.dao;

import java.util.List;

public interface DAO<T> {
	
	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void save(T t) throws Exception;
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void update(T t) throws Exception;
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void deleteByKey(String id) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public T findForObject(String id) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<T> findForList(T t) throws Exception;
	
	/**
	 * 查找对象通过条件
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<T> findForConditions(T Conditions) throws Exception;
	
	/**
	 * 查找记录数
	 * @param Conditions
	 * @return
	 */
	public Integer findCount(T Conditions) throws Exception;
	
	/**
	 * 查找分页
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<T> findByPage(T t)throws Exception;
}
