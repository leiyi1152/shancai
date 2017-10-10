package com.icloud.dao.bms;

import java.util.List;

import com.icloud.model.bms.TadminRole;

public interface TadminRoleMapper {
    int insert(TadminRole record);

    int insertSelective(TadminRole record);
    
    void delele(String adminId);
    
    void batchInsert(List<TadminRole> list);
}