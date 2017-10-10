package com.icloud.service.user.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.icloud.common.dto.UserSession;
import com.icloud.common.util.RandomUtil;
import com.icloud.common.util.wx.HttpRequestUtil;
import com.icloud.common.util.wx.WxConst;
import com.icloud.model.user.SkuUser;
import com.icloud.model.user.User;
import com.icloud.model.user.WxUser;
import com.icloud.service.redis.RedisService;
import com.icloud.service.user.LoginService;
import com.icloud.service.user.SkuUserService;
import com.icloud.service.user.UserService;
import com.icloud.service.user.WxUserService;

@Service
public class LoginServiceImpl  implements LoginService{
	


	public final static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	private RedisService redisService;
	@Autowired
	private WxUserService wxUserService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private SkuUserService skuUserService;
	
	/**
	 * 微信登录
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Object login(String jsonParams, HttpServletRequest request) throws Exception{
		JSONObject resjson = new JSONObject();
		if(!StringUtils.isEmpty(jsonParams)){
			JSONObject json =  JSONObject.parseObject(jsonParams);
			String url = WxConst.getOpenIdUrl.replace("APPID", WxConst.appid).replace("SECRET", WxConst.secret)
					.replace("JSCODE",json.getString("code"));
			JSONObject jsonObject = HttpRequestUtil.httpRequest(url,  "GET", null);
			if (!(jsonObject == null) && null != jsonObject.getString("openid")) {
				log.info("jsonObj:" + jsonObject.toString());
				String openId = jsonObject.getString("openid");
				String session_key = jsonObject.getString("session_key");
				if(!StringUtils.isEmpty(openId) && !StringUtils.isEmpty(session_key)){
					WxUser user = wxUserService.findByOpenId(openId);
					JSONObject user_raw = JSONObject.parseObject(json.getString("user_raw"));
					if(null==user){
						user = new WxUser();
						user.setNick(user_raw.getString("nickName"));
						user.setOpenId(openId);
						user.setWxHeadImg(user_raw.getString("avatarUrl"));
						user.setCurrentType("0");
						user.setCreateTime(new Date());
						wxUserService.save(user);
						SkuUser skuUser = skuUserService.findByWxUser(user.getId());
						if(null==skuUser){
							skuUser = new SkuUser();
							skuUser.setWxUserId(user.getId());
							skuUser.setStatus("0");
							skuUser.setSkuCompanyName("--");
							skuUser.setSkuName("--");
							skuUser.setSkuPhone("--");
							skuUser.setNick(user.getNick());
							skuUser.setWxHeadImg(user.getWxHeadImg());
							skuUserService.save(skuUser);
						}else{
							skuUser.setNick(user.getNick());
							skuUser.setWxHeadImg(user.getWxHeadImg());
							skuUserService.update(skuUser);
						}
						User uu = userService.findByWxUser(user.getId());
						if(null==uu){
							uu = new User();
							uu.setWxUserId(user.getId());
							uu.setStatus("0");
							uu.setNick(user.getNick());
							uu.setWxHeadImg(user.getWxHeadImg());
							userService.save(uu);
						}else{
							uu.setNick(user.getNick());
							uu.setWxHeadImg(user.getWxHeadImg());
							userService.update(uu);
						}
						
					}else{
						user.setNick(user_raw.getString("nickName"));
						user.setOpenId(openId);
						user.setWxHeadImg(user_raw.getString("avatarUrl"));
						wxUserService.update(user);
					}
					String rd_session = RandomUtil.getRandomString(20);
					JSONObject resultData = new JSONObject();
					resjson.put("errCode", "0000");
					resjson.put("resultMsg", "登陆成功");
					resjson.put("resultData", resultData);
					resultData.put("sid", rd_session);
					resultData.put("identityType",user.getCurrentType());
				
					
					log.info("openId=" + openId+"; session_key="+session_key);
					
					UserSession userSession =  new UserSession();
					userSession.setOpenId(openId);
					userSession.getSession_key();
					userSession.setLoginTime(System.currentTimeMillis());
					redisService.set(rd_session, userSession,1200L);
					
					return resjson;
				}else{
					
					resjson.put("errCode", "0002");
					resjson.put("resultMsg", "openid获取失败");
				}
				
			}else{
				resjson.put("errCode", "0002");
				resjson.put("resultMsg", "openid获取失败");
			}
		}else{
			resjson.put("errCode", "1000");
			resjson.put("resultMsg", "参数缺失");
		}
	 
		return resjson;
		
	}
	
	@Override
	public Object checkSession(String jsonParams,HttpServletRequest request) {
		JSONObject resjson = new JSONObject();
		if(org.apache.commons.lang.StringUtils.isNotBlank(jsonParams)){
			JSONObject json =  JSONObject.parseObject(jsonParams);
			String rd_session = json.getString("sid");
		 if(org.apache.commons.lang.StringUtils.isNotBlank(rd_session)){	
			UserSession user = (UserSession) redisService.getSession(rd_session);
		    if(null==user){
		    	resjson.put("errCode", "0011");
				resjson.put("resultMsg", "本地会话失效");
		    	log.info("redis失效式会话失效"); 
				return resjson;
		    }
			Long loginTime = user.getLoginTime();
			if(System.currentTimeMillis()/1000-loginTime/1000>=7200){
				resjson.put("errCode", "0012");
				resjson.put("resultMsg", "微信登录状态失效");
				return resjson;
			}
				resjson.put("resultCode", "0000");
				resjson.put("resultMsg", "会话尚未失效");
				return resjson;
			}else{
				resjson.put("errCode", "1000");
				resjson.put("resultMsg", "参数缺失");
				return resjson;
			}
		}
		resjson.put("errCode", "1000");
		resjson.put("resultMsg", "参数缺失");
		return resjson;
		
		
	}
	
 
}