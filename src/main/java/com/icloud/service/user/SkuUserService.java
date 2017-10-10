package com.icloud.service.user;


import java.util.List;
import java.util.Map;

import com.icloud.common.dto.vo.CountVo;
import com.icloud.model.user.SkuUser;
import com.icloud.service.BaseService;

public interface SkuUserService extends BaseService<SkuUser>{
	SkuUser findByPhone(String phone);
	SkuUser findByWxUser(String wxUserId);
	List<CountVo> findCountByDays(Map<String,String> map);
}
