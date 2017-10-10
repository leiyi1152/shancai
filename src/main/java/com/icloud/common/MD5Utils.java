package com.icloud.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * mad5加密   短信供应商前面加了一个0
 * 
 */
public class MD5Utils {
	
	public static String makeMD5(String password) {   
		 MessageDigest md;   
		   try {   
		    // 生成一个MD5加密计算摘要   
		    md = MessageDigest.getInstance("MD5");   
		    // 计算md5函数  设置字符集   UTF-8
		    md.update(password.getBytes("UTF-8"));   
		    // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符   
		    // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值   
		    
		  //  String pwd = new BigInteger(1, md.digest()).toString(16);
		    byte[] b=md.digest();
		    StringBuffer pwd=new StringBuffer();
		    for (int i = 0; i < b.length; i++) {
		    	int c=b[i];
		    	if (c < 0)
		    		c += 256;
		    	if (c < 16)
		    	pwd.append("0");
				pwd.append(Integer.toHexString(c));
			}
		    // System.err.println(pwd);   
		    return pwd.toString();   
		   } catch (Exception e) {   
		    e.printStackTrace();   
		   }   
		   return password;   
	}
	 /** 
     * 将源字符串使用MD5加密为字节数组 
     * @param source 
     * @return 
     */  
    public static byte[] encode2bytes(String source) {  
        byte[] result = null;  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.reset();  
            md.update(source.getBytes("UTF-8"));  
            result = md.digest();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
          
        return result;  
    }  
      
    /** 
     * 将源字符串使用MD5加密为32位16进制数 
     * @param source 
     * @return 
     */  
	  public static String encode2hex(String source) {  
	        byte[] data = encode2bytes(source);  
	  
	        StringBuffer hexString = new StringBuffer();  
	        for (int i = 0; i < data.length; i++) {  
	            String hex = Integer.toHexString(0xff & data[i]);  
	              
	            if (hex.length() == 1) {  
	                hexString.append('0');  
	            }  
	              
	            hexString.append(hex);  
	        }  
	          
	        return hexString.toString();  
	    }  
	
}
