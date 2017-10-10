package com.icloud.dao.bms;
import com.icloud.dao.DAO;
import com.icloud.model.bms.Tadmin;

public interface TadminMapper extends DAO<Tadmin>{
	
	public Integer findCountByAccount(String account);
    
}