package com.icloud.web.product.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.icloud.common.ResponseUtils;
import com.icloud.model.product.Product;
import com.icloud.service.product.ProductService;
import com.icloud.web.AppBaseController;

@RestController
public class AppProductController extends AppBaseController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/getProductList")
	public String getProductList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject parmObj = super.readToJSONObect(request);
		
		String pageNo = parmObj.getString("pageNo");
		String pageSize = parmObj.getString("pageSize");
		String productTypeId = parmObj.getString("productTypeId");
		JSONObject resultObj = new JSONObject();
		Product pp =  new Product();
		if(StringUtils.isNotBlank(productTypeId)){
		  pp.setCategoryId(productTypeId);
		}
		PageInfo<Product> page = productService.findByPage(StringUtils.isNotBlank(pageNo)?Integer.parseInt(pageNo):1, StringUtils.isNotBlank(pageSize)?Integer.parseInt(pageSize):10,pp);
		List<Product> list = page.getList();
		JSONArray array = new JSONArray();
		JSONObject arrObj = new JSONObject();
		if(null!=list){
			for(Product p:list){
				JSONObject o = new JSONObject();
				o.put("productName", p.getProductName());
				o.put("productDescription", p.getProductDesc());
				o.put("productPic", p.getProductPics());
				o.put("productTypeId", p.getCategoryId());
				array.add(o);
			}
		}
		arrObj.put("totalCount", page.getTotal());
		arrObj.put("hasMore",  page.getPages() > page.getPageNum());
		arrObj.put("list", array);	
		resultObj.put("resultData", arrObj);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "获取列表成功");
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
		
		
	}
}
