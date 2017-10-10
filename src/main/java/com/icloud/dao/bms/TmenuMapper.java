package com.icloud.dao.bms;

import java.util.List;

import com.icloud.dao.DAO;
import com.icloud.model.bms.Tadmin;
import com.icloud.model.bms.Tmenu;

public interface TmenuMapper extends DAO<Tmenu>{


    int insertSelective(Tmenu record);
    
    List<Tmenu> selectMenuByUser(Tadmin admin);

    List<Tmenu> selectParentMenu();
    
    int selectCountByName(String menuName);
    
    List<Tmenu> selectAllList();
    
    int countByParent(String id);
  
    List<Tmenu> selectByRole(String roleId);
    
}