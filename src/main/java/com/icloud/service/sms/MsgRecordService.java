package com.icloud.service.sms;

import com.icloud.model.demand.Demand;
import com.icloud.model.sms.MsgRecord;
import com.icloud.service.BaseService;

public interface MsgRecordService extends BaseService<MsgRecord> {
	
	//保存一条待发送记录 新需求被响应时
	public void saveAnRecord(Demand demand)throws Exception;

}
