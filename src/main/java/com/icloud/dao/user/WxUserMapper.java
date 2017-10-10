package com.icloud.dao.user;

import java.util.List;
import java.util.Map;

import com.icloud.common.dto.vo.CountVo;
import com.icloud.dao.DAO;
import com.icloud.model.user.WxUser;

public interface WxUserMapper extends DAO<WxUser>{
	
	WxUser findByOpenId(String openId);
	List<CountVo> findCountByDays(Map<String,String> map);
    
}