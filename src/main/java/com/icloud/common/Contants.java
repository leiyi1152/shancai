package com.icloud.common;

import com.icloud.common.util.ConfigUtil;

/**
 * 系统常量配置
 * @author luoqw
 *2016年9月18日 上午9:46:40
 */
public class Contants { 
	
	public static final String service_domain = ConfigUtil.get("service_domain");
	
	public static final String token_server_domain = "http:tokenservername.com/";
	
	public static final String userInfo_get = token_server_domain+"/Token/userInfo";//获取token地址

	
	/**公众号请求接口**/
	public static final String mct_mp_add = service_domain+"/mp/add";//添加公众号地址
	
	
	/**图片上传基本路径**/
	public static final String IMG_BASE_PATH_ = "/img/";
	public static final String _DO_MAIN_ = "https://tiny.bys2b.com";
	
	/**腾讯地图API**/
	public static final String TX_LBS_API_KEY = "WWKBZ-NNPR3-JON3J-3JMPX-KM6ES-ODFCH";
	//逆地址转换API 地址-》坐标
	public static final String TX_LBS_GEOCODER_URL = "http://apis.map.qq.com/ws/geocoder/v1/?address=ADDRESS&key=KEY";
	
	/**百度地图API **/
	public static final String BD_LBS_API_KEY="A4gjEvgoyry4G4zxMg8mCiCtGh7dhst4";
	/** 百度逆地址转换**/
	public static final String BD_LBS_GEOCODER_URL= "http://api.map.baidu.com/geocoder/v2/?output=json&address=ADDRESS&ak=AK";
	
	/**腾讯云短信**/
	public static final String sdkAppId = "1400034295";
	public static final String strAppKey ="efb3a3e8c5babb7cc36bff05813e4749";
	
	public static final String yun_sms_url = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";
    
	public static final String smsCode = "【闪采】%s为您的实名绑定验证码，请于2分钟内填写。如非本人操作，请忽略本短信。";
	public static final String responseSmsTemplate = "【闪采】您于%s在闪采小程序发布的%s类需求已被%sSKU经理响应，快去看看他提供的好货吧！";

}
