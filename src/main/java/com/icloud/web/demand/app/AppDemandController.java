package com.icloud.web.demand.app;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.icloud.common.DateTools;
import com.icloud.common.dto.vo.CountVo;
import com.icloud.common.util.ConfigUtil;
import com.icloud.model.category.Category;
import com.icloud.model.demand.Comments;
import com.icloud.model.demand.Demand;
import com.icloud.model.demand.DemandExt;
import com.icloud.model.demand.DemandLog;
import com.icloud.model.demand.DemandStatus;
import com.icloud.model.demand.Follow;
import com.icloud.model.demand.OperationLog;
import com.icloud.model.demand.TerminationRecord;
import com.icloud.model.user.SkuUser;
import com.icloud.model.user.User;
import com.icloud.model.user.WxUser;
import com.icloud.service.category.CategoryService;
import com.icloud.service.demand.CommentsService;
import com.icloud.service.demand.DemandExtService;
import com.icloud.service.demand.DemandLogService;
import com.icloud.service.demand.DemandService;
import com.icloud.service.demand.FollowService;
import com.icloud.service.demand.OperationLogService;
import com.icloud.service.demand.TerminationRecordService;
import com.icloud.service.redis.RedisService;
import com.icloud.service.sms.MsgRecordService;
import com.icloud.service.user.SkuUserService;
import com.icloud.service.user.UserService;
import com.icloud.service.user.WxUserService;
import com.icloud.web.AppBaseController;

@RestController
public class AppDemandController extends AppBaseController {
	@Autowired
	private DemandService demandService;
	@Autowired
	private UserService userService;
	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private SkuUserService skuUserService;
	@Autowired
	private DemandExtService demandExtService;
	@Autowired
	private FollowService followService;
	@Autowired
	private DemandLogService demandLogService;
	@Autowired
	private CommentsService commentsService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TerminationRecordService terminationRecordService;
	@Autowired
	private OperationLogService operationLogService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private MsgRecordService msgRecordService;

