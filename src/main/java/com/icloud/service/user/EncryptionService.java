package com.icloud.service.user;

/**
 * 
 * @author 
 *
 */
public interface EncryptionService {

	/**
	 * 微信加密接口
	 * @param request
	 * @return
	 */
	public Object encryption(String jsonParams);
	
}
