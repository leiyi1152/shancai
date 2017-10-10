package com.icloud.common;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * @filename      : DateTools.java
 * @description   : 日期时间工具
 * @author        : chengkunxf
 * @create        : 2013-4-10 下午2:23:38
 * @copyright     : hyzy Corporation 2014
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2013-4-10 下午2:23:38
 */
public class DateTools {
		/**默认时间格式**/
		public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
		/**年月日默认时间格式**/
		public static final String YEAR_MONTY_DAY_FORMAT = "yyyy-MM-dd";
		
		/**
		 * 把当前时间转换成成字符串
		 * @param farmatString
		 * 			字符串格式
		 * @return String
		 */
		public static String convertDateToString(String farmatString) {
			Date datetime = new Date();
			SimpleDateFormat f = new SimpleDateFormat(farmatString);
			return f.format(datetime);
		}
		
		/**
		 *把指定日期格式化成指定格式 
		 * @autor wanghongkai
		 * @param date
		 * @param farmatString
		 * @return
		 */
		public static String convertDateToString(Date date,String farmatString) {
			SimpleDateFormat f = new SimpleDateFormat(farmatString);
			return f.format(date);
		}
	
	  /**
	   * 判断字符串是否是有效的日期，
	   *      yyyy-MM-dd
	   *      yyyy-MM-d
	   *      yyyy-M-dd
	   *      yyyy-M-d
	   *      yyyy/MM/dd
	   *      yyyy/MM/d
	   *      yyyy/M/dd
	   *      yyyy/M/d
	   *      yyyyMMdd"
	   * @param date 日期字符串
	   * @return 是则返回true，否则返回false
	   */
	  public static boolean isValidDate(String date) {
	    if (!StringUtils.isBlank(date)) {
	    	return false;
	    }
	    try {
		      boolean result = false;
		      SimpleDateFormat formatter;
		      char dateSpace = date.charAt(4);
		      String format[];
		      if ((dateSpace == '-') || (dateSpace == '/')) {
		    	  	format = new String[4];
			        String strDateSpace = Character.toString(dateSpace);
			        format[0] = new StringBuffer("yyyy").append(strDateSpace).append("MM").append(strDateSpace).append("dd").toString();
			        format[1] = new StringBuffer("yyyy").append(strDateSpace).append("MM").append(strDateSpace).append("d").toString(); 
			        format[2] = new StringBuffer("yyyy").append(strDateSpace).append("M").append(strDateSpace).append("dd").toString();
			        format[3] =  new StringBuffer("yyyy").append(strDateSpace).append("M").append(strDateSpace).append("d").toString();
		      }else {
			        format = new String[1];
			        format[0] = "yyyyMMdd";
		      }
	
		      for (int i = 0; i < format.length; i++){
		        String aFormat = format[i];
		        formatter = new SimpleDateFormat(aFormat);
		        formatter.setLenient(false);
		        String tmp = formatter.format(formatter.parse(date));
		        if(date.equals(tmp)) {
			          result = true;
			          break;
		        }
		      }
		      return result;
	    }catch (ParseException e) {
	    	return false;
	    }
	  }

	  /**
	   * 判断字符串是否是有效的日期，格式"yyyy-MM-dd HH:mm:ss"
	   *
	   * @param date 日期字符串
	   * @return 是则返回true，否则返回false
	   */
	  public static boolean isValidTime(String date) {
	    try {
	      SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
	      formatter.setLenient(false);
	      formatter.parse(date);
	      return true;
	    }catch (ParseException e) {
	      return false;
	    }
	  }

	  /**
	   * 转换字符串为日期，格式"yyyy-MM-dd"
	   *
	   * @param date 日期字符串,格式为"yyyy-MM-dd"
	   * @return 返回格式化的日期
	   */
	  public static Date strToDate(String date) throws ParseException {
	    SimpleDateFormat formatter = new SimpleDateFormat(YEAR_MONTY_DAY_FORMAT);
	    formatter.setLenient(false);
	    return formatter.parse(date);
	  }

	  /**
	   * 转换字符串为日期，格式"yyyy-MM-dd"
	   *
	   * @param date 日期字符串,格式为"yyyyMMdd"
	   * @return 返回格式化的日期
	   */
	  public static Date strToFormateDate(String date) throws ParseException {
	        String str = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
	        SimpleDateFormat formatter = new SimpleDateFormat(YEAR_MONTY_DAY_FORMAT);
	        formatter.setLenient(false);
	        return formatter.parse(str);
	  }
	  
