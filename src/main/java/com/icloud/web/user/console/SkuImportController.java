package com.icloud.web.user.console;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.icloud.common.ResponseUtils;
import com.icloud.model.user.SkuImport;
import com.icloud.service.user.SkuImportService;
import com.icloud.web.BaseController;

@Controller
public class SkuImportController extends BaseController<SkuImport> {
	
	@Autowired
	private SkuImportService skuImportService;
	
	
	@RequestMapping("/admin/to_imort")
	public String toimportSku(){
		return "user/sku_import";
	}
	@RequestMapping("/admin/sku_imort")
	public String importSku(@RequestParam("excelfile") MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		String fileName = file.getOriginalFilename();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		JSONObject resultObj = new JSONObject();
		File temFile;
			try {
				temFile = File.createTempFile("temp", "."+extension);
				file.transferTo(temFile);
				try {
					int count = skuImportService.importSku(temFile);
					resultObj.put("count", count);
					resultObj.put("errCode", "0000");
					
					ResponseUtils.renderJson(response,resultObj.toJSONString());
					return null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultObj.put("count", 0);
			resultObj.put("errCode", "0001");
			
			ResponseUtils.renderJson(response,resultObj.toJSONString());
		return null;
		
	}
	

	@RequestMapping("/admin/sku_import_list")
	public String list(HttpServletRequest request, SkuImport t)
			throws Exception {
		PageInfo<SkuImport> page = skuImportService.findForList(1, 10, t);
		request.setAttribute("page", page);
		return "user/sku_import_list";
	}

	@RequestMapping("/admin/sku_getlist")
	@Override
	public String getList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String pageNo =request.getParameter("pageNo");
		SkuImport sku = new SkuImport();
		if(StringUtils.isNotBlank(name))
		sku.setName(name);
		if(StringUtils.isNotBlank(phone))
		sku.setPhone(phone);
		PageInfo<SkuImport> page = skuImportService.findForList(StringUtils.isNotBlank(pageNo)?Integer.parseInt(pageNo):1, 10, sku);
		List<SkuImport> list = page.getList();
		JSONObject resultObj = new JSONObject();
		JSONArray array = new JSONArray();
		if(null!=list&&list.size()>0){
			
			for(SkuImport sku1:list){
				array.add(sku1);
			}
			
		}
		resultObj.put("list", array);
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}

	@RequestMapping("/admin/sku_to_input")
	@Override
	public String toinput(String id, HttpServletRequest request)
			throws Exception {
		SkuImport sku = skuImportService.findForKey(id);
		request.setAttribute("sku", sku);
		return "user/sku_input";
	
	}

	@RequestMapping("/admin/sku_input")
	@Override
	public String input(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String isBind = request.getParameter("isBind");
		SkuImport sku = null;
		if(StringUtils.isNotBlank(id)){
			sku = skuImportService.findForKey(id);
		}
		if(null==sku){
			sku = new SkuImport();
			sku.setPhone(phone);
			sku.setName(name);
			sku.setIsBind(StringUtils.isNotBlank(isBind)?isBind:"0");
			List<SkuImport> list = new ArrayList<>();
			list.add(sku);
			skuImportService.bathInsert(list);
			ResponseUtils.renderHtml(response, "0001");
			return null;
		}
		
		
		sku.setPhone(phone);
		sku.setName(name);
		sku.setIsBind(StringUtils.isNotBlank(isBind)?isBind:"0");
		
		skuImportService.update(sku);
		ResponseUtils.renderHtml(response, "0000");
		return null;
	}

    @RequestMapping("/admin/sku_del")
	public String del(String id, HttpServletResponse response)
			throws Exception {
           skuImportService.deleteByKey(id);
		   return null;
	}

}
