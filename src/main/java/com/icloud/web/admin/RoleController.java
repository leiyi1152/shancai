package com.icloud.web.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icloud.common.ResponseUtils;
import com.icloud.model.bms.Tmenu;
import com.icloud.model.bms.Trole;
import com.icloud.model.bms.TroleMenu;
import com.icloud.service.bms.MenuService;
import com.icloud.service.bms.RoleMenuService;
import com.icloud.service.bms.RoleService;

@Controller
public class RoleController extends AdminBaseController<Trole> {
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private MenuService menuService;

	@RequestMapping("/admin/role_list")
	public String list(HttpServletRequest request,Trole role) throws Exception {
		List<Trole> list = roleService.findList(role);
		request.setAttribute("role", role);
		request.setAttribute("list", list);
		return "bms/role_list";

	}
	@RequestMapping("/admin/role_getlist")
	public String getList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleName = request.getParameter("roleName");
		String pageNo = request.getParameter("pageNo");
		JSONObject json = new JSONObject();
		Trole role = new Trole();
		if (StringUtils.isNotBlank(roleName)) {
			json.put("roleName", roleName);
			role.setRoleName(roleName);
		}
		if (StringUtils.isNotBlank(pageNo)) {
			role.setPageNo(Integer.parseInt(pageNo));
		}
		List<Trole> list = roleService.findList(role);

		json.put("pages", role.getPages());
		JSONArray array = new JSONArray();
		for (Trole subrole : list) {
			JSONObject sub = new JSONObject();
			sub.put("id", subrole.getId());
			sub.put("roleName", subrole.getRoleName());

			array.add(sub);
		}
		json.put("list", array);
		ResponseUtils.renderJson(response, json.toJSONString());
		return null;
	}
	
	@RequestMapping("/admin/role_to_input")
	public String toinput(String id,HttpServletRequest request) throws Exception{
		List<Tmenu> mList = menuService.selectAllList();
		request.setAttribute("mList", mList);
		if(StringUtils.isNotBlank(id)){
			Trole role = roleService.findByKey(id);
			List<Tmenu> roleMenus = menuService.selectByRole(id);
			
			for(Tmenu m:roleMenus){
				for(Tmenu menu:mList){
					if(m.getId().equals(menu.getId())){
						menu.setIsHas(true);
						continue;
					}
				}
			}
			request.setAttribute("role", role);
		}else{
			request.setAttribute("role", null);
		}
		
		
		return "bms/role_input";
	}
	
	@RequestMapping("/admin/role_input")
     public String input(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   String id = request.getParameter("id");
	   String roleName = request.getParameter("roleName");
	   String[] menuIds = request.getParameterValues("menuId");
	   if(StringUtils.isNotBlank(id)){
		   TroleMenu rm = new TroleMenu();
		   rm.setRoleId(id);
		   roleMenuService.delete(rm);
		   if(null!=menuIds){
			   List<TroleMenu> rmList = new ArrayList<TroleMenu>();
			   for(int i=0;i<menuIds.length;i++){
				   rm = new TroleMenu();
				   rm.setMenuId(menuIds[i]);
				   rm.setRoleId(id);
				   rmList.add(rm);
			   }
			   roleMenuService.batchInsert(rmList);
		   }
		   ResponseUtils.renderText(response, "0001");
		   return null;
		   
	   }else{
		   int count = roleService.selectCountByName(roleName);
		   if(count>0){
			   ResponseUtils.renderText(response, "0002");
			   return null;
		   }
		   
		   
		   Trole role = new Trole();
		   role.setRoleName(roleName);
		   roleService.save(role);
		   if(menuIds!=null){
			   List<TroleMenu> rmList = new ArrayList<TroleMenu>();
			   TroleMenu rm = null;
			   for(int i=0;i<menuIds.length;i++){
				   rm = new TroleMenu();
				   rm.setMenuId(menuIds[i]);
				   rm.setRoleId(role.getId());
				   rmList.add(rm);
			   }
			   roleMenuService.batchInsert(rmList);
		   }
		   ResponseUtils.renderText(response, "0000");
		   return null;
	   }
	   
    }
	
	
	/**
	 * 删除
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/admin/role_del")
	public String del(String id,HttpServletResponse response) throws Exception{
		roleService.delete(id);
		TroleMenu rm = new TroleMenu();
		rm.setRoleId(id);
		roleMenuService.delete(rm);
		ResponseUtils.renderText(response, "0000");
		return null;
		
	}
	
	
}
