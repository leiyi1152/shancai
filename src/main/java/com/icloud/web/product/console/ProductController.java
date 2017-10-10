package com.icloud.web.product.console;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.icloud.common.ResponseUtils;
import com.icloud.model.category.Category;
import com.icloud.model.product.Product;
import com.icloud.service.category.CategoryService;
import com.icloud.service.product.ProductService;
import com.icloud.web.BaseController;

@Controller
public class ProductController extends BaseController<Product> {
    @Autowired
	private ProductService productService;
	@Autowired
    private CategoryService categoryService;
	@RequestMapping("/admin/product_list")
	public String list(HttpServletRequest request, Product t) throws Exception {
        request.setAttribute("p", t);
		PageInfo<Product> page = productService.findByPage(1, 10, t);
		request.setAttribute("page", page);
		List<Category> caList = categoryService.findList(new Category());
		request.setAttribute("caList", caList);
		return "product/product_list";
	}

	@RequestMapping("/admin/product_page_json")
	public String getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productName = request.getParameter("productName");
        String pageNo = request.getParameter("pageNo");
		Product t = new Product();
		if(StringUtils.isNotBlank(productName)){
        	t.setProductName(productName);
		}	
		
		PageInfo<Product> page = productService.findByPage(StringUtils.isNotBlank(pageNo)?Integer.parseInt(pageNo):1, 10, t);
        JSONObject resultObj = new JSONObject();
        resultObj.put("list", page.getList());
        ResponseUtils.renderJson(response,resultObj.toJSONString());
		return null;
	}

	@RequestMapping("/admin/product_to_input")
	public String toinput(String id, HttpServletRequest request) throws Exception {
		List<Category> caList = categoryService.findList(new Category());
		request.setAttribute("caList", caList);
		if(StringUtils.isNotBlank(id)){
			Product p = productService.findByKey(id);
			request.setAttribute("product", p);
		}
		return "product/product_input";
	}

	@RequestMapping("/admin/product_input")
	public String input(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String productName = request.getParameter("productName");
		String productDesc = request.getParameter("productDesc");
		String productPics = request.getParameter("productPics");
		String categoryId = request.getParameter("categoryId");
		if(StringUtils.isNotBlank(id)){
			Product p = productService.findByKey(id);
			p.setProductDesc(productDesc);
			p.setProductName(productName);
			p.setProductPics(productPics);
			p.setCategoryId(categoryId);
			productService.update(p);
			ResponseUtils.renderHtml(response, "0000");
		}else{
			Product p = new Product();
			p.setProductDesc(productDesc);
			p.setProductName(productName);
			p.setProductPics(productPics);
			p.setCategoryId(categoryId);
			productService.save(p);
			ResponseUtils.renderHtml(response, "1000");
		}
		return null;
	}

	@RequestMapping("/admin/product_del")
	public String del(String id, HttpServletResponse response) throws Exception {
		productService.delete(id);
		ResponseUtils.renderHtml(response, "0000");
		return null;
	}

}
