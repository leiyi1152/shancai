package com.icloud.web.admin;

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
import com.icloud.model.bms.TroleMenu;
import com.icloud.service.bms.MenuService;
import com.icloud.service.bms.RoleMenuService;

@Controller
public class MenuController extends AdminBaseController<Tmenu> {
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleMenuService roleMenuService;
	
	
	
	@RequestMapping("/admin/menu_list")
	public String list(HttpServletRequest request,Tmenu menu) throws Exception{
	  List<Tmenu> list = menuService.findList(menu);
	  request.setAttribute("menu", menu);
	  request.setAttribute("list", list);
	  return "bms/menu_list";
	}
	
	@RequestMapping("/admin/menu_getlist")
	public String getList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String menuName = request.getParameter("menuName");
		String pageNo = request.getParameter("pageNo");		
		JSONObject json = new JSONObject();
		Tmenu menu = new Tmenu();
		if(StringUtils.isNotBlank(menuName)){
			menu.setMenuName(menuName);
		}
		if(StringUtils.isNotBlank(pageNo)){
			menu.setPageNo(Integer.parseInt(pageNo));
		}
		List<Tmenu> list = menuService.findList(menu);
		json.put("pages", menu.getPages());
		JSONArray array = new JSONArray();
		for(Tmenu m:list){
			JSONObject sub = new JSONObject();
			sub.put("id",m.getId());
			sub.put("menuName", m.getMenuName());
			sub.put("menuUrl", m.getMenuUrl());
			
			if(m.getParentId().equals("0")){
				sub.put("parentName", "一级菜单");
			}else{
				sub.put("parentName",m.getParent().getMenuName());
			}
			
			array.add(sub);
		}
		json.put("list", array);
		ResponseUtils.renderJson(response, json.toJSONString());
		return null;
		
		
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/admin/menu_to_input")
	public String toinput(String id, HttpServletRequest request) throws Exception{
		if(StringUtils.isNotBlank(id)){
			Tmenu menu = menuService.findByKey(id);
			request.setAttribute("menu", menu);
		}else{
			request.setAttribute("menu", null);
		}
		
		List<Tmenu> list = menuService.selectParentMenu();
		request.setAttribute("list", list);
		
		return "bms/menu_input";
		
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/admin/menu_input")
	public String input(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String id = request.getParameter("id");
		String menuName = request.getParameter("menuName");
		String menuUrl = request.getParameter("menuUrl");
		String parentId= request.getParameter("parentId"); 
		String sortNum = request.getParameter("sortNum");
		
		
		if(StringUtils.isNotBlank(id)){
			Tmenu menu = menuService.findByKey(id);
			menu.setMenuUrl(menuUrl);
			menu.setParentId(parentId);
			menu.setSortNum(Integer.parseInt(sortNum));
			menuService.update(menu);
			ResponseUtils.renderText(response, "0002");
			return null;
			
		}else{
			int count = menuService.selectCountByName(menuName);
			if(count>0){
				ResponseUtils.renderText(response, "0001");
				return null;
			}
			
			Tmenu menu = new Tmenu();
			menu.setMenuName(menuName);
			menu.setMenuUrl(menuUrl);
			menu.setParentId(parentId);
			menu.setSortNum(Integer.parseInt(sortNum));
			menuService.save(menu);
		}
		
		ResponseUtils.renderText(response, "0000");
		return null;
	}
	
	@RequestMapping("/admin/menu_del")
	public String del(String id, HttpServletResponse response) throws Exception {
	
		Tmenu menu = menuService.findByKey(id);
		if(null!=menu){
			if(menu.getParentId().equals("0")){
				int count = menuService.countByParent(id);
				if(count>0){
					ResponseUtils.renderText(response, "0001");
					return null;
				}else{
					menuService.delete(id);
					TroleMenu rm = new TroleMenu();
					rm.setMenuId(id);
				    roleMenuService.delete(rm);
				}
			}else{
				 menuService.delete(id);
				 TroleMenu rm = new TroleMenu();
			     rm.setMenuId(id);
				 roleMenuService.delete(rm);
			}
		}
		ResponseUtils.renderText(response, "0001");
		return null;
	}

	
	
	
	
}
