package com.icloud.web.user.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icloud.common.util.RequestUtil;
import com.icloud.service.user.EncryptionService;


@RestController
public class EncryptionController{
	 private EncryptionService encryptionService;
	
 
	/**微信小程序加密接口**/
	@RequestMapping(value = "/encry/encryption")
	 public Object encryption(String data,HttpServletRequest request) throws Exception {
        return  RequestUtil.pakageJsonp(request,encryptionService.encryption(data));
    }


	
}