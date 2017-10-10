package com.icloud.service.user;


import java.util.List;
import java.util.Map;

import com.icloud.common.dto.vo.CountVo;
import com.icloud.model.user.WxUser;
import com.icloud.service.BaseService;

public interface WxUserService extends BaseService<WxUser>{
	WxUser findByOpenId(String openId);
	
	WxUser getUserFromSession(String sid);
	
	List<CountVo> findCountByDays(Map<String,String> map);
	
}
