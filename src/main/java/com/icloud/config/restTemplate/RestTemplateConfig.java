package com.icloud.config.restTemplate;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {


	
 

	/** RestTemplate **/
	@Bean
	@LoadBalanced
	public RestTemplate restTemplateBean() {
		RestTemplate restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
		if (factory instanceof SimpleClientHttpRequestFactory) {
			((SimpleClientHttpRequestFactory) factory).setConnectTimeout(60 * 1000);
			((SimpleClientHttpRequestFactory) factory).setReadTimeout(60 * 1000);
		} else if (restTemplate.getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {

			((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout(10 * 1000);
			((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout(10 * 1000);
		}

		return restTemplate;
	}
	
}
