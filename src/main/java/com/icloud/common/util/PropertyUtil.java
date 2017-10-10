package com.icloud.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

	private static final String BUNDLE_NAME = "/conf.properties";

	public static String getString(String key) {
		try {
			return prop.getProperty(key);
		} catch (Exception e) {
			return '!' + key + '!';
		}
	}
	
	private static  Properties prop=new Properties(); 
	static{
		try {  
	        InputStream is=PropertyUtil.class.getResourceAsStream(BUNDLE_NAME);  
	        prop.load(is);  
	        is.close();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } 
	} 
}
