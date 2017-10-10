package com.icloud.web.user.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icloud.common.util.RequestUtil;
import com.icloud.service.user.LoginService;


@RestController
public class LoginController{
	@Autowired
	private LoginService loginService;
 
	/**微信小程序登录接口**/
	@RequestMapping(value = "/wx/login")
	 public Object login(HttpServletRequest request) throws Exception {
		String jsonText = RequestUtil.readPostContent(request);  
		return  RequestUtil.pakageJsonp(request,loginService.login(jsonText,request));
    }
	
	/**登录会话有效性验证**/
	@RequestMapping(value="/wx/checkSession")
	public Object checkSession(HttpServletRequest request){
		String jsonText = RequestUtil.readPostContent(request);
		Object obj = loginService.checkSession(jsonText, request);
		return RequestUtil.pakageJsonp(request,obj);
	}
	
	
}