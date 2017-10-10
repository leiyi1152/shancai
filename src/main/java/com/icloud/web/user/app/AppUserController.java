package com.icloud.web.user.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icloud.model.category.Category;
import com.icloud.model.category.SkuCategory;
import com.icloud.model.user.SkuImport;
import com.icloud.model.user.SkuUser;
import com.icloud.model.user.User;
import com.icloud.model.user.WxUser;
import com.icloud.service.category.CategoryService;
import com.icloud.service.redis.RedisService;
import com.icloud.service.sms.SmsService;
import com.icloud.service.user.SkuImportService;
import com.icloud.service.user.SkuUserService;
import com.icloud.service.user.UserService;
import com.icloud.service.user.WxUserService;
import com.icloud.web.AppBaseController;

@RestController
public class AppUserController extends AppBaseController{
	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SkuImportService skuImportService;
	@Autowired
	private UserService userService;
	@Autowired
	private SkuUserService skuUserService;
	@Autowired
	private SmsService smsService;
	
	/** 采购用户进行实行认证 
	 * @throws Exception **/
	@RequestMapping("/addBuyerAuthorized")
	public String addBuyerAuthorized(HttpServletRequest request) throws Exception{
		
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		String name = parameterObj.getString("name");
		String phone = parameterObj.getString("phone");
		String code = parameterObj.getString("verificationCode");
		String company = parameterObj.getString("company");
		JSONObject resultObj = new JSONObject();
		if(StringUtils.isBlank(sid)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(name)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数name");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(phone)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数phone");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(code)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数verificationCode");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(company)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数company");
			return pakageJsonp(resultObj);
		}
		
		String reidsCode = (String) redisService.get(phone);
		if(!code.equals(reidsCode)){
			resultObj.put("errCode", "0014");
			resultObj.put("resultMsg", "验证码错误");
			return pakageJsonp(resultObj);
		}
		redisService.remove(phone);
		WxUser wu = wxUserService.getUserFromSession(sid);
		if(wu==null){
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "用户为空，sid无效");
			return pakageJsonp(resultObj);
		}
		User uu = userService.findByWxUser(wu.getId());
		User phone_u = userService.findByPhone(phone);
		
		if(uu!=null){
			if(phone_u!=null&&!uu.getId().equals(phone_u.getId())){
				resultObj.put("errCode", "0015");
				resultObj.put("resultMsg", "当前手机号已被使用");
				return pakageJsonp(resultObj);
			}
		}else{
			if(null!=phone_u){
				resultObj.put("errCode", "0015");
				resultObj.put("resultMsg", "当前手机号已被使用");
				return pakageJsonp(resultObj);
			}
			uu = new User();
		}
		uu.setCompanyName(company);
		uu.setName(name);
		uu.setPhone(phone);
		uu.setStatus("1");
		uu.setCreateTime(new Date());
		
		if(StringUtils.isNotBlank(uu.getId())){
		    userService.update(uu);
		}else{
			uu.setCreateTime(new Date());
			userService.save(uu);
		}
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "验证成功");
 		return pakageJsonp(resultObj);
		
	}

	/**
	 * 获取采购人员认证信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBuyerAuthorizedInfo")
	public String getBuyerAuthorizedInfo(HttpServletRequest request){
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		if(StringUtils.isBlank(sid)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if(u==null){
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "用户为空，sid无效");
			return pakageJsonp(resultObj);
		}
		User uu = userService.findByWxUser(u.getId());
		JSONObject resultData = new JSONObject();
		if(null!=uu&&uu.getStatus().equals("1")){
			resultData.put("isAuthorized", true);
			resultData.put("name", uu.getName());
			resultData.put("phone", uu.getPhone());
			resultData.put("company", uu.getCompanyName());
		}else{
			resultData.put("isAuthorized", false);
		}
		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "获取采购人员信息成功");
		return pakageJsonp(resultObj);
	}
	/**
	 * 获取SKU 经理认证信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSkuerAuthorizedInfo")
	public String getSkuerAuthorizedInfo(HttpServletRequest request){
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		if(StringUtils.isBlank(sid)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if(u==null){
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "用户为空，sid无效");
			return pakageJsonp(resultObj);
		}
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		JSONObject resultData = new JSONObject();
		if(null!=skuUser&&skuUser.getStatus().equals("1")){
			resultData.put("isAuthorized", true);
			resultData.put("name", skuUser.getSkuName());
			resultData.put("phone", skuUser.getSkuPhone());
			resultData.put("company", skuUser.getSkuCompanyName());
		}else{
			resultData.put("isAuthorized", false);
			resultObj.put("resultData", resultData);
			resultObj.put("errCode", "0000");
			resultObj.put("resultMsg", "成功获取sku经理信息");
			return pakageJsonp(resultObj);
		}
		List<Category> cagegoryList = categoryService.selectSkuCategory(skuUser.getId());
		if(null!=cagegoryList&&cagegoryList.size()>0){
			JSONArray array = new JSONArray();
			for(Category c:cagegoryList){
				JSONObject ca = new JSONObject();
				ca.put("id", c.getId());
				ca.put("name", c.getCategoryName());
				array.add(ca);
			}
		  resultData.put("relatedProductType", array);	
		}
		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "成功获取sku经理信息");
		return pakageJsonp(resultObj);
	}
	/**
	 * sku经理认证
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addSkuerAuthorized")
	public String addSkuerAuthorized(HttpServletRequest request) throws Exception{
		JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String sid = parameterObj.getString("sid");
		String name = parameterObj.getString("name");
		String phone = parameterObj.getString("phone");
		String code = parameterObj.getString("verificationCode");
		String company = parameterObj.getString("company");
		String relatedProductTypeId = parameterObj.getString("relatedProductTypeId");
		if(StringUtils.isBlank(sid)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数sid");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(name)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数name");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(phone)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数phone");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(code)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数verificationCode");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(company)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "缺少参数company");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(relatedProductTypeId)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "sku经理至少关注一个一级品类");
			return pakageJsonp(resultObj);
		}

		JSONArray array = JSONArray.parseArray(relatedProductTypeId);
		if(array.size()==0){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "sku经理至少关注一个一级品类");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if(u==null){
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "用户为空，sid无效");
			return pakageJsonp(resultObj);
		}
		String reidsCode = (String) redisService.get(phone);
		if(!code.equals(reidsCode)){
			resultObj.put("errCode", "0014");
			resultObj.put("resultMsg", "验证码错误");
			return pakageJsonp(resultObj);
		}
		redisService.remove(phone);
		

		SkuImport sku = new SkuImport();
		sku.setPhone(phone);
		sku.setName(name);
		SkuImport sku2 = skuImportService.findForObject(sku);
		if(null==sku2){
			resultObj.put("errCode", "0016");
			resultObj.put("resultMsg", "当前信息与系统登记不符，认证失败");
			return pakageJsonp(resultObj);
		}
		
		sku2.setIsBind("1");
		skuImportService.update(sku2);
		
		
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		SkuUser phone_u = skuUserService.findByPhone(phone);
		SkuUser newUser = null;
		if(null==skuUser){
			if(phone_u!=null){
				if("0".equals(phone_u.getStatus())){
				    newUser = phone_u;
				}else{
					resultObj.put("errCode", "0015");
					resultObj.put("resultMsg", "手机号已被使用");
					return pakageJsonp(resultObj);
				}
			}else{
				newUser = new SkuUser();
			}
		}else{
			if(phone_u!=null){
				if(phone_u.getId().equals(skuUser.getId())){
					newUser = skuUser;
				}else{
					if("0".equals(phone_u.getStatus())){
						   newUser = phone_u;
 					   }else{
						   resultObj.put("errCode", "0015");
						   resultObj.put("resultMsg", "手机号已被使用");
						   return pakageJsonp(resultObj);
					}
				}
			}else{
				//最新手机号 
				 newUser = new SkuUser();
				 
			}
			 skuUser.setStatus("0");
			 skuUser.setSkuName("--");
			 skuUser.setSkuCompanyName("--");
			 skuUser.setWxUserId("--");
			 skuUserService.update(skuUser);
		}
		
		//手机号已经被解绑 可进行绑定
		newUser.setSkuCompanyName(company);
		newUser.setSkuPhone(phone);
		newUser.setSkuName(name);
		newUser.setStatus("1");
		newUser.setWxUserId(u.getId());
		newUser.setNick(u.getNick());
		newUser.setWxHeadImg(u.getWxHeadImg());
		if(null==newUser.getCreateTime()){
		   newUser.setCreateTime(new Date());
		}
		if(StringUtils.isNotBlank(newUser.getId())){
		    skuUserService.update(newUser);
		}else{
			skuUserService.save(newUser);
		}
		
		List<SkuCategory> list = new ArrayList<SkuCategory>();
		  SkuCategory e = null;
		   for (int i=0;i<array.size();i++){
			 e = new SkuCategory();
			 e.setCategoryId(array.getString(i));
			 e.setUserId(newUser.getId());
			 list.add(e);
		 }
		
		//先删除再添加
		categoryService.deleteSkuRelated(newUser.getId());
		categoryService.addSkuRelated(list);
		
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "成功认证");
		return pakageJsonp(resultObj);
	   
		
	}
	
	/**发送短信
	 * @throws Exception **/
	@RequestMapping("/sendSms")
    public String sendSms(HttpServletRequest request) throws Exception{
    	JSONObject parameterObj = super.readToJSONObect(request);
		JSONObject resultObj = new JSONObject();
		String phone = parameterObj.getString("phone");
		if (StringUtils.isBlank(phone)) {
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "参数缺失phone");
			return pakageJsonp(resultObj);
		}
		
		JSONObject obj = smsService.sendCodeSms(phone);
		if(null==obj||!obj.containsKey("result")){
			resultObj.put("errCode", "0009");
			resultObj.put("resultMsg", "短信发送失败");
			return pakageJsonp(resultObj);
		}
		if(!"0".equals(obj.getString("id"))){
			resultObj.put("errCode", "0000");
			resultObj.put("resultMsg", "短信发送成功");
			return pakageJsonp(resultObj);
		}
