package com.icloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.netflix.discovery.shared.Application;


@SpringBootApplication
@MapperScan("com.icloud.dao.*")/** 扫描mybatis mapper接口 */
@PropertySource("classpath:jdbc.properties")
@PropertySource("classpath:config.properties")
@EnableTransactionManagement/**启用注解事务管理**/
@EnableScheduling  
public class ApplicationStartUp extends SpringBootServletInitializer{	
	  @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(Application.class);
	    }
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStartUp.class, args);
	}
	
}
