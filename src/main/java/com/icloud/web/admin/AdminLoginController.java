package com.icloud.web.admin;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icloud.common.MD5Utils;
import com.icloud.common.ResponseUtils;
import com.icloud.model.bms.Tadmin;
import com.icloud.model.bms.Tmenu;
import com.icloud.model.category.Category;
import com.icloud.service.bms.AdminService;
import com.icloud.service.bms.MenuService;
import com.icloud.service.category.CategoryService;


@Controller
public class AdminLoginController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/tologin")
	public String tologin(HttpSession session){
		if(null!=session.getAttribute("admin_user")&&null!=session.getAttribute("admin_menu")){
			return "index";
		}
		return "login";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam String account,@RequestParam String password,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Tadmin admin = new Tadmin();
		admin.setAccount(account);
		admin.setPassWord(MD5Utils.encode2hex(password));
		admin = adminService.login(admin);
		if(null==admin){
			ResponseUtils.renderText(response, "0001");
		}else if(admin.getIsLock().equals("1")){
			ResponseUtils.renderText(response, "0002");
		}else{
			session.setAttribute("admin_user", admin);
			List<Tmenu> list = menuService.selectMenuByUser(admin);
			session.setAttribute("admin_menu", list);
			//return "redirect:/admin";
			ResponseUtils.renderText(response, "0000");
		}
		return null;
		//return "redirect:/tologin";
		
		
		
	}
	
	@RequestMapping("/admin")
	public String index(HttpServletRequest request,HttpSession session){
		if(null!=session.getAttribute("admin_user")){		
			//取出用戶權限
			Tadmin admin = (Tadmin) session.getAttribute("admin_user");
			List<Tmenu> list = menuService.selectMenuByUser(admin);
			session.setAttribute("admin_menu", list);
			JSONArray navObj = new JSONArray();
			for(Tmenu menu:list){
				if(menu.getParentId().equals("0")){
					JSONObject obj = new JSONObject();
					obj.put("title", menu.getMenuName());
					obj.put("icon", "&#xe621;");
					obj.put("spread", true);
					JSONArray array = new JSONArray();
					for(Tmenu submenu:list){
						if(submenu.getParentId().equals(menu.getId())){
							JSONObject subobj = new JSONObject();
							subobj.put("title", submenu.getMenuName());
							subobj.put("icon", "&#xe621;");
							subobj.put("href", submenu.getMenuUrl());
							array.add(subobj);
						}
						
					}
					obj.put("children", array);
					navObj.add(obj);
				}
					
			}
			request.setAttribute("nav_obj", navObj.toJSONString());
			return "index";
		}
		
		return "tologin";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("admin_user");
		session.removeAttribute("admin_menu");
		return "tologin";
	}
	
	@RequestMapping("/analysis")
	public String analysis(HttpServletRequest request) throws Exception{
		List<Category> list = categoryService.findList(new Category());
		request.setAttribute("list", list);
		return "analysis";
	}
	
}
