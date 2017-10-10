package com.icloud.service.sms;

import com.alibaba.fastjson.JSONObject;

public interface SmsService {
	
	public JSONObject sendCodeSms(String phone) throws Exception;

}
