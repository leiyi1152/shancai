package com.icloud.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.icloud.common.util.RequestUtil;
 
/**
 * Controller基类 
 * @author luoqw
 *  2016年10月20日下午5:38:28
 */
public abstract class AppBaseController extends RequestUtil {

	@Autowired
	protected  HttpServletRequest request;
	
	public final static Logger log = LoggerFactory
			.getLogger(BaseController.class);

	/**
	 * 将对象转成json字符串，并组装成jsonp格式
	 * @param obj javaBeand对象
	 * @return    jsonp格式字符串
	 */
	protected String pakageJsonp(Object obj) { 
		String callbackFun = request.getParameter("callbackFun");
		String result = "";
		if(obj instanceof JSONObject ){
			 result = obj.toString();
		}else{
			 result = JSONObject.toJSONString(obj);
		}
		log.info("返回參數:"+result);
		
		if (StringUtils.isBlank(callbackFun)) {
			return result;
		}
		String jsonpCallback = callbackFun+"("+  result  +");";
		return jsonpCallback;
	}
}
