package com.icloud.web.demand.console;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.icloud.common.DateTools;
import com.icloud.common.ResponseUtils;
import com.icloud.common.dto.vo.CountVo;
import com.icloud.model.category.Category;
import com.icloud.model.demand.Comments;
import com.icloud.model.demand.Demand;
import com.icloud.model.demand.DemandExt;
import com.icloud.model.demand.DemandLog;
import com.icloud.model.demand.DemandStatus;
import com.icloud.model.demand.TerminationRecord;
import com.icloud.model.user.SkuUser;
import com.icloud.service.category.CategoryService;
import com.icloud.service.demand.CommentsService;
import com.icloud.service.demand.DemandExtService;
import com.icloud.service.demand.DemandLogService;
import com.icloud.service.demand.DemandService;
import com.icloud.service.demand.TerminationRecordService;
import com.icloud.service.user.SkuUserService;
import com.icloud.web.BaseController;

@Controller
public class DemandController extends BaseController<Demand> {
	
	@Autowired
	private TerminationRecordService terminationRecordService;

	@Autowired
	private DemandService demandService;
	@Autowired
	private DemandExtService demandExtService;
	@Autowired
	private DemandLogService demandLogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentsService commentsService;
	@Autowired
	private SkuUserService skuUserService;
	
	
	
	@RequestMapping("/admin/demand_list")
	@Override
	public String list(HttpServletRequest request, Demand demand) throws Exception {
		String publishedName = request.getParameter("publishedName");
		String publishedPhone = request.getParameter("publishedPhone");
		String demandStatus = request.getParameter("demandStatus");
	    String publishedTimeStart = request.getParameter("publishedTimeStart");
	    String publishedTimeEnd = request.getParameter("publishedTimeEnd");
	    String categoryId = request.getParameter("categoryId");
	    if(StringUtils.isNotBlank(publishedName)){
	    	request.setAttribute("publishedName", publishedName);
	    	demand.setPublishedName(publishedName);
	    }
	    if(StringUtils.isNotBlank(publishedPhone)){
	    	request.setAttribute("publishedPhone", publishedPhone);
	    	demand.setPublishedPhone(publishedPhone);
	    }
	    if(StringUtils.isNotBlank(demandStatus)){
	    	request.setAttribute("demandStatus", demandStatus);
	    	demand.setDemandStatus(demandStatus);
	    }
	    if(StringUtils.isNotBlank(publishedTimeStart)){
	    	request.setAttribute("publishedTimeStart", publishedTimeStart);
	    	publishedTimeStart += " 00:00:00";
	    	demand.setPushlishedStartTime(DateTools.str2Date(publishedTimeStart, "yyyy-MM-dd HH:mm:ss"));
	    }
	    if(StringUtils.isNotBlank(publishedTimeEnd)){
	    	request.setAttribute("publishedTimeEnd", publishedTimeEnd);
	    	publishedTimeEnd += " 23:59:59";
	    	demand.setPushlishedEndTime(DateTools.str2Date(publishedTimeEnd, "yyyy-MM-dd HH:mm:ss"));
	    }
	    if(StringUtils.isNotBlank(categoryId)){
	    	request.setAttribute("categoryId", categoryId);
	    	demand.setCategoryId(categoryId);
	    }
		List<Category> list = categoryService.findList(new Category());
		request.setAttribute("list", list);
		PageInfo<Demand> page = demandService.findByPage(1, 10, demand);
		request.setAttribute("page", page);
		return "demand/demand_list";
	}

