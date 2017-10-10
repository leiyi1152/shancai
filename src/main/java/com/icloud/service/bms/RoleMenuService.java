package com.icloud.service.bms;

import java.util.List;

import com.icloud.model.bms.TroleMenu;

public interface RoleMenuService {
	int insert(TroleMenu record);

    int insertSelective(TroleMenu record);
    
    void delete(TroleMenu record);
    
    void batchInsert(List<TroleMenu> list);

}
