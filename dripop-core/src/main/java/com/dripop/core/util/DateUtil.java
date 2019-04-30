package com.dripop.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 日期工具类,提供时间转换、比较、格式化等各种常用方法
 * <ul>
 * <li>
 * <b>修改历史：</b><br/>
 * <p>
 * [2015-9-11上午9:28:17]涂小炼<br/>
 * 新建
 * </p>
 * </li>
 * </ul>
 */

public class DateUtil {
	private static Logger LOG = LoggerFactory.getLogger(DateUtil.class);
	/**
	 * 时间间隔：日
	 */
	public final static int DATE_INTERVAL_DAY = 1;
	/**
	 * 时间间隔：周
	 */
	public final static int DATE_INTERVAL_WEEK = 2;
	/**
	 * 时间间隔：月
	 */
	public final static int DATE_INTERVAL_MONTH = 3;
	/**
	 * 时间间隔：年
	 */
	public final static int DATE_INTERVAL_YEAR = 4;
	/**
	 * 时间间隔：小时
	 */
	public final static int DATE_INTERVAL_HOUR = 5;
	/**
	 * 时间间隔：分钟
	 */
	public final static int DATE_INTERVAL_MINUTE = 6;
	/**
	 * 时间间隔：秒
	 */
	public final static int DATE_INTERVAL_SECOND = 7;
	/**
	 * 时间格式：年月日
	 */
	public final static String DATE_FORMAT_YMD = "yyyy-MM-dd";
	/**
	 * 时间格式：年月日时分秒
	 */
	public final static String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 获得时间
	 * @author 涂小炼
	 * @creationDate. 2012-7-31 下午03:06:05 
	 * @param date 时间
	 * @param dateFormat 时间格式
	 * @return 时间
	 */
	public static Date getDate(Date date, String dateFormat) {
		return dateFormat(dateFormat(date, dateFormat), dateFormat);
	}
	/**
	 * 获得当前日期(年月日)
	 * @author 涂小炼
	 * @creationDate. 2010-8-27 下午05:11:23
	 * @return 当前时间（yyyy-MM-dd）
	 */
	public static Date getNowDate() {
		return dateFormat(dateFormat(new Date(), DATE_FORMAT_YMD), DATE_FORMAT_YMD);
	}
	/**
	 * 获取当前时间字符串(年月日)
	 * @author 涂小炼
	 * @creationDate. 2011-5-4 下午08:22:34 
	 * @return 时间字符串
	 */
	public static String getNowStringDate() {
	    return dateFormat(new Date(), DATE_FORMAT_YMD);
	}
	/**
	 * 获得当前时间(年月日时分秒)
	 * @author 涂小炼
	 * @creationDate. 2010-8-27 下午05:12:57
	 * @return 当前时间（yyyy-MM-dd HH:mm:ss）
	 */
	public static Date getNowTime() {
		return dateFormat(dateFormat(new Date(), DATE_FORMAT_YMDHMS), DATE_FORMAT_YMDHMS);
	}
	/**
	 * 获取当前时间字符串(年月日时分秒)
	 * @author 涂小炼
	 * @creationDate. 2014-3-10 下午03:16:42 
	 * @return 时间字符串
	 */
	public static String getNowStringTime() {
		 return dateFormat(new Date(), DATE_FORMAT_YMDHMS);
	}
	/**
	 * 获得明天的日期字符串(格式年月日)
	 * @author 涂小炼
	 * @creationDate. 2011-5-4 下午08:19:28 
	 * @return 明天的日期
	 */
	public static String getTomorrowStringDate() {
	    return dateFormat(getTomorrowTime(), DATE_FORMAT_YMD);
	}
	/**
	 * 获得明天的日期(年月日)
	 * @author 涂小炼
	 * @creationDate. 2011-5-4 下午08:19:57 
	 * @return 明天的日期
	 */
	public static Date getTomorrowDate() {
		return dateAdd(DATE_INTERVAL_DAY, getNowDate(), 1);
	}
	/**
	 * 获得明天的时间(年月日时分秒)
	 * @author 涂小炼
	 * @creationDate. 2011-5-4 下午08:20:19 
	 * @return 明天的时间
	 */
	public static Date getTomorrowTime() {
	    return dateAdd(DATE_INTERVAL_DAY, getNowTime(), 1);
	}
	/**
	 * 获得昨天的日期
	 * @author 涂小炼
	 * @creationDate. 2013-10-22 下午03:54:48 
	 * @return 昨天的日期
	 */
	public static Date getYesterdayDate() {
		return dateAdd(DATE_INTERVAL_DAY, getNowDate(), -1);
	}
	/**
	 * 获取当月第一天   
	 * @author 涂小炼
	 * @creationDate. 2013-10-22 下午03:45:53 
	 * @return 日期
	 */
    public static Date getMonthFirst() {
    	Calendar lastDate = Calendar.getInstance();
    	lastDate.set(Calendar.DATE, 1); // 设为当前月的1号
    	return getDate(lastDate.getTime(), DATE_FORMAT_YMD);
    }
    /**
     * 获得下个月第一天的日期
     * @author 涂小炼
     * @creationDate. 2013-10-22 下午03:52:38 
     * @return 日期
     */
    public static Date getNextMonthFirst() {
    	Calendar lastDate = Calendar.getInstance();
    	lastDate.add(Calendar.MONTH, 1); // 加一个月
    	lastDate.set(Calendar.DATE, 1);  // 把日期设置为当月第一天
    	return getDate(lastDate.getTime(), DATE_FORMAT_YMD);
    }
	/**
	 * 取得当前星期几
	 * @author 涂小炼
	 * @creationDate. 2010-9-20 下午05:34:36 
	 * @param date 时间
	 * @return 星期
	 */
	public static String getWeekOfDate(Date date) {
		if (date == null) return null;
	    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}; 
	    Calendar cal = Calendar.getInstance(); 
	    cal.setTime(date); 
	    int w = cal.get(Calendar.DAY_OF_WEEK) - 1; 
	    if (w < 0) w = 0; 
	    return weekDays[w]; 
	}
	/**
	 * 时间类型转换返回字符串
	 * @author 涂小炼
	 * @creationDate. 2010-8-27 下午05:18:37
	 * @param date 时间
	 * @param dateFormat 时间格式
	 * @return 转换后的时间字符串
	 */
	public static String dateFormat(Date date, String dateFormat) {
		if (date == null) return null;
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}
	/**
	 * 字符串时间类型转换返回Date类型
	 * @author 涂小炼
	 * @creationDate. 2010-8-27 下午05:23:35
	 * @param date 字符串时间
	 * @param dateFormat 时间格式
	 * @return 转换后的时间
	 */
	public static Date dateFormat(String date, String dateFormat) {
		if (StringUtil.isEmpty(date)) return null;
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			return format.parse(date);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
	/**
	 * 加时间
	 * @author 涂小炼
	 * @creationDate. 2010-8-27 下午05:28:06
	 * @param interval 增加项，可以是天数、月份、年数、时间、分钟、秒
	 * @param date 时间
	 * @param num 加的数目
	 * @return 时间加后的时间
	 */
	public static Date dateAdd(int interval, Date date, int num) {
		if (date == null) return null;
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		switch (interval) {
		case DATE_INTERVAL_DAY:
			calendar.add(Calendar.DATE, num);
			break;
		case DATE_INTERVAL_WEEK:
			calendar.add(Calendar.WEEK_OF_MONTH, num);
			break;
		case DATE_INTERVAL_MONTH:
			calendar.add(Calendar.MONTH, num);
			break;
		case DATE_INTERVAL_YEAR:
			calendar.add(Calendar.YEAR, num);
			break;
		case DATE_INTERVAL_HOUR:
			calendar.add(Calendar.HOUR, num);
			break;
		case DATE_INTERVAL_MINUTE:
			calendar.add(Calendar.MINUTE, num);
			break;
		case DATE_INTERVAL_SECOND:
			calendar.add(Calendar.SECOND, num);
			break;
		default:
		}
		return calendar.getTime();
	}
	/**
	 * 两个时间时间差[前面时间和比较时间比,小于比较时间返回负数]
	 * @author 涂小炼
	 * @creationDate. 2010-8-27 下午05:26:13
	 * @param interval 比较项，可以是天数、月份、年数、时间、分钟、秒
	 * @param date 时间
	 * @param compare 比较的时间
	 * @return 时间差(保留两位小数点,小数点以后两位四舍五入)
	 */
	public static double getDateDiff(int interval, Date date, Date compare) {
		if (date == null || compare == null) return 0;
		double result = 0;
		double time = 0;
		Calendar calendar = null;
		switch (interval) {
		case DATE_INTERVAL_DAY:
			time = date.getTime() - compare.getTime();
			result = time / 1000d / 60d / 60d / 24d;
		    break;
		case DATE_INTERVAL_HOUR:
			time = date.getTime() - compare.getTime();
			result = time / 1000d / 60d / 60d;
			break;
		case DATE_INTERVAL_MINUTE:
			time = date.getTime() / 1000d / 60d;
			result = time - compare.getTime() / 1000d / 60d;
			break;
		case DATE_INTERVAL_MONTH:
			calendar = Calendar.getInstance();
			calendar.setTime(date);
		    time = calendar.get(Calendar.YEAR) * 12d;
		    calendar.setTime(compare);
		    time -= calendar.get(Calendar.YEAR) * 12d;
		    calendar.setTime(date);
		    time += calendar.get(Calendar.MONTH);
		    calendar.setTime(compare);
		    result = time - calendar.get(Calendar.MONTH);
			break;
		case DATE_INTERVAL_SECOND:
			time = date.getTime() - compare.getTime();
			result = time / 1000d;
			break;
		case DATE_INTERVAL_WEEK:
			calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    time = calendar.get(Calendar.YEAR) * 52d;
		    calendar.setTime(compare);
		    time -= calendar.get(Calendar.YEAR) * 52d;
		    calendar.setTime(date);
		    time += calendar.get(Calendar.WEEK_OF_YEAR);
		    calendar.setTime(compare);
		    result = time - calendar.get(Calendar.WEEK_OF_YEAR);
			break;
		case DATE_INTERVAL_YEAR:
			calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    time = calendar.get(Calendar.YEAR);
		    calendar.setTime(compare);
		    result = time - (double)calendar.get(Calendar.YEAR);
			break;
		default:
			break;
		}
		return new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 获取时间差[前面时间和比较时间比,小于比较时间返回负数]
	 * @author 涂小炼
	 * @creationDate. 2010-9-1 下午04:36:07 
	 * @param level 返回时间等级(1:返回天;2:返回天-小时;3:返回天-小时-分4:返回天-小时-分-秒;)
	 * @param date 时间
	 * @param compare 比较的时间
	 * @return 时间差(保留两位小数点,小数点以后两位四舍五入)
	 */
	public static String getDateBetween(Integer level, Date date, Date compare) {
		if (date == null || compare == null) return null;
		long s = new BigDecimal(getDateDiff(DATE_INTERVAL_SECOND, date, compare)).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		int ss = 1;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
	   
		long day = s / dd;
		long hour = (s - day * dd) / hh;
		long minute = (s - day * dd - hour * hh) / mi;
		long second = (s - day * dd - hour * hh - minute * mi) / ss;
		String flag =(day < 0 || hour < 0 || minute < 0 || second < 0) ? "-" : "";
		day = Math.abs(day);
		hour = Math.abs(hour);
		minute = Math.abs(minute);
		second = Math.abs(second);
		StringBuilder result = new StringBuilder(flag);
		switch (level) {
		case 1:
			if (day != 0)result.append(day).append("天");
			break;
		case 2:
			if (day != 0)result.append(day).append("天");
			if (hour != 0)result.append(hour).append("小时");
			break;
		case 3:
			if (day != 0)result.append(day).append("天");
			if (hour != 0)result.append(hour).append("小时");
			if (minute != 0)result.append(minute).append("分");
			break;
		case 4:
			if (day != 0)result.append(day).append("天");
			if (hour != 0)result.append(hour).append("小时");
			if (minute != 0)result.append(minute).append("分");
			if (second != 0)result.append(second).append("秒");
			break;
		default:
			break; 
		}
		return result.toString();
	}
	/**
	 * 时间是否是今天
	 * @author 涂小炼
	 * @creationDate. 2011-5-4 下午08:24:58 
	 * @param date 时间
	 * @return 布尔
	 */
	public static boolean isToday(Date date) {
		if (date == null) return false;
	    return getNowStringDate().equals(dateFormat(date, DATE_FORMAT_YMD));
	}
	/**
	 * 时间是否合法
	 * @author 涂小炼
	 * @creationDate. 2012-6-19 下午01:07:59 
	 * @param date 时间
	 * @param dateFormat 时间格式
	 * @return
	 */
	public static boolean isValidDate(String date, String dateFormat) {
		try {
			new SimpleDateFormat(dateFormat).parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getCurrDate(String formatStr) {
        return getFormattedDate(new Date(), formatStr);
    }
    
	/**
     * 得到当前日期，格式yyyy-MM-dd。
     * @return String 格式化的日期字符串
     */
    public static String getCurrDate() {
        return getFormattedDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 对输入的日期字符串进行格式化,
     * 如果输入的是0000/00/00 00:00:00则返回空串.
     * @param date      String 需要进行格式化的日期字符串
     * @param formatStr String 要转换的日期格式
     * @return String 经过格式化后的字符串
     */
    public static String getFormattedDate(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
            return formatter.format(date);
        } catch (Exception e) {
        	LOG.error("时间转换异常",e);
            return "";
        }
    }
    
    /**
     * 当前日期增加iDays天后日期 add by liufeng 20061208
     *
     * @param strDate     String
     * @param iDays       int
     * @param strFormatTo String
     * @return String
     */
    public static String getDateAddDay(String strDate, int iDays, String strFormatTo) {
        Timestamp tsDate = Timestamp.valueOf(strDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tsDate);
        cal.add(Calendar.DAY_OF_MONTH, iDays);
        Timestamp tsEndDateAdd = new Timestamp(cal.getTimeInMillis());
        return DateUtil.getFormattedDate(tsEndDateAdd, strFormatTo);
    }
	
    //指定日期的前两天 
    public static String getQueryStr(Date dates, int  d){
		String date=getDateAddDay(getFormattedDate(dates, "yyyy-MM-dd HH:mm:ss"),d,"yyyy-MM-dd HH:mm:ss");
		if(date!=null&&!"".equals(date)){
			date=date.substring(0,10);
		}
		return date;
	}
    
    /**
     * 对输入的日期按照默认的格式yyyy-MM-dd转换.
     *
     * @param dtDate java.sql.Timestamp 需要进行格式化的日期字符串
     * @return String 经过格式化后的字符串
     */
    public static String getFormattedDate(Date dtDate) {
        return getFormattedDate(dtDate, "yyyy-MM-dd");
    }
    
    public static Date getDateByStrings(String strDate) {
        return getDateByString(strDate, "yyyy-MM-dd");
    }
    
    /**
     * 根据传入的日期字符串转换成相应的日期对象，
     * 如果字符串为空或不符合日期格式，则返回当前时间。
     *
     * @param strDate String 日期字符串
     * @return java.sql.Timestamp 日期对象
     */
    public static Date getDateByString(String strDate, String dateFormat) {
        if (strDate == null || strDate.trim().length() < 1) {
            return null;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(false);
            return formatter.parse(strDate);
        } catch (Exception ex) {
        	LOG.error(ex.getMessage());
            return null;
        }
    }
    
    /**
     * 获取指定年月的第一天
     * @author wangdan
     * @creationDate. 2015-6-14 上午09:54:11 
     * @param yearMonth
     * @return
     */
    public static String getMonthFirstDay(String yearMonth){
    	Date currDate = DateUtil.dateFormat(yearMonth, "yyyy年MM月");
		String startDate = DateUtil.getFormattedDate(currDate, "yyyyMM")+"01";//startDate
		return startDate;
    }
    
    /**
     * 获取指定年月的最后一天
     * @author wangdan
     * @creationDate. 2015-6-14 上午09:56:33 
     * @param yearMonth
     * @return
     */
    public static String getMonthLastDay(String yearMonth){
    	Date currDate = DateUtil.dateFormat(yearMonth, "yyyy年MM月");
		Calendar a = Calendar.getInstance();;
		a.setTime(currDate);
		a.set(Calendar.DATE, 1); //把日期设置为当月第一天
		a.roll(Calendar.DATE, -1); //日期回滚一天，也就是最后一天
		String endDate = DateUtil.dateFormat(a.getTime(),"yyyyMMdd");
		return endDate;
    }
    
    public static String getSpecifiedDayAfter(String specifiedDay, int days){ 
    	Calendar c = Calendar.getInstance(); 
    	Date date=null; 
    	try { 
    		date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
    	} catch (Exception ex) {
        	LOG.error(ex.getMessage());
            return specifiedDay;
        }
    	c.setTime(date); 
    	c.add(Calendar.DAY_OF_MONTH, days);

    	String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
    	return dayAfter; 
    } 
    
    public static long getMinuteAfter(int minutes){ 
    	Calendar c = Calendar.getInstance(); 
    	c.setTime(new Date()); 
    	c.add(Calendar.MINUTE, minutes);
    	
    	return c.getTimeInMillis();
    } 
    
    public static boolean compareTime(long times){
    	Calendar c = Calendar.getInstance(); 
    	c.setTime(new Date()); 
    	if (c.getTimeInMillis() > times){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static boolean compareTime(Date d){
    	Calendar c = Calendar.getInstance(); 
    	c.setTime(new Date()); 
    	if (c.getTimeInMillis() > d.getTime()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /** 
     *  
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * 天 * 小时 * 分 * seconds 后的格式  
     */  
    public static String formatDuring(long mss) {  
        long days = mss / (1000 * 60 * 60 * 24);  
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
//        long seconds = (mss % (1000 * 60)) / 1000;  
        return days + "天" + hours + "小时" + minutes + "分";  
    }  
    /** 
     *  
     * @param begin 时间段的开始 
     * @param end   时间段的结束 
     * @return  输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示 
     */  
    public static String formatDuring(Date begin, Date end) {  
        return formatDuring(end.getTime() - begin.getTime());  
    }
    
    public static Date getDateLastSecond(Date date) {
    	date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date;
    }
    
	@SuppressWarnings({ "deprecation", "unused" })
	public static String getMonthAndDaysBetweenDate(String date1,String date2){
		try{
	    	  Date d1 = dateFormat(date1, DATE_FORMAT_YMD);
	    	  Date d2=dateFormat(date2, DATE_FORMAT_YMD);
	
	    	  int months=0;//相差月份
	    	  int days=0;
	    	  int y1=d1.getYear();
	    	  int y2=d2.getYear();
	    	  int dm1=d2.getMonth();//起始日期月份
	    	  int dm2=d2.getMonth();//结束日期月份
	    	  int dd1=d1.getDate(); //起始日期天
	    	  int dd2=d2.getDate(); //结束日期天
	    	  if(d1.getTime()<d2.getTime()){
		    	  months=d2.getMonth()-d1.getMonth()+(y2-y1)*12;
		    	  if(dd2<dd1){
		    		  months=months-1;
		    	  }
		    	  days=(int)getDateDiff(DATE_INTERVAL_DAY, d2, dateAdd(DATE_INTERVAL_MONTH, d1, months));
		    	  if (months > 0){
		    		  if (days > 0){
		    			  return "" + months + "月"+days+"天";
		    		  }else{
		    			  return "" + months;
		    		  }
		    	  }else{
		    		  if (days > 0){
		    			  return days+"天";
		    		  }else{
		    			  return "";
		    		  }
		    	  }
	    	  }
	    	  return "";
		}catch(Exception e){
	    	LOG.error(e.getMessage());
	    	return "";
	    }
    }

    public static String getYearMonthAddMonth(int month) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH,month); // 加一个月
		return dateFormat(getDate(lastDate.getTime(),"yyyyMM"),"yyyyMM");
	}
    public static List<String> getYearMonthAddMonthList(int month) {
		List<String> resultList = new ArrayList<String>();
		for(int i=1;i<=month;i++) {
			Calendar lastDate = Calendar.getInstance();
			lastDate.add(Calendar.MONTH,i); // 加一个月
			String resultMonth = dateFormat(getDate(lastDate.getTime(),"yyyyMM"),"yyyyMM");
			resultList.add(resultMonth);
		}
		return resultList;
	}

	/**
	 *  时间段内的时间结合 2017-07-01存储
	 *  2017-06-10  -  2017-07-31
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<String> getBetweenDateList(Date startTime, Date endTime) {
		List<String> list = new ArrayList<String>();
		int dayInt = (int)DateUtil.getDateDiff(DateUtil.DATE_INTERVAL_DAY, endTime, startTime) +1;
		if(dayInt>0){
			for(int i=0;i<dayInt;i++){
				Date date = DateUtil.dateAdd(DATE_INTERVAL_DAY, startTime, i);
				list.add(dateFormat(date,"yyyy-MM-dd"));
			}
		}
		return list;
	}

	/**
	 * 两个时间段内的秒数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static Long getBetweenDateSecond(Date startTime, Date endTime) {
		Long second = 0L;
		Long startSecond = startTime.getTime()/1000;
		Long endSecond = endTime.getTime()/1000;
		second = endSecond - startSecond;
		return second;
	}

    /**
     * dq
     * 获取结束时间
     * @param date
     * @return
     */
    public static Date getEndTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * dq
     * 获取0点
     * @param date
     * @return
     */
    public static Date getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 时间等值判断
     * @param d1
     * @param d2
     * @return
     */
    public static boolean sameDate(Date d1, Date d2){
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT_YMD);
        //fmt.setTimeZone(new TimeZone()); // 如果需要设置时间区域，可以在这里设置
        return fmt.format(d1).equals(fmt.format(d2));
    }

	/**
	 * clm
	 * 2018/5/14
	 * 判断指定时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * clm
	 * 2018/5/14
	 * d1大于d2返回1
	 * d1小于d2返回-1
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Integer compareDate(Date d1, Date d2) {
		try {
			if (d1.getTime() > d2.getTime())
			{
				return 1;
			}
			else if (d1.getTime() < d2.getTime())
			{
				return -1;
			}
			else
			{
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
}