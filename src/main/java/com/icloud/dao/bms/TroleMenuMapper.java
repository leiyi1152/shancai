package com.icloud.dao.bms;

import java.util.List;

import com.icloud.model.bms.TroleMenu;

public interface TroleMenuMapper {
    int insert(TroleMenu record);

    int insertSelective(TroleMenu record);
    
    void delete(TroleMenu roleMenu);
    
    void batchInsert(List<TroleMenu> list);
}