	@RequestMapping("/admin/demand_getList")
	@Override
	public String getList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String publishedName = request.getParameter("publishedName");
		String publishedPhone = request.getParameter("publishedPhone");
		String demandStatus = request.getParameter("demandStatus");
	    String publishedTimeStart = request.getParameter("publishedTimeStart");
	    String publishedTimeEnd = request.getParameter("publishedTimeEnd");
	    String categoryId = request.getParameter("categoryId");
	    String pageNo = request.getParameter("pageNo");
	    Demand demand = new Demand();
	    if(StringUtils.isNotBlank(publishedName)){
	    	demand.setPublishedName(publishedName);
	    }
	    if(StringUtils.isNotBlank(publishedPhone)){
	    	demand.setPublishedPhone(publishedPhone);
	    }
	    if(StringUtils.isNotBlank(demandStatus)){
	    	demand.setDemandStatus(demandStatus);
	    }
	    if(StringUtils.isNotBlank(publishedTimeStart)){
	    	publishedTimeStart += " 00:00:00";
	    	demand.setPushlishedStartTime(DateTools.str2Date(publishedTimeStart, "yyyy-MM-dd HH:mm:ss"));
	    }
	    if(StringUtils.isNotBlank(publishedTimeEnd)){
	    	publishedTimeEnd += " 23:59:59";
	    	demand.setPushlishedEndTime(DateTools.str2Date(publishedTimeEnd, "yyyy-MM-dd HH:mm:ss"));
	    }
	    if(StringUtils.isNotBlank(categoryId)){
	    	demand.setCategoryId(categoryId);
	    }
	    PageInfo<Demand> page = demandService.findByPage(StringUtils.isNotBlank(pageNo)?Integer.parseInt(pageNo):1, 10, demand);
	    List<Demand> list = page.getList();
	    JSONObject resultObj = new JSONObject();
	    JSONArray array = new JSONArray();
	    if(null!=list&&list.size()>0){
	    	for(Demand de:list){
	    		JSONObject obj = new JSONObject();
	    		obj.put("publishedName", de.getPublishedName());
	    		obj.put("publishedPhone", de.getPublishedPhone());
	    		obj.put("categoryName", de.getCategory().getCategoryName());
	    		obj.put("id", de.getId());
	    		obj.put("demandStatus", de.getDemandStatus());
	    		obj.put("publishedTime", DateTools.convertDateToString(de.getPushlishedTime(),"yyyy-MM-dd HH:mm:ss"));
	    	    obj.put("demandStatus", (DemandStatus.values()[Integer.parseInt(de.getDemandStatus())]).getName());
	    		array.add(obj);
	    	}
	    }
	    resultObj.put("list", array);
	    ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}
    
	@RequestMapping("/admin/demand_getDemand")
	public String getDenamd(String id,HttpServletRequest request ,HttpServletResponse response) throws Exception{
		DemandExt ext = demandExtService.selectByDemand(id);
		JSONObject resultObj = new JSONObject();
		resultObj.put("title", ext.getDemandTitle());
		resultObj.put("skuer", ext.getSkuUserName());
		resultObj.put("skuerPhone", ext.getSkuUserPhone());
		resultObj.put("skuerCompany", ext.getSkuCompany());
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
		
	}
	
	@RequestMapping("/admin/demand_to_input")
	@Override
	public String toinput(String id, HttpServletRequest request)
			throws Exception {
		Demand demand = demandService.findByKey(id);
		DemandExt ext = demandExtService.selectByDemand(id);
		List<DemandLog> logList = demandLogService.findByDemand(id);
		List<Comments> commentList = commentsService.findByDemand(1, 20, demand.getId()).getList();
		request.setAttribute("demand", demand);
		request.setAttribute("demandExt", ext);
		request.setAttribute("logList", logList);
		request.setAttribute("commentList", commentList);
		return "demand/demand_detail";
	}

	@Override
	public String input(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String del(String id, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping("/admin/demand_to_termination")
	public String getTerminationRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		TerminationRecord record = terminationRecordService.findByDemand(id);
		request.setAttribute("record", record);
		return "demand/demand_termination";
	}
	
	@RequestMapping("/admin/demand_termination")
	public String terminationRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String desc = request.getParameter("desc");
		String type = request.getParameter("type");
		TerminationRecord record = terminationRecordService.findByDemand(id);
		record.setModifyTime(new Date());
		record.setRefuseReason(desc);
		record.setStatus(type);
		terminationRecordService.update(record);
		
		Demand demand = demandService.findByKey(id);
		DemandLog log = new DemandLog();
		log.setCreateTime(new Date());
		log.setDemandId(demand.getId());
		log.setLogUser("管理员");
		if(type.equals("1")){
			demand.setDemandStatus(DemandStatus.terminationed.ordinal()+"");
			log.setDemandStatus(DemandStatus.terminationed.ordinal()+"");
			log.setLogInfo("需求终止审核[成功]");
			    Comments comment = new Comments();
				SkuUser skuUser = skuUserService.findByKey(demand.getResponseId());
				comment.setNick(StringUtils.isNotBlank(skuUser.getSkuName())?skuUser.getSkuName():skuUser.getNick());
				comment.setUserId(skuUser.getId());
				
				comment.setContent("申请中止响应需求审核已经通过！");
				comment.setPictures("[]");
				comment.setCreateTime(new Date());
				comment.setHeadImg(skuUser.getWxHeadImg());
				comment.setDemandId(id);
				commentsService.save(comment);
		}else if(type.equals("2")){
			demand.setDemandStatus(demand.getTerminationStatus());
			log.setLogInfo("需求终止审核[失败]：状态回滚至："+ DemandStatus.values()[Integer.parseInt(demand.getDemandStatus())].getName());
			log.setDemandStatus(demand.getDemandStatus());
			Comments comment = new Comments();
			SkuUser skuUser = skuUserService.findByKey(demand.getResponseId());
			comment.setNick(StringUtils.isNotBlank(skuUser.getSkuName())?skuUser.getSkuName():skuUser.getNick());
			comment.setUserId(skuUser.getId());
			
			comment.setContent("申请中止响应需求审核失败！");
			comment.setPictures("[]");
			comment.setCreateTime(new Date());
			comment.setHeadImg(skuUser.getWxHeadImg());
			comment.setDemandId(id);
			commentsService.save(comment);
		}else if(type.equals("3")){
			demand.setDemandStatus(demand.getTerminationStatus());
			log.setLogInfo("需求重启：状态回滚至："+ DemandStatus.values()[Integer.parseInt(demand.getDemandStatus())].getName());
		}
		
		demandService.update(demand);
		demandLogService.save(log);
		
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("errCode", "0000");
		resultObj.put("status", DemandStatus.values()[Integer.parseInt(demand.getDemandStatus())].getName());
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}
	
	/**
	 * 统计按品类分组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/demand_analysis")
	public String demanaAnalysis(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String s_date = request.getParameter("xq_s_date");
		String e_date = request.getParameter("xq_e_date");
		String[] categoryId= request.getParameterValues("categoryId");
		String demandStatus = request.getParameter("demandStatus");
		JSONObject resultObj = new JSONObject();
		if(StringUtils.isBlank(s_date)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "开始时间为空");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		if(StringUtils.isBlank(e_date)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "结束时间为空");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		
		Date startDate = DateTools.str2Date(s_date, "yyyy-MM-dd");
		Date endDate = DateTools.str2Date(e_date+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		if(startDate.after(endDate)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "开始时间不能大于结束时间");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		long ii = endDate.getTime()/1000;
		long iii = startDate.getTime()/1000;
		long iiii = 31*24*60*60;
		if(ii-iii>iiii){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "时间间隔不能大于一个月");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		
		if(null==categoryId){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "请选择一个品类");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		
		
		resultObj.put("title", s_date+"至"+e_date+DemandStatus.values()[Integer.parseInt(demandStatus)].getName()+"各品类需求数对比");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime",s_date);
		map.put("endTime", e_date+" 23:59:59");
		map.put("demandStatus", demandStatus);
		SortedMap<String,Integer> resultMap = new TreeMap<String,Integer>();
		
		JSONArray dates = new JSONArray();
		
		for(;startDate.getTime()<=endDate.getTime();){
			resultMap.put(DateTools.date2Str(startDate,"yyyy-MM-dd"),0);
			dates.add(DateTools.date2Str(startDate,"yyyy-MM-dd"));
			startDate.setDate(startDate.getDate()+1);
		}

		
		resultObj.put("dates", dates);
		JSONArray series = new JSONArray();
		
		for(String category:categoryId){
			Category ca = categoryService.findByKey(category);
			map.put("categoryId", category);
			JSONObject obj = new JSONObject();
			obj.put("name", ca.getCategoryName());
			List<CountVo> list = demandService.countByStatusAndCatrgory(map);
			JSONArray caArr = new JSONArray();
			SortedMap<String,Integer> caMap = new TreeMap<>(resultMap);
			for(int i=0;null!=list&&i<list.size();i++){
				caMap.put(list.get(i).getItem(), list.get(i).getCount());
			}
			for(Integer count:caMap.values()){
				caArr.add(count);
			}
			obj.put("data", caArr);
			series.add(obj);	
		}
		
		resultObj.put("series", series);
		
		
		resultObj.put("errCode", "0000");
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}
	
	/**
	 * 状态 按状态分组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/demand_analysis_status")
	public String demanaAnalysisStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String s_date = request.getParameter("xq_s_date");
		String e_date = request.getParameter("xq_e_date");
		String categoryId= request.getParameter("categoryId");
		String[] demandStatus = request.getParameterValues("demandStatus");
		JSONObject resultObj = new JSONObject();
		if(StringUtils.isBlank(s_date)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "开始时间为空");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		if(StringUtils.isBlank(e_date)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "结束时间为空");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		
		Date startDate = DateTools.str2Date(s_date, "yyyy-MM-dd");
		Date endDate = DateTools.str2Date(e_date+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		if(startDate.after(endDate)){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "开始时间不能大于结束时间");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		long ii = endDate.getTime()/1000;
		long iii = startDate.getTime()/1000;
		long iiii = 31*24*60*60;
		if(ii-iii>iiii){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "时间间隔不能大于一个月");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		
		if(null==demandStatus){
			resultObj.put("errCode", "0001");
			resultObj.put("resultMsg", "请选择一个状态");
			ResponseUtils.renderJson(response, resultObj.toJSONString());
			return null;
		}
		
		Category ca = categoryService.findByKey(categoryId);
		resultObj.put("title", s_date+"至"+e_date+"["+ca.getCategoryName()+"]各状态需求数对比");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime",s_date);
		map.put("endTime", e_date+" 23:59:59");
		map.put("categoryId", categoryId);
		
		SortedMap<String,Integer> resultMap = new TreeMap<String,Integer>();
		
		JSONArray dates = new JSONArray();
		
		for(;startDate.getTime()<=endDate.getTime();){
			resultMap.put(DateTools.date2Str(startDate,"yyyy-MM-dd"),0);
			dates.add(DateTools.date2Str(startDate,"yyyy-MM-dd"));
			startDate.setDate(startDate.getDate()+1);
		}

		
		resultObj.put("dates", dates);
		JSONArray series = new JSONArray();
		
		for(String status:demandStatus){
			map.put("demandStatus", status);
			JSONObject obj = new JSONObject();
			obj.put("name", DemandStatus.values()[Integer.parseInt(status)].getName());
			List<CountVo> list = demandService.countByStatusAndCatrgory(map);
			JSONArray caArr = new JSONArray();
			SortedMap<String,Integer> caMap = new TreeMap<>(resultMap);
			for(int i=0;null!=list&&i<list.size();i++){
				caMap.put(list.get(i).getItem(), list.get(i).getCount());
			}
			for(Integer count:caMap.values()){
				caArr.add(count);
			}
			obj.put("data", caArr);
			series.add(obj);	
		}
		
		resultObj.put("series", series);
		resultObj.put("errCode", "0000");
		ResponseUtils.renderJson(response, resultObj.toJSONString());
		return null;
	}
	
	@RequestMapping("/admin/remove")
	@ResponseBody
	public String removeRequirement(String id,HttpServletRequest request){
		try {
			Demand demand = demandService.findByKey(id);
			demand.setDemandStatus("-1");
			demandService.update(demand);
			return "0000";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "0001";
		}
		
	}

}
