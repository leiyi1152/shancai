package com.icloud.service.bms;

import java.util.List;

import com.icloud.model.bms.Tadmin;
import com.icloud.model.bms.Tmenu;
import com.icloud.service.BaseService;

public interface MenuService extends BaseService<Tmenu> {

	List<Tmenu> selectMenuByUser(Tadmin admin);
	
	List<Tmenu> selectParentMenu();
	
	int insertSelective(Tmenu record);
	int selectCountByName(String menuName);
	List<Tmenu> selectAllList();

	int countByParent(String id);

	 List<Tmenu> selectByRole(String roleId);
}
