package com.icloud.service.bms;

import java.util.List;

import com.icloud.model.bms.Trole;
import com.icloud.service.BaseService;

public interface RoleService extends BaseService<Trole>{
	
    int insertSelective(Trole record);
     
    int selectCountByName(String roleName);
    List<Trole> selectByAdmin(String adminId);
    List<Trole> selectAll();
}
