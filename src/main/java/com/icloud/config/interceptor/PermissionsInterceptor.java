package com.icloud.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.icloud.common.Contants;

/**
 * 权限拦截器
 * @author leiyi
 *
 */
public class PermissionsInterceptor implements HandlerInterceptor {
	
	public static final String NO_INTERCEPTOR_PATH = ".*((_del)|(_getList)|(_input))";	//不对匹配该值的访问路径拦截（正则）
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("admin_user");
		if(null==obj){
			response.sendRedirect(Contants._DO_MAIN_+"/tologin");
			return false;
		}
		return true;
		/*List<Tmenu> adminMenu = (List<Tmenu>) session.getAttribute("admin_menu");
		String requestPath = request.getServletPath();
		if(requestPath.matches(NO_INTERCEPTOR_PATH)){
			return true;
		}
		for(Tmenu m:adminMenu){
			if(m.getMenuUrl().indexOf(requestPath)>=0){
				return true;
			}
		}
		
	    ResponseUtils.renderText(response, "您暂无权限");
		return false;*/
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
			
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
