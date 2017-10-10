package com.icloud.service.sms.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.icloud.common.Contants;
import com.icloud.common.DateTools;
import com.icloud.common.sms.SmsSenderUtil;
import com.icloud.common.util.HttpClientUtils;
import com.icloud.dao.sms.MsgRecordMapper;
import com.icloud.dao.user.SkuUserMapper;
import com.icloud.model.demand.Demand;
import com.icloud.model.sms.MsgRecord;
import com.icloud.model.user.SkuUser;
import com.icloud.service.sms.MsgRecordService;
import com.icloud.service.user.SkuUserService;

@Service
@Transactional
public class MsgRecordServiceImpl implements MsgRecordService {
	
	public final static Logger logger = LoggerFactory
			.getLogger(MsgRecordServiceImpl.class);
	@Autowired
	private MsgRecordMapper msgRecordMapper;
	@Autowired
	private SkuUserMapper skuUserMapper;

	@Override
	public void save(MsgRecord t) throws Exception {
		msgRecordMapper.save(t);
	}

	@Override
	public void update(MsgRecord t) throws Exception {
		msgRecordMapper.update(t);
	}

	@Override
	public List<MsgRecord> findList(MsgRecord t) throws Exception {
		return msgRecordMapper.findForList(t);
	}

	@Override
	public Integer findCount(MsgRecord t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public MsgRecord findByKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<MsgRecord> findByPage(int pageNo, int pageSize, MsgRecord t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAnRecord(Demand demand) throws Exception {
		
		    SkuUser skuUser = skuUserMapper.findForObject(demand.getResponseId());
		    SmsSenderUtil util = new SmsSenderUtil();
		  
		    long random = util.getRandom();
	        long curTime = System.currentTimeMillis()/1000;
            String phone = demand.getPublishedPhone();
			JSONObject data = new JSONObject();

	        JSONObject tel = new JSONObject();
	        tel.put("nationcode", "86");
	        tel.put("mobile",phone);

	        data.put("type", "0");
	        data.put("msg", String.format(Contants.responseSmsTemplate,DateTools.date2Str(demand.getPushlishedTime(), "yyyy-MM-dd HH:mm"),demand.getCategory().getCategoryName(),skuUser.getSkuName()));
	        data.put("sig", util.strToHash(String.format(
	        		"appkey=%s&random=%d&time=%d&mobile=%s",
	        		Contants.strAppKey, random, curTime, phone)));
	        data.put("tel", tel);
	        data.put("time", curTime);
	        data.put("extend", "");
	        data.put("ext", "");
	        // 与上面的 random 必须一致
	   		String wholeUrl = String.format("%s?sdkappid=%s&random=%d", Contants.yun_sms_url, Contants.sdkAppId, random);
	   		   
	        MsgRecord msg = new MsgRecord();
	        msg.setCreateTime(new Date());
	        msg.setModifyTime(new Date());
	        msg.setMsgContent(data.toJSONString());
	        msg.setReceiveObj(phone);
	        msg.setStatus("0");
	        msg.setType("1");
	        msg.setUrl(wholeUrl);
	        msgRecordMapper.save(msg);

	   
			JSONObject resultObj = HttpClientUtils.HttpRequest(wholeUrl, "POST", data.toJSONString());		
			logger.info(resultObj.toJSONString());
			if(null!=resultObj&&resultObj.containsKey("result")&&resultObj.getString("result").equals("0")){
				msg.setStatus("1");
				msgRecordMapper.update(msg);
			}
	}

}
