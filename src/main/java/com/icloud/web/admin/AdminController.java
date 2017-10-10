package com.icloud.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icloud.common.DateTools;
import com.icloud.common.MD5Utils;
import com.icloud.common.ResponseUtils;
import com.icloud.model.bms.Tadmin;
import com.icloud.model.bms.TadminRole;
import com.icloud.model.bms.Trole;
import com.icloud.service.bms.AdminRoleService;
import com.icloud.service.bms.AdminService;
import com.icloud.service.bms.RoleService;


@Controller
public class AdminController extends AdminBaseController<Tadmin> {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminRoleService adminRoleService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 跳转列表页
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/admin/admin_list")
	public String list(HttpServletRequest request,Tadmin admin ) throws Exception{	
		List<Tadmin> list = adminService.findList(admin);
		request.setAttribute("admin", admin);
		request.setAttribute("list", list);
		return "bms/admin_list";
	}

	/**
	 * 获取列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/admin/getlist")
	public String getList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String account = request.getParameter("account");
		String pageNo = request.getParameter("pageNo");		
		JSONObject json = new JSONObject();
		Tadmin admin = new Tadmin();
		if(StringUtils.isNotBlank(account)){
			json.put("account", account);
			admin.setAccount(account);
		}
		if(StringUtils.isNotBlank(pageNo)){
			admin.setPageNo(Integer.parseInt(pageNo));
		}
		List<Tadmin> list = adminService.findList(admin);
		
		json.put("pages", admin.getPages());
		JSONArray array = new JSONArray();
		for(Tadmin subadmin:list){
			JSONObject sub = new JSONObject();
			sub.put("id",subadmin.getId());
			sub.put("account", subadmin.getAccount());
			sub.put("isLock", subadmin.getIsLock());
            sub.put("createDate", DateTools.convertDateToString(subadmin.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			array.add(sub);
		}
		json.put("list", array);
		ResponseUtils.renderJson(response, json.toJSONString());
		return null;

	}
	
	/**
	 * 管理员输入
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/admin/admin_to_input")
	public String toinput(String id,HttpServletRequest request) throws Exception{
		
		List<Trole> allRole = roleService.selectAll();
		request.setAttribute("rList", allRole);
		if(StringUtils.isNotBlank(id)){
		  Tadmin admin= adminService.findByKey(id);
		  List<Trole> aList =  roleService.selectByAdmin(admin.getId());
		   
		   for(Trole role:aList){
			   for(Trole r:allRole){
				   if(r.getId().equals(role.getId())){
					   r.setIsHas(true);
					   continue;
				   }
			   }
		   }
		   
		   request.setAttribute("admin", admin);
		}else{
			request.setAttribute("admin", null);
		}
		return "bms/admin_input";
	
	}
	/**
	 * 管理员添加
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/admin/admin_input")
	public String input(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String isLock = request.getParameter("isLock");
		String passwordPrompt = request.getParameter("passwordPrompt");
		String[] roleIds =  request.getParameterValues("roleId");
		if(StringUtils.isBlank(id)){
			int count = adminService.selectCountByAccount(account);
			if(count>0){
				//账号已被使用
				ResponseUtils.renderHtml(response, "0001");
				return null;
			}
			
			Tadmin admin = new Tadmin();
			admin.setAccount(account);
			admin.setIsLock(StringUtils.isNotBlank(isLock)&&isLock.equals("on")?"0":"1");
			admin.setPassWord(MD5Utils.encode2hex(password));
			admin.setPasswordPrompt(passwordPrompt);
			admin.setCreateDate(new Date());
			adminService.save(admin);
			if(null!=roleIds){
				TadminRole ar = null;
				List<TadminRole> list = new ArrayList<TadminRole>();
				for(int i=0;i<roleIds.length;i++){
					ar = new TadminRole();
					ar.setAdminId(admin.getId());
					ar.setRoleId(roleIds[i]);
					list.add(ar);
				}
				adminRoleService.batchInsert(list);
			}
			ResponseUtils.renderHtml(response, "0000");
			return null;
		}else{
			Tadmin admin =adminService.findByKey(id);
			if(!admin.getPassWord().equals(password)){
				admin.setPassWord(MD5Utils.encode2hex(password));
			}
			admin.setPasswordPrompt(passwordPrompt);
			if(StringUtils.isNotBlank(isLock)&&isLock.equals("on")){
				 admin.setIsLock("0");
			}
			adminService.update(admin);
			if(null!=roleIds){
				adminRoleService.delele(admin.getId());
				TadminRole ar = null;
				List<TadminRole> list = new ArrayList<TadminRole>();
				for(int i=0;i<roleIds.length;i++){
					ar = new TadminRole();
					ar.setAdminId(admin.getId());
					ar.setRoleId(roleIds[i]);
					list.add(ar);
				}
				adminRoleService.batchInsert(list);
			}
			ResponseUtils.renderHtml(response, "0002");
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
	@RequestMapping("/admin/admin_del")
	public String del(@RequestParam  String id,HttpServletResponse response) throws Exception{
		adminService.delete(id);
		adminRoleService.delele(id);
		ResponseUtils.renderHtml(response, "0000");
		return null;
		
	}
	
	@RequestMapping("/admin/admin_pass")
	public String passEdit(String id,HttpServletRequest request) throws Exception{
		Tadmin admin = adminService.findByKey(id);
		 request.setAttribute("admin", admin);
		 return "/bms/admin_pass";
	}
	
}
