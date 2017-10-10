package com.icloud.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置拦截器
 * @author luoqw
 *  2016年10月20日下午3:59:23
 */
@Configuration 
public class InterceptorStack {

 
	@Configuration
	static class WebMvcConfigurer extends WebMvcConfigurerAdapter {
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new JsonpInterceptor()).addPathPatterns("/encry/*");
		    registry.addInterceptor(new PermissionsInterceptor()).addPathPatterns("/admin/*"); 
	        
		}
	}
}
