package com.icloud.service.user;


import java.util.List;
import java.util.Map;

import com.icloud.common.dto.vo.CountVo;
import com.icloud.model.user.User;
import com.icloud.service.BaseService;

public interface UserService extends BaseService<User>{
	User findByWxUser(String openId);
	User findByPhone(String phone);
	List<CountVo> findCountByDays(Map<String,String> map);
}
