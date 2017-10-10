package com.icloud.dao.bms;

import java.util.List;

import com.icloud.dao.DAO;
import com.icloud.model.bms.Trole;

public interface TroleMapper extends DAO<Trole>{

    int insertSelective(Trole record);
    
    int selectCountByName(String roleName);
    
    List<Trole> selectByAdmin(String adminId);
    
    List<Trole> selectAll();
}