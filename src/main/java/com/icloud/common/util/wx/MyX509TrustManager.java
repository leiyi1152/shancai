package com.icloud.common.util.wx;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * 证书信任管理器（用于https请求）编写证书过滤器
 * 
 * @author tiangai
 * @since 2014-06-30 Am 11:52
 * @version 1.0
 */
public class MyX509TrustManager implements X509TrustManager {
	/**
	 * 该方法体为空时信任所有客户端证书(检查客户端证书)
	 */
	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	/**
	 * 该方法体为空时信任所有服务器证书 (检查服务器证书)
	 */
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	/**
	 * 返回信任的证书  * @return     
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
