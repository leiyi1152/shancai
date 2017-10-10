package com.icloud.service.sms.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.icloud.common.util.HttpClientUtils;
import com.icloud.model.sms.MsgRecord;
import com.icloud.service.sms.MsgRecordService;

@Component
public class SmsScheduler {
	public final static Logger logger = LoggerFactory
			.getLogger(SmsScheduler.class);
	  @Autowired
	  private MsgRecordService msgRecordService;
	
	   @Scheduled(cron="0 0/5 * * * ?") //每分钟执行一次  
	    public void statusCheck() throws Exception { 
		   logger.info("每5分钟执行一次。start。");   
		   
		   MsgRecord record = new MsgRecord();
		   record.setStatus("0");
		   List<MsgRecord> list = msgRecordService.findList(record);
		   
		   for(MsgRecord obj:list){
			   JSONObject resultObj = HttpClientUtils.HttpRequest(obj.getUrl(), "POST",obj.getMsgContent());		
			   if(null!=resultObj&&resultObj.containsKey("result") &&resultObj.getString("result").equals("0")){
				   obj.setStatus("1");
				   obj.setModifyTime(new Date());
				   msgRecordService.update(obj);
				}
		   }
	       logger.info("每5分钟执行一次。end。");  
	    }  
}