	  /**
	   * 转换字符串为日期，格式"yyyy-MM-dd HH:mm:ss"
	   *
	   * @param date 日期字符串
	   * @return 返回格式化的日期
	   */
	  public static Date strToTime(String date) throws ParseException {
	    SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
	    return formatter.parse(date);
	  }

	  
	  /**
	   * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	   * @param str 字符串
	   * @param format 日期格式
	   * @return 日期
	   * @throws java.text.ParseException
	   */
	  public static Date str2Date(String str, String format){
	   if (null == str || "".equals(str)) {
		   return null;
	   }
	   // 如果没有指定字符串转换的格式，则用默认格式进行转换
	   if (null == format || "".equals(format)) {
		   format = DEFAULT_FORMAT;
	   }
	   SimpleDateFormat sdf = new SimpleDateFormat(format);
	   Date date = null;
	   try{
		    date = sdf.parse(str);
		    return date;
	    }catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    return null;
	  }

	  /** 日期转换为字符串
	   * @param date 日期
	   * @param format 日期格式
	   * @return 字符串
	   */
	  public static String date2Str(Date date, String format) {
	   if (null == date) {
		   return null;
	   }
	   SimpleDateFormat sdf = new SimpleDateFormat(format);
	   return sdf.format(date);
	  }

	  /**
	   * 时间戳转换为字符串
	   * @param time
	   * @return
	   */
	  public static String timestamp2Str(Timestamp time) {
	   Date date = null;
	   if(null != time){
		   date = new Date(time.getTime());
	   }
	   return date2Str(date, DEFAULT_FORMAT);
	  }

	  /** 字符串转换时间戳
	   * @param str
	   * @return
	   */
	  public static Timestamp str2Timestamp(String str) {
		if(StringUtils.isNotEmpty(str)){
		   Date date = str2Date(str, DEFAULT_FORMAT);
		   return new Timestamp(date.getTime());
		}else{
			return null;
		}
	  }
	  
	  
	  /**
	   * 转换日期为字符串，格式"yyyy-MM-dd"
	   *
	   * @param date 日期
	   * @return 返回格式化的日期字符串
	   */
	  public static String dateToStr(Date date) {
	    if (date == null) return null;
	    SimpleDateFormat formatter = new SimpleDateFormat(YEAR_MONTY_DAY_FORMAT);
	    return formatter.format(date);
	  }
	/**
	 * 转换日期为字符串，格式"yyyyMMdd"
	 * @param date
	 * @return
	 */
	 
	  public static String dateToStrNoBlank(Date date) {
	        if (date == null) return null;
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        return formatter.format(date);
	      }
	  /**
	   * 转换日期为字符串，格式"yyyyMMdd"
	   * @param date
	   * @return
	   */
	   
	    public static String dateToSimpleStr(Date date) {
	        String str = "";
	        String simpleStr = "";
            if (date == null) return null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            str = formatter.format(date);
            simpleStr = str.substring(2,4)+str.substring(4,6)+str.substring(6,8);
            return simpleStr;
	    }

	  /**
	   * 转换日期为字符串，格式"yyyy-MM-dd HH:mm:ss"
	   *
	   * @param date 日期
	   * @return 返回格式化的日期字符串
	   */
	  public static String timeToStr(Date date) {
	    if (date == null) return null;
	    SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
	    return formatter.format(date);
	  }

	  /**
	   * 取得现在的日期，格式"yyyy-MM-dd HH:mm:ss"
	   *
	   * @return 返回格式化的日期字符串
	   */
	  public static String getNow() {
	    SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
	    Date Now = new Date();
	    return formatter.format(Now);
	  }

	  /**
	   * 取得现在的日期，格式"yyyy-MM-dd"
	   *
	   * @return 返回格式化的日期字符串
	   */
	  public static String getDate() {
	    SimpleDateFormat formatter = new SimpleDateFormat(YEAR_MONTY_DAY_FORMAT);
	    Date Now = new Date();
	    return formatter.format(Now);
	  }
	 
	  /**
	   * 取得现在的时间，格式"HH:mm:ss"
	   *
	   * @return 返回格式化的时间字符串
	   */
	  public static String getTime() {
	    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	    Date Now = new Date();
	    return formatter.format(Now);
	  }

	  /**
	   * 取得年份，格式"yyyy"
	   *
	   * @return 返回当前年份
	   */
	  public static int getYear() {
	    Date Now = new Date();
	    return getYear(Now);
	  }
	  /**
	   * 取得当年年初日期，格式"yyyy-mm-dd"
	   * @return 返回当年年初日期
	   */
	  public static String getBeginDate(){
	      String date = String.valueOf(getYear()) +"-" + "01" +"-" + "01";
	      return date;
	  }

	  /**
	   * 取得日期的年份，格式"yyyy"
	   *
	   * @param date 日期
	   * @return 日期的年份
	   */
	  public static int getYear(Date date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
	    return Integer.parseInt(formatter.format(date));
	  }

	  /**
	   * 取得月份
	   *
	   * @return 返回当前月份
	   */
	  public static int getMonth() {
	    Date Now = new Date();
	    return getMonth(Now);
	  }

