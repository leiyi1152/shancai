package com.icloud.common;

import com.icloud.common.util.ConfigUtil;

/**
 * 服务接口地址
 * @author luoqw
 *2016年9月18日 上午9:46:40
 */
public class ServiceUrlContants { 
	
	public static final String service_domain = ConfigUtil.get("service_domain");
	
	public static final String token_server_domain = "http:tokenservername.com/";
	
	public static final String userInfo_get = token_server_domain+"/Token/userInfo";//获取token地址

	
	/**公众号请求接口**/
	public static final String mct_mp_add = service_domain+"/mp/add";//添加公众号地址
	
}