	/**
	 * 获取采购人员需求列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@RequestMapping("/getBuyerRequirementList")
	public String getBuyerRequirementList(HttpServletRequest request) throws NumberFormatException, Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		String status = parameterObj.getString("status");
		String publishTimeStart = parameterObj.getString("publishTimeStart");
		String publishTimeEnd = parameterObj.getString("publishTimeEnd");
		Boolean isOnlyFollow = parameterObj.getBoolean("isOnlyFollow");

		String categoryId = parameterObj.getString("productTypeId");
		String pn = parameterObj.getString("pageNum");
		String ps = parameterObj.getString("pageSize");

		int pageNum = StringUtils.isBlank(pn) ? 1 : Integer.parseInt(pn);
		if (pageNum <= 0) {
			pageNum = 1;
		}
		int pageSize = StringUtils.isBlank(ps) ? 9999 : Integer.parseInt(ps);

		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		
		if (null == u) {
			redisService.remove(sid + "_demand_list");
			redisService.remove(sid + "_demand_sort_list");
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		
		User uu = userService.findByWxUser(u.getId());
		
		Demand demand = new Demand();
		demand.setPublishedUser(uu.getId());
		if (StringUtils.isNotBlank(categoryId)) {
			demand.setCategoryId(categoryId);
		}
		if (StringUtils.isNotBlank(status)) {
			if (!"-1".equals(status)) {
				demand.setDemandStatus(status);
			}
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			demand.setPushlishedStartTime(DateTools.str2Date(publishTimeStart, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			publishTimeEnd = publishTimeEnd + " 23:59:59";
			demand.setPushlishedEndTime(DateTools.str2Date(publishTimeEnd, "yyyy-MM-dd HH:mm:ss"));
		}

		List<Demand> list = null;
		// 第一页要刷新 临时的解决方案 因为每次查询当前条件内的总记录耗时较大 所以使用缓存 在一次翻页周期内使用缓存内数据
		if (pageNum == 1) {
			if (null != isOnlyFollow && isOnlyFollow == true) {
				list = demandService.findFollowListAll(demand);
			} else {
				list = demandService.findList(demand);
			}
			redisService.remove(sid + "_demand_list");
			redisService.remove(sid + "_demand_sort_list");
			redisService.set(sid + "_demand_list", list);
		} else {
			list = (List<Demand>) redisService.get(sid + "_demand_list");
			if (list == null) {
				if (null != isOnlyFollow && isOnlyFollow == true) {
					list = demandService.findFollowListAll(demand);
				} else {
					list = demandService.findList(demand);
				}
				redisService.remove(sid + "_demand_list");
				redisService.remove(sid + "_demand_sort_list");
				redisService.set(sid + "_demand_list", list);
			}
		}
		JSONObject resultData = new JSONObject();
		if (null != list && list.size() > 0) {
			int totalCount = list.size();
			LinkedList<Demand> sortList = null;
			sortList = (LinkedList<Demand>) redisService.get(sid + "_demand_sort_list");
			if (null == sortList) {
				sortList = new LinkedList<Demand>();
				int hasNewCommentCount = 0;
				for (int i = 0; i < totalCount; i++) {
					demand = list.get(i);
					OperationLog log = new OperationLog();
					log.setLogType("0");
					log.setUserId(uu.getId());
					log.setObjectId(demand.getId());
					int count = operationLogService.hasNewLog(log);
					if (count > 0) {
						demand.setNewComment(true);
						sortList.add(hasNewCommentCount, demand);
						hasNewCommentCount++;
					} else {
						demand.setNewComment(false);
						sortList.addLast(demand);
					}
				}
				// 耗时操作，在一个翻页周期内，使用缓存
				redisService.set(sid + "_demand_sort_list", sortList);
			}
			JSONArray array = new JSONArray();
			JSONObject obj = null;

			int startIndex = (pageNum - 1) * pageSize;
			int endIndex = pageNum * pageSize;

			if (endIndex > totalCount) {
				endIndex = totalCount;
			}

			for (int i = startIndex; i < endIndex; i++) {
				demand = sortList.get(i);
				obj = new JSONObject();
				obj.put("rid", demand.getId());
				obj.put("publishTime", DateTools.convertDateToString(demand.getPushlishedTime(), "yyyy-MM-dd"));
				obj.put("productType", demand.getCategory().getCategoryName());
				obj.put("status", demand.getDemandStatus());
				obj.put("description", demand.getDemandDesc());
				if (!demand.getDemandStatus().equals("0")) {
					DemandExt ext = demandExtService.selectByDemand(demand.getId());
					SkuUser skuUser = skuUserService.findByKey(demand.getResponseId());
					obj.put("skuerName", ext.getSkuUserName());
					obj.put("skuerCompany", ext.getSkuCompany());
					obj.put("skuerContact", ext.getSkuUserPhone());
					obj.put("title", ext.getDemandTitle());
					obj.put("skuerAvatar", skuUser.getWxHeadImg());

				}

				obj.put("hasNewComment", demand.isNewComment());

				if (null != isOnlyFollow && isOnlyFollow) {
					obj.put("isFollow", true);
				} else {
					Follow f = new Follow();
					f.setDemandId(demand.getId());
					f.setUserId(uu.getId());
					int fcount = followService.checkIsFollow(f);
					obj.put("isFollow", fcount > 0);
				}
				array.add(obj);
			}
			resultData.put("list", array);
			resultData.put("totalCount", totalCount);
			resultData.put("hasMore", totalCount > endIndex);
		} else {
			resultData.put("totalCount", 0);
			resultData.put("hasMore", false);
		}
		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "列表获取成功");
		return pakageJsonp(resultObj);
	}

	/**
	 * 获取sku经理需求列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@RequestMapping("/getSkuerRequirementList")
	public String getSkuerRequirementList(HttpServletRequest request) throws NumberFormatException, Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		String status = parameterObj.getString("status");
		String publishTimeStart = parameterObj.getString("publishTimeStart");
		String publishTimeEnd = parameterObj.getString("publishTimeEnd");
		Boolean isOnlyFollow = parameterObj.getBoolean("isOnlyFollow");

		String categoryId = parameterObj.getString("productTypeId");
		
		String pn = parameterObj.getString("pageNum");
		String ps = parameterObj.getString("pageSize");

		int pageNum = StringUtils.isBlank(pn) ? 1 : Integer.parseInt(pn);
		if (pageNum <= 0) {
			pageNum = 1;
		}
		int pageSize = StringUtils.isBlank(ps) ? 9999 : Integer.parseInt(ps);
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			redisService.remove(sid + "_demand_response_list");
			redisService.remove(sid + "_demand_sort_response_list");
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		
		Demand demand = new Demand();
		demand.setResponseId(null!=skuUser?skuUser.getId():"--");
		if (StringUtils.isNotBlank(categoryId)) {
			demand.setCategoryId(categoryId);
		}
		if (StringUtils.isNotBlank(status)) {
			if (!"-1".equals(status)) {
				demand.setDemandStatus(status);
			}
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			demand.setPushlishedStartTime(DateTools.str2Date(publishTimeStart, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			publishTimeEnd = publishTimeEnd + " 23:59:59";
			demand.setPushlishedEndTime(DateTools.str2Date(publishTimeEnd, "yyyy-MM-dd HH:mm:ss"));
		}
		List<Demand> list = null;
		// 第一页要刷新 临时的解决方案 因为每次查询当前条件内的总记录耗时较大 所以使用缓存 在一次翻页周期内使用缓存内数据
		if (pageNum == 1) {
			if (null != isOnlyFollow && isOnlyFollow == true) {
				list = demandService.findFollowListAll(demand);
			} else {
				list = demandService.findList(demand);
			}
			redisService.remove(sid + "_demand_response_list");
			redisService.remove(sid + "_demand_sort_response_list");
			redisService.set(sid + "_demand_response_list", list);
		} else {
			list = (List<Demand>) redisService.get(sid + "_demand_list");
			if (list == null) {
				if (null != isOnlyFollow && isOnlyFollow == true) {
					list = demandService.findFollowListAll(demand);
				} else {
					list = demandService.findList(demand);
				}
				redisService.remove(sid + "_demand_response_list");
				redisService.remove(sid + "_demand_sort_response_list");
				redisService.set(sid + "_demand_response_list", list);
			}
		}
		JSONObject resultData = new JSONObject();
		if (null != list && list.size() > 0) {
			int totalCount = list.size();
			LinkedList<Demand> sortList = null;
			sortList = (LinkedList<Demand>) redisService.get(sid + "_demand_sort_response_list");
			if (null == sortList) {
				sortList = new LinkedList<Demand>();
				int hasNewCommentCount = 0;
				for (int i = 0; i < totalCount; i++) {
					demand = list.get(i);
					OperationLog log = new OperationLog();
					log.setLogType("0");
					log.setUserId(skuUser.getId());
					log.setObjectId(demand.getId());
					int count = operationLogService.hasNewLog(log);
					if (count > 0) {
						demand.setNewComment(true);
						sortList.add(hasNewCommentCount, demand);
						hasNewCommentCount++;
					} else {
						demand.setNewComment(false);
						sortList.addLast(demand);
					}
				}
				// 耗时操作，在一个翻页周期内，使用缓存
				redisService.set(sid + "_demand_sort_response_list", sortList);
			}
			JSONArray array = new JSONArray();
			JSONObject obj = null;

			int startIndex = (pageNum - 1) * pageSize;
			int endIndex = pageNum * pageSize;

			if (endIndex > totalCount) {
				endIndex = totalCount;
			}

			for (int i = startIndex; i < endIndex; i++) {
				demand = sortList.get(i);
				obj = new JSONObject();
				obj.put("rid", demand.getId());
				obj.put("publishTime", DateTools.convertDateToString(demand.getPushlishedTime(), "yyyy-MM-dd"));
				obj.put("productType", demand.getCategory().getCategoryName());
				obj.put("status", demand.getDemandStatus());
				obj.put("description", demand.getDemandDesc());
				obj.put("buyerName", demand.getPublishedName());
				User buyUser = userService.findByKey(demand.getPublishedUser());
				obj.put("buyerAvatar", buyUser.getWxHeadImg());

				obj.put("buyerCompany", buyUser.getCompanyName());
				obj.put("buyerContact",demand.getPublishedPhone());
				if (!demand.getDemandStatus().equals("0")) {
					DemandExt ext = demandExtService.selectByDemand(demand.getId());
					obj.put("title", ext.getDemandTitle());
				}

				obj.put("hasNewComment", demand.isNewComment());

				if (null != isOnlyFollow && isOnlyFollow) {
					obj.put("isFollow", true);
				} else {
					Follow f = new Follow();
					f.setDemandId(demand.getId());
					f.setUserId(skuUser.getId());
					int fcount = followService.checkIsFollow(f);
					obj.put("isFollow", fcount > 0);
				}
				array.add(obj);
			}
			resultData.put("list", array);
			resultData.put("totalCount", totalCount);
			resultData.put("hasMore", totalCount > endIndex);
		} else {
			resultData.put("totalCount", 0);
			resultData.put("hasMore", false);
		}
		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "列表获取成功");
		return pakageJsonp(resultObj);
	}

	/**
	 * 获取待响应需求列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@RequestMapping("/getUnResponseList")
	public String getUnResponseList(HttpServletRequest request) throws NumberFormatException, Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		String publishTimeStart = parameterObj.getString("publishTimeStart");
		String publishTimeEnd = parameterObj.getString("publishTimeEnd");
		Boolean isOnlyFollow = parameterObj.getBoolean("isOnlyFollow");

		String categoryId = parameterObj.getString("productTypeId");
		String pageNum = parameterObj.getString("pageNum");
		String pageSize = parameterObj.getString("pageSize");

		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		Demand demand = new Demand();
		demand.setPublishedUser(null!=skuUser?skuUser.getId():"--");//用于关联sku经理的一级品类
		if (StringUtils.isNotBlank(categoryId)) {
			demand.setCategoryId(categoryId);
		}
		demand.setDemandStatus("0");
		if (StringUtils.isNotBlank(publishTimeStart)) {
			demand.setPushlishedStartTime(DateTools.str2Date(publishTimeStart,"yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			publishTimeEnd = publishTimeEnd + " 23:59:59";
			demand.setPushlishedEndTime(DateTools.str2Date(publishTimeEnd, "yyyy-MM-dd HH:mm:ss"));
		}
		PageInfo<Demand> page = null;
			page = demandService.getUnResponseList(StringUtils.isBlank(pageNum) ? 1 : Integer.parseInt(pageNum),
					StringUtils.isBlank(pageSize) ? 9999 : Integer.parseInt(pageSize), demand);
		JSONObject resultData = new JSONObject();
		if (null != page) {
			List<Demand> list = page.getList();
			if (null != list && list.size() > 0) {
				JSONArray array = new JSONArray();
				JSONObject obj = null;
				for (int i = 0; i < list.size(); i++) {
					demand = list.get(i);
					obj = new JSONObject();
					obj.put("rid", demand.getId());
					obj.put("publishTime", DateTools.convertDateToString(demand.getPushlishedTime(), "yyyy-MM-dd"));
					obj.put("productType", demand.getCategory().getCategoryName());
					obj.put("status", demand.getDemandStatus());
					obj.put("description", demand.getDemandDesc());

					User publishUser = userService.findByKey(demand.getPublishedUser());
					obj.put("buyerName", demand.getPublishedName());
					obj.put("buyerCompany", publishUser.getCompanyName());
					obj.put("buyerContact", demand.getPublishedPhone());
					obj.put("buyerAvatar", publishUser.getWxHeadImg());
					if (!demand.getDemandStatus().equals("0")) {
						DemandExt ext = demandExtService.selectByDemand(demand.getId());
						obj.put("title", ext.getDemandTitle());
					}
					if (null != isOnlyFollow && isOnlyFollow) {
						obj.put("isFollow", true);
					} else {
						Follow f = new Follow();
						f.setDemandId(demand.getId());
						f.setUserId(skuUser.getId());
						int fcount = followService.checkIsFollow(f);
						obj.put("isFollow", fcount > 0);
					}
					array.add(obj);
				}
				resultData.put("list", array);
			}
			resultData.put("totalCount", page.getTotal());
			resultData.put("hasMore", page.getPages() > page.getPageNum());
		} else {
			resultData.put("totalCount", 0);
			resultData.put("hasMore", false);
		}
		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "列表获取成功");
		return pakageJsonp(resultObj);

	}

	/**
	 * 获取需求详情
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRequirementDetail")
	public String getRequirementDetail(HttpServletRequest request) throws Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		String rid = parameterObj.getString("rid");
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(rid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数rid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		Demand demand = demandService.findByKey(rid);
		JSONObject resultData = new JSONObject();
		if (null == demand) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "rid无效，不存在需求");
			return pakageJsonp(resultObj);
		}
		JSONObject baseInfo = new JSONObject();
		baseInfo.put("rid", demand.getId());
		baseInfo.put("publishTime", DateTools.convertDateToString(demand.getPushlishedTime(), "yyyy-MM-dd HH:mm:ss"));
		baseInfo.put("status", demand.getDemandStatus());
		baseInfo.put("alertType", -1);
		JSONObject description = new JSONObject();
		description.put("content", demand.getDemandDesc());
		if (StringUtils.isNotBlank(demand.getDemandPics())) {
			description.put("pictures", JSONArray.parseArray(demand.getDemandPics()));
		}
		baseInfo.put("description", description);
		baseInfo.put("productType", demand.getCategory().getCategoryName());

		Follow f = new Follow();
		f.setDemandId(demand.getId());
		
		//根据当前身份查用户
		SkuUser skuUser = null;
		if("0".equals(u.getCurrentType())){
			User user = userService.findByWxUser(u.getId());
			f.setUserId(user.getId());
			if(user.getId().equals(demand.getPublishedUser())) {
				baseInfo.put("relatedRole", "0");
			}
		}else{
			skuUser = skuUserService.findByWxUser(u.getId());
			f.setUserId(skuUser.getId());
		}
		
		int count = followService.checkIsFollow(f);
		baseInfo.put("isFollow", count > 0);
		

		JSONObject extInfo = new JSONObject();
		extInfo.put("buyerName", demand.getPublishedName());
		User buyer = userService.findByKey(demand.getPublishedUser());
		extInfo.put("buyerCompany", buyer.getCompanyName());
		extInfo.put("buyerContact", demand.getPublishedPhone());
		extInfo.put("productName", demand.getProductName());
		if (!demand.getDemandStatus().equals("0")) {
			baseInfo.put("responseTime",
					DateTools.convertDateToString(demand.getResponseTime(), "yyyy-MM-dd HH:mm:ss"));
			if (null!=skuUser&&demand.getResponseId().equals(skuUser.getId())) {
				baseInfo.put("relatedRole", "1");
			}
			DemandExt ext = demandExtService.selectByDemand(demand.getId());
			if (null != ext) {
				extInfo.put("title", ext.getDemandTitle());
				extInfo.put("skuerName", ext.getSkuUserName());
				extInfo.put("skuerCompany", ext.getSkuCompany());
				extInfo.put("skuerContact", ext.getSkuUserPhone());
				extInfo.put("amount", ext.getProductCount());
				extInfo.put("totalPrice", ext.getTotalPrice());
				extInfo.put("unitPrice", ext.getProductPrice());
				extInfo.put("expressCompany",ext.getExpressCompany());
				extInfo.put("expressCode", ext.getExpressCode());
				extInfo.put("unit", ext.getUnit());
				extInfo.put("format", ext.getFormat());
				if (null != ext.getDeliveryDeadLine()) {
					extInfo.put("deliveryDeadline",
							DateTools.convertDateToString(ext.getDeliveryDeadLine(), "yyyy-MM-dd"));
					if (System.currentTimeMillis()/1000
							- ext.getDeliveryDeadLine().getTime()/1000 < Integer.parseInt(ConfigUtil.get("deliveryAlert"))
									* 24 * 60 * 60) {
						baseInfo.put("alertType", 0);
					}

				}
				if (null != ext.getPaymentTime()) {
					extInfo.put("paymentDeadline", DateTools.convertDateToString(ext.getPaymentTime(), "yyyy-MM-dd"));
					if (System.currentTimeMillis()/1000
							- ext.getPaymentTime().getTime()/1000 < Integer.parseInt(ConfigUtil.get("paymentAlert"))
									* 24 * 60 * 60) {
						baseInfo.put("alertType", 1);
					}
				}
			}
		}

		if (!baseInfo.containsKey("relatedRole")) {
			baseInfo.put("relatedRole", "-1");
		}
		resultData.put("baseInfo", baseInfo);
		resultData.put("extInfo", extInfo);
		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "需求详情获取成功");
		return pakageJsonp(resultObj);
	}

	/**
	 * 发布需求
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/publishRequirement")
	public String publishRequirement(HttpServletRequest request) throws Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		String productTypeId = parameterObj.getString("productTypeId");
		String description = parameterObj.getString("description");
		String publisherName = parameterObj.getString("publisherName");
		String publisherContact = parameterObj.getString("publisherContact");
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(productTypeId)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数productTypeId");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(description)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数description");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(publisherName)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数publisherName");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(publisherContact)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数publisherContact");
			return pakageJsonp(resultObj);
		}
		JSONObject descriptionObj = JSONObject.parseObject(description);
		if (null == descriptionObj) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数description");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		User user = userService.findByWxUser(u.getId());
		Demand demand = new Demand();
		demand.setCategoryId(productTypeId);
		demand.setPublishedUser(user.getId());
		demand.setPublishedName(publisherName);
		demand.setPublishedPhone(publisherContact);
		demand.setPushlishedTime(new Date());
		demand.setDemandDesc(descriptionObj.getString("content"));
		demand.setDemandPics(descriptionObj.getString("pictures"));
		demand.setModifyTime(new Date());
		demand.setDemandStatus(DemandStatus.newPublish.ordinal() + "");
		demand.setTerminationStatus(DemandStatus.newPublish.ordinal() + "");
		demandService.save(demand);

		DemandExt ext = new DemandExt();
		ext.setDemandId(demand.getId());
		demandExtService.save(ext);

		DemandLog log = new DemandLog();
		log.setCreateTime(demand.getPushlishedTime());
		log.setDemandId(demand.getId());
		log.setDemandStatus(DemandStatus.newPublish.ordinal() + "");
		log.setLogUser(demand.getPublishedName());
		log.setLogInfo("需求发布，发布人：" + demand.getPublishedName());
		demandLogService.save(log);

		JSONObject resultData = new JSONObject();
		resultData.put("rid", demand.getId());
		resultObj.put("resultData", resultData);

		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "需求发布成功");
		return pakageJsonp(resultObj);

	}

	/**
	 * 添加评论
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addComment")
	public String addComment(HttpServletRequest request) throws Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		String rid = parameterObj.getString("rid");
		String content = parameterObj.getString("content");
		String pictures = parameterObj.getString("pictures");

		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(rid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数rid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(content)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数content");
			return pakageJsonp(resultObj);
		}

		WxUser u = wxUserService.getUserFromSession(sid);
		
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		
		Demand demand = demandService.findByKey(rid);
		Comments comment = new Comments();
		
		if("0".equals(u.getCurrentType())){
			 User user = userService.findByWxUser(u.getId());
			 comment.setNick(demand.getPublishedName());
			 comment.setUserId(user.getId());
		}else{
			comment.setNick(StringUtils.isNotBlank(skuUser.getSkuName())?skuUser.getSkuName():u.getNick());
			comment.setUserId(skuUser.getId());
		}

		
		comment.setContent(content);
		comment.setPictures(pictures);
		comment.setCreateTime(new Date());
		comment.setHeadImg(u.getWxHeadImg());
		comment.setDemandId(rid);
		commentsService.save(comment);

		demand = new Demand();
		demand.setId(rid);
		demand.setModifyTime(new Date());
		demandService.update(demand);
		JSONObject resultData = new JSONObject();
		resultData.put("commentId", comment.getId());
		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "评论添加成功");
		return pakageJsonp(resultObj);
	}

	/**
	 * 获取评论列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCommentList")
	public String getCommentList(HttpServletRequest request) {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String rid = parameterObj.getString("rid");
		String sid = parameterObj.getString("sid");
		if (StringUtils.isBlank(rid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数rid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		
		User user = userService.findByWxUser(u.getId());
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		
		
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		PageInfo<Comments> page = commentsService.findByDemand(
				StringUtils.isBlank(pageNum) ? 1 : Integer.parseInt(pageNum),
				StringUtils.isBlank(pageSize) ? 10 : Integer.parseInt(pageSize), rid);
		List<Comments> list = page.getList();
		JSONObject resultData = new JSONObject();
		if (null != list && list.size() > 0) {
			JSONArray array = new JSONArray();
			for (Comments c : list) {
				JSONObject cc = new JSONObject();
				cc.put("cid", c.getId());
				cc.put("publishTime", DateTools.convertDateToString(c.getCreateTime(), "yyyy-MM-dd HH:mm"));
				
				if("0".equals(u.getCurrentType())){
					cc.put("publisherName", c.getUserId().equals(user.getId()) ? "我" : c.getNick());
				}else{
					cc.put("publisherName", c.getUserId().equals(skuUser.getId()) ? "我" : c.getNick());
				}
				
				
				cc.put("publisherAvatar", c.getHeadImg());
				cc.put("content", c.getContent());

				if (StringUtils.isNotBlank(c.getPictures())) {
					cc.put("pictures", JSONArray.parseArray(c.getPictures()));
				}
				array.add(cc);
			}
			resultData.put("list", array);
		}
		resultData.put("totalCount", page.getTotal());
		resultData.put("hasMore", page.getLastPage() > 1);
		resultData.put("pageNum", page.getPageNum());
		resultData.put("pageSize", page.getPageSize());

		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "获取评论列表成功");
		return pakageJsonp(resultObj);

	}

	/**
	 * 抢单
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/responseRequirement")
	public String responseRequirement(HttpServletRequest request) throws Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String rid = parameterObj.getString("rid");
		String sid = parameterObj.getString("sid");
		if (StringUtils.isBlank(rid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数rid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		
		if (skuUser.getStatus().equals("0")) {
			resultObj.put("errCode", "0013");
			resultObj.put("resultMsg", "sku经理尚未认证֤");
			return pakageJsonp(resultObj);
		}
		Demand demand = demandService.findByKey(rid);
		if (null == demand) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "rid无效，需求为空");
			return pakageJsonp(resultObj);
		}
		if (!demand.getDemandStatus().equals("0")) {
			resultObj.put("errCode", "5000");
			resultObj.put("resultMsg", "该需求已被抢");
			return pakageJsonp(resultObj);
		}
		List<Category> list = categoryService.selectSkuCategory(skuUser.getId());
		boolean categoryMatch = false;
		for (int i = 0; list != null && i < list.size(); i++) {
			if (list.get(i).getId().equals(demand.getCategoryId())) {
				categoryMatch = true;
				break;
			}
		}
		if (!categoryMatch) {
			resultObj.put("errCode", "5001");
			resultObj.put("resultMsg", "sku尚未关注该品类");
			return pakageJsonp(resultObj);
		}
		demand.setDemandStatus("1");
		demand.setResponseId(skuUser.getId());
		demand.setResponseTime(new Date());
		demand.setModifyTime(new Date());
		demandService.update(demand);

		DemandExt ext = demandExtService.selectByDemand(demand.getId());
		ext.setSkuCompany(skuUser.getSkuCompanyName());
		ext.setSkuUserId(skuUser.getId());
		ext.setSkuUserPhone(skuUser.getSkuPhone());
		ext.setSkuUserName(skuUser.getSkuName());

		demandExtService.update(ext);

		DemandLog log = new DemandLog();
		log.setCreateTime(new Date());
		log.setDemandId(demand.getId());
		log.setDemandStatus("1");
		log.setLogUser(skuUser.getSkuName());
		log.setLogInfo("需求响应，响应人：" + skuUser.getSkuName());
		demandLogService.save(log);
		
		msgRecordService.saveAnRecord(demand);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "抢单成功");
		return pakageJsonp(resultObj);
	}

	/**
	 * 终止需求ֹ
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/terminateRequirement")
	public String terminateRequirement(HttpServletRequest request) throws Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String rid = parameterObj.getString("rid");
		String sid = parameterObj.getString("sid");
		String reason = parameterObj.getString("reason");
		if (StringUtils.isBlank(rid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数rid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		
		
		User user = userService.findByWxUser(u.getId());
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		
		Demand demand = demandService.findByKey(rid);
		if (null == demand) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "rid无效，需求不存在");
			return pakageJsonp(resultObj);
		}

		TerminationRecord record = new TerminationRecord();
		record.setApplyReson(reason);
		record.setApplyUser((u.getCurrentType().equals("0") ? user.getId() : skuUser.getId()));
		record.setCreateTime(new Date());
		record.setDemandId(demand.getId());
		record.setModifyTime(new Date());
		record.setStatus("0");
		record.setApplyRole(u.getCurrentType());
		terminationRecordService.save(record);

		demand.setTerminationStatus(demand.getDemandStatus());// 需求终止前的状态记录，用于回滚操作״̬
		demand.setDemandStatus(DemandStatus.terminationing.ordinal() + "");// 需求终止申请
		demand.setModifyTime(new Date());

		demandService.update(demand);

		DemandLog log = new DemandLog();
		log.setCreateTime(new Date());
		log.setDemandId(demand.getId());
		log.setDemandStatus(DemandStatus.terminationing.ordinal() + "");
		log.setLogUser((u.getCurrentType().equals("0") ? user.getName() : skuUser.getSkuName()));
		log.setLogInfo("需求终止申请，申请人：" + (u.getCurrentType().equals("0") ? user.getName() : skuUser.getSkuName()));
		demandLogService.save(log);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "申请成功，等待审核");
		return pakageJsonp(resultObj);
	}

	/**
	 * 需求状态变更留痕
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/recordRequirement")
	public String recordRequirement(HttpServletRequest request) throws Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		String rid = parameterObj.getString("rid");
		String toStatus = parameterObj.getString("toStatus");
		String productName = parameterObj.getString("productName");
		String unitPrice = parameterObj.getString("unitPrice");
		String amount = parameterObj.getString("amount");
		String deliverDeadline = parameterObj.getString("deliveryDeadline");
		String paymentDeadline = parameterObj.getString("paymentDeadline");
		String productPicutres = parameterObj.getString("productPicutres");
		String contractPictures = parameterObj.getString("contractPictures");
		String expressCompany = parameterObj.getString("expressCompany");
		String expressCode = parameterObj.getString("expressCode");
		String expressPictures = parameterObj.getString("expressPictures");
		String paymentPictures = parameterObj.getString("paymentPictures");
        String arrivalPictures = parameterObj.getString("arrivalPictures");
        String unit = parameterObj.getString("unit");
        String format = parameterObj.getString("format");
        JSONObject resultObj = new JSONObject();
		if (StringUtils.isBlank(rid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数rid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效");
			return pakageJsonp(resultObj);
		}
		Demand demand = demandService.findByKey(rid);
		if (null == demand) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "需求不存在");
			return pakageJsonp(resultObj);
		}
		DemandExt ext = demandExtService.selectByDemand(rid);
		DemandLog log = new DemandLog();
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(toStatus)) {
			demand.setDemandStatus(toStatus);
			demand.setTerminationStatus(toStatus);
			sb.append("需求状态更改：" + DemandStatus.values()[Integer.parseInt(toStatus)].getName() + "；");
			log.setDemandStatus(toStatus);
		}
		if (StringUtils.isNotBlank(productName)) {
			sb.append("修改产品名称：" + productName + "；");
			demand.setProductName(productName);
		}
		if (StringUtils.isNotBlank(unitPrice)) {
			ext.setProductPrice(unitPrice);
			sb.append("修改产品单价：" + unitPrice + "；");
		}
		if (StringUtils.isNotBlank(amount)) {
			ext.setProductCount(Integer.parseInt(amount));
			sb.append("修改产品总数：" + amount + "；");
		}
		if (StringUtils.isNotBlank(deliverDeadline)) {
			ext.setDeliveryDeadLine(DateTools.str2Date(deliverDeadline, "yyyy-MM-dd"));
			sb.append("修改发货截止时间：" + deliverDeadline + "；");
		}
		if (StringUtils.isNotBlank(paymentDeadline)) {
			ext.setPaymentTime(DateTools.str2Date(paymentDeadline, "yyyy-MM-dd"));
			sb.append("修改付款截止时间：" + paymentDeadline + "；");
		}
		if (StringUtils.isNotBlank(productPicutres)) {
			ext.setProductPics(productPicutres);
			sb.append("添加产品图片；");
		}
		if (StringUtils.isNotBlank(contractPictures)) {
			ext.setContractPics(contractPictures);
			sb.append("添加合用图片；");
		}
		if (StringUtils.isNotBlank(expressCompany)) {
			ext.setExpressCompany(expressCompany);
			sb.append("添加物流公司：" + expressCompany + "；");
		}
		if (StringUtils.isNotBlank(expressCode)) {
			ext.setExpressCode(expressCode);
			sb.append("添加物流单号：" + expressCode + "；");
		}
		if (StringUtils.isNotBlank(expressPictures)) {
			ext.setExpressPics(expressPictures);
			sb.append("添加物流图片；");
		}
		if (StringUtils.isNotBlank(paymentPictures)) {
			ext.setPaymentPics(paymentPictures);
			sb.append("添加付款凭证；");
		}
		if(StringUtils.isNotBlank(arrivalPictures)){
			ext.setArrivalPictures(arrivalPictures);
			sb.append("添加到货凭证图片；");
		}
		if(StringUtils.isNotBlank(unit)){
			ext.setUnit(unit);
			sb.append("采购单位："+unit+"；");
		}
		if(StringUtils.isNotBlank(format)){
			ext.setFormat(format);
			sb.append("包装规格："+format+"；");
		}
		
		log.setDemandId(rid);
		log.setCreateTime(new Date());
		log.setLogInfo(sb.toString());
		if(u.getCurrentType().equals("0")){
			log.setLogUser(demand.getPublishedName());
		}else{
			SkuUser skuUser = skuUserService.findByWxUser(u.getId());
			log.setLogUser(skuUser.getSkuName());
		}
         
		demand.setModifyTime(new Date());
		demandService.update(demand);

		demandExtService.update(ext);
		demandLogService.save(log);

		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "处理成功");
		return pakageJsonp(resultObj);
	}

	/**
	 * 日志
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/userLog")
	public String userLog(HttpServletRequest request) {
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		String logType = parameterObj.getString("logType");
		String objectId = parameterObj.getString("objectId");
		String extInfo = parameterObj.getString("extInfo");
		JSONObject resultObj = new JSONObject();
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		
		
		OperationLog log = new OperationLog();
		log.setCreateTime(new Date());
		log.setObjectId(objectId);
		if(u.getCurrentType().equals("0")){
			User user = userService.findByWxUser(u.getId());
		    log.setUserId(user.getId());
		}else{
			SkuUser skuUser = skuUserService.findByWxUser(u.getId());
			log.setUserId(skuUser.getId());
		}
		log.setLogType(logType);
		log.setExtInfo(extInfo);
		operationLogService.save(log);

		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "日志保存成功");
		return pakageJsonp(resultObj);
	}

	/**
	 * 关注/取消关注
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toggleFollow")
	public String toggleFollow(HttpServletRequest request) throws Exception {
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		String rid = parameterObj.getString("rid");
		String type = parameterObj.getString("type");
		JSONObject resultObj = new JSONObject();
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(rid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数rid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(type)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数type");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		Follow f = new Follow();
		f.setDemandId(rid);
		if(u.getCurrentType().equals("0")){
			User user = userService.findByWxUser(u.getId());
		    f.setUserId(user.getId());
		}else{
			SkuUser skuUser = skuUserService.findByWxUser(u.getId());
			f.setUserId(skuUser.getId());
		}
		if ("0".equals(type)) {
			followService.unFollow(f);
			resultObj.put("errCode", "0000");
			resultObj.put("resultMsg", "取消关注");
			return pakageJsonp(resultObj);
		} else {
			followService.unFollow(f);
			followService.save(f);
			resultObj.put("errCode", "0000");
			resultObj.put("resultMsg", "关注");
			return pakageJsonp(resultObj);
		}

	}
	
	@RequestMapping("/removeRequirement")
	public String removeRequirement(HttpServletRequest request) throws Exception{
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		String rid = parameterObj.getString("rid");
		JSONObject resultObj = new JSONObject();
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(rid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数rid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		Demand demand = demandService.findByKey(rid);
		if(null==demand){
			resultObj.put("errCode", "0000");
			resultObj.put("resultMsg", "删除成功");
			return pakageJsonp(resultObj);
		}
		
		demand.setDemandStatus("-1");
		demandService.update(demand);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "删除成功");
		return pakageJsonp(resultObj);
	}
	/**
	 * 需求统计
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRequirementCountData")
	public String getRequirementCountData(HttpServletRequest request) throws Exception{
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		String type = parameterObj.getString("type");
		JSONObject resultObj = new JSONObject();
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		if (StringUtils.isBlank(type)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数type");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		
		String currentRole = u.getCurrentType();
		
		if("0".equals(currentRole)){
			//采购人员
			User user = userService.findByWxUser(u.getId());
			Demand demand = new Demand();
			demand.setPublishedUser(user.getId());
			Integer totalCount = demandService.findCount(demand);
			if("0".equals(type)){
				List<CountVo> list = demandService.countByCategory(demand);
				List<Category> categorys = categoryService.findList(new Category());
				JSONArray array = new JSONArray();
				for(Category ca:categorys){
					JSONObject dataItem = new JSONObject();
					dataItem.put("name", ca.getCategoryName());
					dataItem.put("data",0);
					for(int i=0;null!=list&&i<list.size();i++){
						  CountVo vo = list.get(i);
						  if(vo.getItem().equals(ca.getId())){
							  dataItem.put("data", vo.getCount());
							  break;
						  }
					}
					array.add(dataItem);
				}
				
				JSONObject resultData = new JSONObject();
				resultData.put("totalCount", totalCount);
				resultData.put("dataList", array);
				resultObj.put("resultData", resultData);
				resultObj.put("errCode", "0000");
				resultObj.put("resultMsg", "统计成功");
				return pakageJsonp(resultObj);
			}else{
				List<CountVo> list = demandService.countByStatus(demand);
				JSONArray array = new JSONArray();
				for(DemandStatus status:DemandStatus.values()){
					JSONObject dataItem = new JSONObject();
					dataItem.put("name", status.getName());
					dataItem.put("data",0);
					for(int i=0;null!=list&&i<list.size();i++){
						  CountVo vo = list.get(i);
						  if(vo.getItem().equals(status.ordinal()+"")){
							  dataItem.put("data", vo.getCount());
							  break;
						  }
					}
					array.add(dataItem);
				}
				JSONObject resultData = new JSONObject();
				resultData.put("totalCount", totalCount);
				resultData.put("dataList", array);
				resultObj.put("resultData", resultData);
				resultObj.put("errCode", "0000");
				resultObj.put("resultMsg", "统计成功");
				return pakageJsonp(resultObj);
			}
		}else{
			//sku经理
			SkuUser skuUser = skuUserService.findByWxUser(u.getId());
			Demand demand = new Demand();
			demand.setResponseId(skuUser.getId());
			Integer totalCount = demandService.findCount(demand);
			if("0".equals(type)){
				List<CountVo> list = demandService.countByCategory(demand);
				List<Category> categorys = categoryService.findList(new Category());
				JSONArray array = new JSONArray();
				for(Category ca:categorys){
					JSONObject dataItem = new JSONObject();
					dataItem.put("name", ca.getCategoryName());
					dataItem.put("data",0);
					for(int i=0;null!=list&&i<list.size();i++){
						  CountVo vo = list.get(i);
						  if(vo.getItem().equals(ca.getId())){
							  dataItem.put("data", vo.getCount());
							  break;
						  }
					}
					array.add(dataItem);
				}
				
				JSONObject resultData = new JSONObject();
				resultData.put("totalCount", totalCount);
				resultData.put("dataList", array);
				resultObj.put("resultData", resultData);
				resultObj.put("errCode", "0000");
				resultObj.put("resultMsg", "统计成功");
				return pakageJsonp(resultObj);
			}else{
				List<CountVo> list = demandService.countByStatus(demand);
				JSONArray array = new JSONArray();
				for(DemandStatus status:DemandStatus.values()){
					JSONObject dataItem = new JSONObject();
					dataItem.put("name", status.getName());
					dataItem.put("data",0);
					for(int i=0;null!=list&&i<list.size();i++){
						  CountVo vo = list.get(i);
						  if(vo.getItem().equals(status.ordinal()+"")){
							  dataItem.put("data", vo.getCount());
							  break;
						  }
					}
					array.add(dataItem);
				}
				JSONObject resultData = new JSONObject();
				resultData.put("totalCount", totalCount);
				resultData.put("dataList", array);
				resultObj.put("resultData", resultData);
				resultObj.put("errCode", "0000");
				resultObj.put("resultMsg", "统计成功");
				return pakageJsonp(resultObj);
			}
			
		}
	}
	@RequestMapping("/getResponseTimeData")
	public String getResponseTimeData(HttpServletRequest request){
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		JSONObject resultObj = new JSONObject();
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		String currentRole = u.getCurrentType();
		
		if("0".equals(currentRole)){
			User user = userService.findByWxUser(u.getId());
			JSONArray array = new JSONArray();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("publishedUser", user.getId());
			//1 6小时内
			map.put("time",0);
			map.put("gtTime", 6*60*60);
			Integer sixH_count = demandService.responseTimeData(map);
			JSONObject sixH = new JSONObject();
			sixH.put("name","6小时内");
			sixH.put("data", sixH_count);
			array.add(sixH);
			//2 12小时内
			map.put("time",6*60*60 );
			map.put("gtTime",12*60*60 );
		    Integer twelveH_count = demandService.responseTimeData(map);
		    JSONObject twelveH = new JSONObject();
		    twelveH.put("name", "12小时内");
		    twelveH.put("data", twelveH_count);
		    array.add(twelveH);
		     
		     //3 一天内
		     map.put("time",12*60*60);
		     map.put("gtTime", 24*60*60);
		     Integer day_count = demandService.responseTimeData(map);
		     JSONObject day = new JSONObject();
		     day.put("name","1天内");
		     day.put("data", day_count);
		     array.add(day);
		     
		     //4 大于一天
		     map.put("time", 24*60*60);
		     map.remove("gtTime");
		     Integer gtDay_count = demandService.responseTimeData(map);
		     JSONObject getDay = new JSONObject();
		     getDay.put("name","大于一天");
		     getDay.put("data", gtDay_count);
		     array.add(getDay);
		     
		     JSONObject resultData = new JSONObject();
		     Demand demand = new Demand();
		     demand.setPublishedUser(user.getId());
		     Long timee =demandService.averageTime(demand);
		     Integer count = demandService.countResponse(demand);
		     if(count==0){
		    	 resultData.put("averageTime", 0);
		     }else{
		    	 int average = (int) (timee/count);
		    	 resultData.put("averageTime", (int)(average/60));
		     }
		     resultData.put("dataList", array);
		     resultObj.put("resultData", resultData);
		     resultObj.put("errCode", "0000");
		     resultObj.put("resultMsg", "统计成功");
		     
		     return pakageJsonp(resultObj);
		}else{
			SkuUser skuUser = skuUserService.findByWxUser(u.getId());
			JSONArray array = new JSONArray();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("responseId", skuUser.getId());
			//1 6小时内
			map.put("time",0);
			map.put("gtTime", 6*60*60);
			Integer sixH_count = demandService.responseTimeData(map);
			JSONObject sixH = new JSONObject();
			sixH.put("name","6小时内");
			sixH.put("data", sixH_count);
			array.add(sixH);
			//2 12小时内
			map.put("time",6*60*60 );
			map.put("gtTime",12*60*60 );
		    Integer twelveH_count = demandService.responseTimeData(map);
		    JSONObject twelveH = new JSONObject();
		    twelveH.put("name", "12小时内");
		    twelveH.put("data", twelveH_count);
		    array.add(twelveH);
		     
		     //3 一天内
		     map.put("time",12*60*60);
		     map.put("gtTime", 24*60*60);
		     Integer day_count = demandService.responseTimeData(map);
		     JSONObject day = new JSONObject();
		     day.put("name","1天内");
		     day.put("data", day_count);
		     array.add(day);
		     
		     //4 大于一天
		     map.put("time", 24*60*60);
		     map.remove("gtTime");
		     Integer gtDay_count = demandService.responseTimeData(map);
		     JSONObject getDay = new JSONObject();
		     getDay.put("name","大于一天");
		     getDay.put("data", gtDay_count);
		     array.add(getDay);
		     
		     JSONObject resultData = new JSONObject();
		     Demand demand = new Demand();
		     demand.setResponseId(skuUser.getId());
		     Long timee =demandService.averageTime(demand);
		     Integer count = demandService.countResponse(demand);
		     if(count==0){
		    	 resultData.put("averageTime", 0);
		     }else{
		    	 int average = (int) (timee/count);
		    	 resultData.put("averageTime", (int)(average/60));
		     }
		     resultData.put("dataList", array);
		     resultObj.put("resultData", resultData);
		     resultObj.put("errCode", "0000");
		     resultObj.put("resultMsg", "统计成功");
		     return pakageJsonp(resultObj);
		}
	}
	@RequestMapping("/getTopPartner")
	public String getTopPartner(HttpServletRequest request) throws Exception{
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		JSONObject resultObj = new JSONObject();
		if (StringUtils.isBlank(sid)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if (null == u) {
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效，用户为空");
			return pakageJsonp(resultObj);
		}
		String currentRole = u.getCurrentType();
		if("0".equals(currentRole)){
			User user = userService.findByWxUser(u.getId());
			JSONArray array = new JSONArray();
			Demand demand = new Demand();
			demand.setPublishedUser(user.getId());
			List<CountVo> list = demandService.selectRank(demand);
			for(int i=0;list!=null&&i<list.size();i++){
				CountVo vo = list.get(i);
				String skuId = vo.getItem();
				SkuUser skuUser = skuUserService.findByKey(skuId);
				JSONObject skuObj = new JSONObject();
				skuObj.put("avatar",skuUser.getWxHeadImg());
				skuObj.put("name", skuUser.getSkuName());
				skuObj.put("company", skuUser.getSkuCompanyName());
				skuObj.put("phone", skuUser.getSkuPhone());
				skuObj.put("requirementCount", vo.getCount());
				array.add(skuObj);
			} 
			JSONObject resultData = new JSONObject();
			 resultData.put("list", array);
		     resultObj.put("resultData", resultData);
		     resultObj.put("errCode", "0000");
		     resultObj.put("resultMsg", "统计成功");
		}else{
			SkuUser skuUser = skuUserService.findByWxUser(u.getId());
			JSONArray array = new JSONArray();
			Demand demand = new Demand();
			demand.setResponseId(skuUser.getId());
			List<CountVo> list = demandService.selectRank(demand);
			for(int i=0;list!=null&&i<list.size();i++){
				CountVo vo = list.get(i);
				String userId = vo.getItem();
				User user = userService.findByKey(userId);
				JSONObject uObj = new JSONObject();
				uObj.put("avatar", user.getWxHeadImg());
				uObj.put("name", user.getName());
				uObj.put("company", user.getCompanyName());
				uObj.put("phone", user.getPhone());
				uObj.put("requirementCount", vo.getCount());
				array.add(uObj);
			} 
			JSONObject resultData = new JSONObject();
			 resultData.put("list", array);
		     resultObj.put("resultData", resultData);
		     resultObj.put("errCode", "0000");
		     resultObj.put("resultMsg", "统计成功");
		}
		return pakageJsonp(resultObj);
	}

}
