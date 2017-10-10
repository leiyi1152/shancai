package com.icloud.web.user.console;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.icloud.common.DateTools;
import com.icloud.common.ResponseUtils;
import com.icloud.common.dto.vo.CountVo;
import com.icloud.model.demand.Demand;
import com.icloud.model.user.SkuImport;
import com.icloud.model.user.SkuUser;
import com.icloud.model.user.User;
import com.icloud.service.demand.DemandService;
import com.icloud.service.user.SkuImportService;
import com.icloud.service.user.SkuUserService;
import com.icloud.service.user.UserService;
import com.icloud.service.user.WxUserService;
import com.icloud.web.BaseController;

@Controller
public class UserController extends BaseController<User> {

	@Autowired
	private UserService userService;
	@Autowired
	private SkuUserService skuUserService;
	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private DemandService demandService;
	
	/**
	 * 采购人员列表
	 */
	@RequestMapping("/admin/user_list")
	public String list(HttpServletRequest request, User t) throws Exception {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String companyName = request.getParameter("companyName");
		if(StringUtils.isNotBlank(companyName)){
			request.setAttribute("companyName", companyName);
			t.setCompanyName(companyName);
		}
		if(StringUtils.isNotBlank(name)){
			request.setAttribute("name", name);
			t.setName(name);
		}
		if(StringUtils.isNotBlank(phone)){
			request.setAttribute("phone", phone);
			t.setPhone(phone);
		}
		t.setStatus("1");
       PageInfo<User> page = userService.findByPage(1,10, t);
	   request.setAttribute("page", page);
	   return "user/user_list";
	}
	
	
	/**
	 * sku人员列表
	 * @param request
	 * @param t
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/sku_user_list")
	public String skuUserList(HttpServletRequest request, SkuUser t) throws Exception {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String companyName = request.getParameter("companyName");
		if(StringUtils.isNotBlank(companyName)){
			request.setAttribute("companyName", companyName);
			t.setSkuCompanyName(companyName);
		}
		if(StringUtils.isNotBlank(name)){
			request.setAttribute("name", name);
			t.setSkuName(name);
		}
		if(StringUtils.isNotBlank(phone)){
			request.setAttribute("phone", phone);
			t.setSkuPhone(phone);
		}
	   t.setStatus("1");
       PageInfo<SkuUser> page = skuUserService.findByPage(1,10, t);
	   request.setAttribute("page", page);
	   return "user/sku_user_list";
	}
	
    /*
     * 采购翻页
     * (non-Javadoc)
     * @see com.icloud.web.BaseController#getList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
	@RequestMapping("/admin/user_getList")
	public String getList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String companyName = request.getParameter("companyName");
		String pageNo = request.getParameter("pageNo");
		User user = new User();
		if(StringUtils.isNotBlank(companyName)){
			user.setCompanyName(companyName);
		}
		if(StringUtils.isNotBlank(name)){
			user.setName(name);
		}
		if(StringUtils.isNotBlank(phone)){
			user.setPhone(phone);
		}
		user.setStatus("1");
	    PageInfo<User> page =	userService.findByPage(StringUtils.isNotBlank(pageNo)?Integer.parseInt(pageNo):1, 10, user);
		List<User> list = page.getList();
		JSONObject resultObj = new JSONObject();
		JSONArray array = new JSONArray();
		if(null!=list&&list.size()>0){
			for(User u:list){
				array.add(u);
			}
			
		}
		resultObj.put("list", array);
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}
	
	
	/**
	 * sku翻页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/sku_user_getList")
	public String skuUserGetList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String companyName = request.getParameter("companyName");
		String pageNo = request.getParameter("pageNo");
		SkuUser user = new SkuUser();
		if(StringUtils.isNotBlank(companyName)){
			user.setSkuCompanyName(companyName);
		}
		if(StringUtils.isNotBlank(name)){
			user.setSkuName(name);
		}
		if(StringUtils.isNotBlank(phone)){
			user.setSkuPhone(phone);
		}
		user.setStatus("1");
	    PageInfo<SkuUser> page =	skuUserService.findByPage(StringUtils.isNotBlank(pageNo)?Integer.parseInt(pageNo):1, 10, user);
		List<SkuUser> list = page.getList();
		JSONObject resultObj = new JSONObject();
		JSONArray array = new JSONArray();
		if(null!=list&&list.size()>0){
			for(SkuUser u:list){
				array.add(u);
			}
			
		}
		resultObj.put("list", array);
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}

	@RequestMapping("/admin/user_to_input")
	@Override
	public String toinput(String id, HttpServletRequest request)
			throws Exception {
	    User u = userService.findByKey(id);
	    request.setAttribute("user", u);
		return "user/user_input";
	}
	
	@RequestMapping("/admin/sku_user_to_input")
	public String skuUsertoinput(String id, HttpServletRequest request)
			throws Exception {
	    SkuUser u = skuUserService.findByKey(id);
	    request.setAttribute("user", u);
		return "user/sku_user_input";
	}

	@RequestMapping("/admin/user_input")
	@Override
	public String input(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
         
		return null;
	}
	
	@RequestMapping("/admin/sku_user_input")
	public String skuinput(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		SkuUser skuUser = skuUserService.findByKey(id);
		JSONObject jsonObj = new JSONObject();
		if(null==skuUser){
			jsonObj.put("errCode", "0001");
			jsonObj.put("resultMsg", "当前用户不存在");
			ResponseUtils.renderJson(response, jsonObj.toJSONString());
			return null;
		}
		/*SkuImport sku = new SkuImport();
		sku.setName(skuUser.getSkuName());
		sku.setPhone(skuUser.getSkuPhone());
		skuImportService.delete(sku);
		SkuImport sku1 = skuImportService.findForObject(sku);
		sku1.setIsBind("0");
		skuImportService.update(sku1);*/
		
		
		skuUser.setSkuName("--");
		skuUser.setSkuCompanyName("--");
		skuUser.setStatus("0");
		skuUser.setWxUserId("--");
		skuUserService.update(skuUser);
		jsonObj.put("errCode", "0000");
		ResponseUtils.renderJson(response, jsonObj.toJSONString());
		return null;
	}

	@Override
	public String del(String id, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping("/admin/total_statistical")
	public String analysis(HttpServletResponse response) throws Exception{
		
		JSONObject resultObj = new JSONObject();
		SkuUser u = new SkuUser();
		u.setStatus("1");
		Integer skuTotalCount = skuUserService.findCount(u);
		resultObj.put("skuerCount", null==skuTotalCount?0:skuTotalCount);
		User u1 = new User();
		u1.setStatus("1");
		Integer buyerTotalCount = userService.findCount(u1);
		resultObj.put("buyerCount", null==buyerTotalCount?0:buyerTotalCount);
		
		/**　时间统计开始 **/
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, -1);
		Date baseTime = time.getTime();
		String baseTimeStr = DateTools.date2Str(baseTime, "yyyy-MM-dd");
		Map<String,String> map = new HashMap<String,String>();
		map.put("startTime", baseTimeStr+" 00:00:00");
		map.put("endTime", baseTimeStr+" 23:59:59");
		map.put("status", "1");
		//新增微信
		List<CountVo> wxcount = wxUserService.findCountByDays(map);
		resultObj.put("lastDayAdd", (null!=wxcount&&wxcount.size()>0)?wxcount.get(0).getCount():0);
		//新增采购 
		List<CountVo> userCount = userService.findCountByDays(map);
		resultObj.put("buyerBind", (null!=userCount&&userCount.size()>0)?userCount.get(0).getCount():0);
		//新增sku
		List<CountVo> skuUserCount =skuUserService.findCountByDays(map);
		resultObj.put("skuBind", (null!=skuUserCount&&skuUserCount.size()>0)?skuUserCount.get(0).getCount():0);
		
		Integer demandCount = demandService.findCount(new Demand());
		resultObj.put("totalDemand", demandCount);
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}

	@RequestMapping("/admin/user_days_statistical")
	public String userDaysAnalysis(HttpServletRequest request,HttpServletResponse response){
		JSONObject resultObj = new JSONObject();
		String startTimeStr = request.getParameter("s_date");
		String entTimeStr = request.getParameter("e_date");
	
		if(StringUtils.isBlank(startTimeStr)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "开始时间为空");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		if(StringUtils.isBlank(startTimeStr)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "结束时间为空");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		Date startTime = DateTools.str2Date(startTimeStr, "yyyy-MM-dd");
		Date endTime = DateTools.str2Date(entTimeStr, "yyyy-MM-dd");
		
		if(startTime.after(endTime)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "开始时间不能大于结束时间");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			 return null;
		}
		if(endTime.getTime()/1000-startTime.getTime()/1000>(30*24*60*60)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "时间间隔不能大于30天");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
		    return null;
		}
		
		SortedMap<String,Integer> datesMap = new TreeMap<String,Integer>();
		JSONArray dates = new JSONArray();
		while(startTime.getTime()<=endTime.getTime()){
			datesMap.put(DateTools.date2Str(startTime, "yyyy-MM-dd"), 0);
			dates.add(DateTools.date2Str(startTime, "yyyy-MM-dd"));
			startTime.setDate(startTime.getDate()+1);
		}
		
		resultObj.put("dates", dates);
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("startTime",startTimeStr);
		paraMap.put("endTime", entTimeStr);
		paraMap.put("status", "1");
		//1
		SortedMap<String,Integer> addCount = new TreeMap<String,Integer>(datesMap);
		JSONArray addCountArray = new JSONArray();
		List<CountVo> addCountList = wxUserService.findCountByDays(paraMap);
		for(int i=0;null!=addCountList&&i<addCountList.size();i++){
			addCount.put(addCountList.get(i).getItem(), addCountList.get(i).getCount());
		}
		for(Integer o:addCount.values()){
			addCountArray.add(o);
		}
		resultObj.put("adds", addCountArray);
		
		//2
				SortedMap<String,Integer> buyerCount = new TreeMap<String,Integer>(datesMap);
				JSONArray buyerCountArray = new JSONArray();
				List<CountVo> buyerCountList = userService.findCountByDays(paraMap);
				for(int i=0;null!=buyerCountList&&i<buyerCountList.size();i++){
					buyerCount.put(buyerCountList.get(i).getItem(), buyerCountList.get(i).getCount());
				}
				for(Integer o:buyerCount.values()){
					buyerCountArray.add(o);
				}
				resultObj.put("buyers", buyerCountArray);
	
				//3
				SortedMap<String,Integer> skuerCount = new TreeMap<String,Integer>(datesMap);
				JSONArray skuerCountArray = new JSONArray();
				List<CountVo> skuerCountList = skuUserService.findCountByDays(paraMap);
				for(int i=0;null!=skuerCountList&&i<skuerCountList.size();i++){
					skuerCount.put(skuerCountList.get(i).getItem(), skuerCountList.get(i).getCount());
				}
				for(Integer o:skuerCount.values()){
					skuerCountArray.add(o);
				}
				resultObj.put("skuers", skuerCountArray);		 
				resultObj.put("errCode", "0000");
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
		
	}
	
}
