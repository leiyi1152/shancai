package com.icloud.service.sms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.icloud.common.Contants;
import com.icloud.common.sms.SmsSenderUtil;
import com.icloud.common.util.HttpClientUtils;
import com.icloud.service.redis.RedisService;
import com.icloud.service.sms.SmsService;

@Service
public class SmsServiceImpl implements SmsService {
	public final static Logger logger = LoggerFactory
			.getLogger(SmsServiceImpl.class);
	@Autowired
	private RedisService redisService;

	@Override
	public JSONObject sendCodeSms(String phone) throws Exception{
		    SmsSenderUtil util = new SmsSenderUtil();
		    String code = util.getCode()+"";
		    redisService.set(phone, code, 3000L);
		    logger.info("验证码:"+phone+":"+code);
		    long random = util.getRandom();
	        long curTime = System.currentTimeMillis()/1000;

			JSONObject data = new JSONObject();

	        JSONObject tel = new JSONObject();
	        tel.put("nationcode", "86");
	        tel.put("mobile", phone);

	        data.put("type", "0");
	        data.put("msg", String.format(Contants.smsCode,code));
	        data.put("sig", util.strToHash(String.format(
	        		"appkey=%s&random=%d&time=%d&mobile=%s",
	        		Contants.strAppKey, random, curTime, phone)));
	        data.put("tel", tel);
	        data.put("time", curTime);
	        data.put("extend", "");
	        data.put("ext", "");

	        // 与上面的 random 必须一致
			String wholeUrl = String.format("%s?sdkappid=%s&random=%d", Contants.yun_sms_url, Contants.sdkAppId, random);
		   
			JSONObject resultObj = HttpClientUtils.HttpRequest(wholeUrl, "POST", data.toJSONString());
			
			
		    return resultObj;
	}

}