	  /**
	   * 取得日期的月份
	   *
	   * @param date 日期
	   * @return 日期的月份
	   */
	  public static int getMonth(Date date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("M");
	    return Integer.parseInt(formatter.format(date));
	  }

	  /**
	   * 取得今天的日期数
	   *
	   * @return 返回今天的日期数
	   */
	  public static int getDay() {
	    Date Now = new Date();
	    return getDay(Now);
	  }

	  /**
	   * 取得日期的天数
	   *
	   * @param date 日期
	   * @return 日期的天数
	   */
	  public static int getDay(Date date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("d");
	    return Integer.parseInt(formatter.format(date));
	  }

	  /**
	   * 获得与某日期相隔几天的日期
	   *
	   * @param date     指定的日期
	   * @param addCount 相隔的天数
	   * @return 返回的日期
	   */
	  public static Date addDay(Date date, int addCount) throws ParseException {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(strToDate(dateToStr(date)));
	    calendar.add(Calendar.DATE, addCount);
	    return calendar.getTime();
	  }

	  /**
	   * 获得与某日期相隔几天的日期（精确到时分秒）
	   *
	   * @param date     指定的日期
	   * @param addCount 相隔的天数
	   * @return 返回的日期
	   */
	  public static Date addDayPrecise(Date date, int addCount){
		  Calendar calendar = Calendar.getInstance();
		  calendar.add(Calendar.DATE, addCount);    //得到前一天
		  Date newDate = calendar.getTime();
		  return newDate;
	  }

	  /**
	   * 获得与某日期相隔几月的日期
	   *
	   * @param date     指定的日期
	   * @param addCount 相隔的月数
	   * @return 返回的日期
	   */
	  public static Date addMonth(Date date, int addCount) throws ParseException {
		Assert.notNull(date, "指定的基数日期，不能为空！");
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.MONTH, addCount);
	    return c.getTime();
	  }

	  /**
	   * 将传入的日期转换为中文格式
	   * @param enDate 指定的日期，格式为2007-01-02
	   * @return 返回的符合中文格式的日期
	   */
	  public static String enToCnDate(Date enDate){
	      String datestr = dateToStr(enDate);
	      String s=datestr.substring(0,4);
	      s = s + "年";
	      String s2=datestr.substring(5,7);
	      s2 = s2 + "月";
	      String s3 = datestr.substring(8, datestr.length());
	      s2 = s2 + s3 + "日";
	      String sFinal;
	      String [] a={"0","1","2","3","4","5","6","7","8","9"};
	      String [] b={"O","一","二","三","四","五","六","七","八","九"};
	      
	      for(int i=0;i<a.length;i++){
		        for(int j=0;j<s.length()-1;j++){
			        if(s.substring(j,j+1).equals(a[i])){
			        	s=s.substring(0,j)+b[i]+s.substring(j+1,s.length());
			        }
		        }
	      }
          String [] c={"01","02","03","04","05","06","07","08","09","10",
          "11","12","13","14","15","16","17","18","19","20",
          "21","22","23","24","25","26","27","28","29","30","31"};
   
          String [] d={"一","二","三","四","五","六","七","八","九","十",
          "十一","十二","十三","十四","十五","十六","十七","十八","十九","二十",
          "二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八",
          "二十九","三十","三十一"};
          
         for(int i=0;i<c.length;i++){
           for(int j=0;j<s2.length()-2;j++){
               if(s2.substring(j,j+2).equals(c[i])){
                   s2=s2.substring(0,j)+d[i]+s2.substring(j+2,s2.length());
               }
           }
        }
        sFinal = s + s2;
        return sFinal;
	  }
	  
	/**
	 * 获取当月第一天 
	 * @author:chengkunxf
	 * @return
	 */
    public static String getFirstDayOfMonth(){      
       String str = "";    
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
       Calendar lastDate = Calendar.getInstance();    
       lastDate.set(Calendar.DATE,1);//设为当前月的1号    
       str=sdf.format(lastDate.getTime());    
       return str;      
    }
    
	/**
	 * 计算当月最后一天,返回字符串  
	 * @author:chengkunxf
	 * @return
	 */
    public static String getEndDayOfMonth(){      
       String str = "";    
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
       Calendar lastDate = Calendar.getInstance();    
       lastDate.set(Calendar.DATE,1);//设为当前月的1号    
       lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号    
       lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天    
       str=sdf.format(lastDate.getTime());    
       return str;      
    }
    
    /**
     * 获得下个月第一天的日期
     * @author:chengkunxf
     * @return
     */
    public static String getNextMonthFirst(){    
       String str = "";    
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
       Calendar lastDate = Calendar.getInstance();    
       lastDate.add(Calendar.MONTH,1);//减一个月    
       lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天     
       str=sdf.format(lastDate.getTime());    
       return str;      
    }
}
