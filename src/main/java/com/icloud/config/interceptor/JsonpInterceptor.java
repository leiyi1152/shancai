package com.icloud.config.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * token拦截器
 * @author luoqw
 * 2016-9-7下午6:05:59
 */
public class JsonpInterceptor implements HandlerInterceptor{
	@Autowired
	public RestTemplate restTemplate;
	@Override
	public void afterCompletion(HttpServletRequest reqeust, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest reqeust, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
 
	}

	@Override
	public boolean preHandle(HttpServletRequest reqeust, HttpServletResponse response, Object arg2) throws Exception {
        return true;
	}
	

}
