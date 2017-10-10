package com.icloud.web.demand.console;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.icloud.common.ResponseUtils;
import com.icloud.model.category.Category;
import com.icloud.service.category.CategoryService;
import com.icloud.web.BaseController;

@Controller
public class CategoryController extends BaseController<Category> {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/admin/category_list")
	public String list(HttpServletRequest request, Category t)
			throws Exception {
		String name = request.getParameter("categoryName");
		if(StringUtils.isNotBlank(name)){
			request.setAttribute("categoryName", name);
		t.setCategoryName(name);
		}
		PageInfo<Category> page = categoryService.findByPage(1, 10, t);
		request.setAttribute("page", page);
		return "category/category_list";
	}

	@RequestMapping("/admin/category_getList")
	public String getList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("categoryName");
		Category category = new Category();
		if(StringUtils.isNotBlank(name)){
		category.setCategoryName(name);
		}
		String pageNo = request.getParameter("pageNo");
		PageInfo<Category> page = categoryService.findByPage(StringUtils.isNotBlank(pageNo)?Integer.parseInt(pageNo):1, 10, category);
		List<Category> list = page.getList();
		JSONObject resultObj = new JSONObject();
		JSONArray array = new JSONArray();
		if(null!=list&&list.size()>0){
			for(Category ca:list){
				array.add(ca);
			}
		}
		resultObj.put("list", array);
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}
	@RequestMapping("/admin/category_to_input")
	public String toinput(String id, HttpServletRequest request)
			throws Exception {
		Category category = categoryService.findByKey(id);
		request.setAttribute("category", category);
		return "category/category_input";
	}

	@RequestMapping("/admin/category_input")
	public String input(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        String categoryName = request.getParameter("categoryName");
        String id = request.getParameter("id");
        String deletStatus = request.getParameter("deletStatus");
        String icon = request.getParameter("icon");
        Category category = null;
        Category hasCategory = categoryService.checkName(categoryName);
        if(StringUtils.isNotBlank(id)){
        	category = categoryService.findByKey(id);
        	if(null!=hasCategory){
        		if(!hasCategory.getId().equals(id)){
        			ResponseUtils.renderHtml(response, "2001");
        		}else{
        			category.setDeletStatus(StringUtils.isBlank(deletStatus)?"0":"1");
            		category.setCategoryName(categoryName);
            		category.setIcon(icon);
            		categoryService.update(category);
            		ResponseUtils.renderHtml(response, "2000");
        		}
        	}else{
        		category.setDeletStatus(StringUtils.isBlank(deletStatus)?"0":"1");
        		category.setCategoryName(categoryName);
        		category.setIcon(icon);
        		categoryService.update(category);
        		ResponseUtils.renderHtml(response, "2000");
        	}
        }else{
        	category= new Category();
        	category.setDeletStatus(StringUtils.isBlank(deletStatus)?"0":"1");
        	category.setCategoryName(categoryName);
        	category.setIcon(icon);
        	category.setParentId("0");
        	if(null!=hasCategory){
        		if(!hasCategory.getId().equals(id)){
        			ResponseUtils.renderHtml(response, "2001");
        		}
        	}else{
        		categoryService.save(category);
        		ResponseUtils.renderHtml(response, "0000");
        	}
        }
        
        
		return null;
	}
	@RequestMapping("/admin/category_del")
	public String del(String id, HttpServletResponse response)
			throws Exception {
		Category category = categoryService.findByKey(id);
		if(null!=category){
			category.setDeletStatus(category.getDeletStatus().equals("0")?"1":"0");
			categoryService.update(category);
		}
		return null;
	}

}