//		redisService.set(phone, "2222");
		JSONObject resultData = new JSONObject();
		log.info("短信发送成功");
			resultObj.put("resultData", resultData);
			resultObj.put("errCode", "0000");
			resultObj.put("resultMsg", "短信发送成功");
		return pakageJsonp(resultObj);
    }
	
	/**
	 * 获取全部一级品类列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllProductTypeList")
	public String getAllProductTypeList(HttpServletRequest request) throws Exception{
		JSONObject resultObj = new  JSONObject();
		Category category = new Category();
		category.setDeletStatus("1");
		List<Category> list = categoryService.findList(category);
		if(null!=list&&list.size()>0){
			JSONObject resultData = new JSONObject();
			JSONArray array = new JSONArray();
			for(Category ca:list){
				JSONObject c = new JSONObject();
				c.put("id", ca.getId());
				c.put("name", ca.getCategoryName());
				c.put("picture", ca.getIcon());
				array.add(c);
			}
			resultData.put("list", array);
			resultObj.put("resultData",resultData);
		}
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "获取品类列表成功");
		return  pakageJsonp(resultObj);
		
	}
	/**
	 * 用户身份切换接口
	 * @param reuqets
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeIdentity")
	public String changeIdentity(HttpServletRequest reuqets) throws Exception{
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		String identityType = parameterObj.getString("identityType");
		JSONObject resultObj = new JSONObject();
		if(StringUtils.isBlank(sid)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "参数缺失sid");
			return pakageJsonp(resultObj);
		}
		if(StringUtils.isBlank(identityType)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "参数缺失identityType");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if(null==u){
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效用户为空");
			return pakageJsonp(resultObj);
		}
		u.setCurrentType(identityType);
		wxUserService.update(u);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "身份切换成功");
		return  pakageJsonp(resultObj);
	}
	
	/**
	 * 获取sku经理 关注的一级品类
	 * @return
	 */
	@RequestMapping("/getSkuerProductTypeList")
	public String getSkuerProductTypeList(){
		JSONObject parameterObj = super.readToJSONObect(request);
		String sid = parameterObj.getString("sid");
		JSONObject resultObj = new JSONObject();
		if(StringUtils.isBlank(sid)){
			resultObj.put("errCode", "1000");
			resultObj.put("resultMsg", "参数缺失sid");
			return pakageJsonp(resultObj);
		}
		WxUser u = wxUserService.getUserFromSession(sid);
		if(null==u){
			resultObj.put("errCode", "2000");
			resultObj.put("resultMsg", "sid无效用户为空");
			return pakageJsonp(resultObj);
		}
		SkuUser skuUser = skuUserService.findByWxUser(u.getId());
		List<Category> cagegoryList = categoryService.selectSkuCategory(null!=skuUser?skuUser.getId():"--");
		JSONObject resultData = new JSONObject();
		if(null!=cagegoryList&&cagegoryList.size()>0){
			JSONArray array = new JSONArray();
			for(Category c:cagegoryList){
				JSONObject ca = new JSONObject();
				ca.put("id", c.getId());
				ca.put("name", c.getCategoryName());
				array.add(ca);
			}
		  resultData.put("relatedProductType", array);	
		}
		resultObj.put("resultData", resultData);
		resultObj.put("errCode", "0000");
		resultObj.put("resultMsg", "成功");
		return pakageJsonp(resultObj);
	}
	
	
}
