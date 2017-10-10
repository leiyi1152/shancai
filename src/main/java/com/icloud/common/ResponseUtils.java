package com.icloud.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * 
  * @filename     : ResponseUtils.java   
  * @description  : TODO 
  * @version      : V 1.0
  * @author       : zcb
  * @create       : 2016-5-9 上午9:09:56  
  * @Copyright    : hyzy Corporation 2016    
  * 
  * Modification History:
  * 	Date			Author			Version			Description
  *--------------------------------------------------------------
  *2016-5-9 上午9:09:56
 */
public class ResponseUtils {

	// send context
	public static void render(HttpServletResponse response, String contentType,
			String text) {
		response.setContentType(contentType);
		try {
			response.getWriter().write(text);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// JSON
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	// xml
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	// text
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	// html
	public static void renderHtml(HttpServletResponse response, String text) {
		render(response, "text/html;charset=UTF-8", text);
	}

}
