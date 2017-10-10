package com.icloud.dao.user;

import java.util.List;
import java.util.Map;

import com.icloud.common.dto.vo.CountVo;
import com.icloud.dao.DAO;
import com.icloud.model.user.SkuUser;

public interface SkuUserMapper extends DAO<SkuUser>{
	
	SkuUser findByPhone(String phone);
	SkuUser findByWxUser(String wxUserId);
	List<CountVo> findCountByDays(Map<String,String> map);
	
    
}