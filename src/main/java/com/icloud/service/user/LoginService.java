package com.icloud.service.user;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author 
 *
 */
public interface LoginService {


	/**
	 * 	登录
	 * @param request 
	 */
	public Object login(String jsonParams, HttpServletRequest request) throws Exception;
	
	
	/**
	 * 	验证登录
	 */
	public Object checkSession(String jsonParams, HttpServletRequest request);
	

	
	
	
}
