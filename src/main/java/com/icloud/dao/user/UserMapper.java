package com.icloud.dao.user;

import java.util.List;
import java.util.Map;

import com.icloud.common.dto.vo.CountVo;
import com.icloud.dao.DAO;
import com.icloud.model.user.User;

public interface UserMapper extends DAO<User>{
	
	User findByWxUser(String openId);
	User findByPhone(String phone);
	List<CountVo> findCountByDays(Map<String,String> map);
    
}