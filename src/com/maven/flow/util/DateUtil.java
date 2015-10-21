
package com.maven.flow.util;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 日期转换类
 * 转换一个 java.util.Date 对象到一个字符串以及
 * 一个字符串到一个 java.util.Date 对象. 
 */
public class DateUtil {

	public static final String DATE_PATTERN = "(19[0-9][0-9]|20[0-9][0-9])-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1])";
	public static final String DATETIME_PATTERN = "(19[0-9][0-9]|20[0-9][0-9])-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = DATE_FORMAT+" HH:mm:ss";
	
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static String datePattern = "yyyy-MM-dd";
    private static String timePattern = "HH:mm:ss";
    
    private static String specPattern="MM/dd/yyyy";

    /**
     * Return 缺省的日期格式 (yyyy/MM/dd)
     * @return 在页面中显示的日期格式
     */
    public static String getDatePattern() {
        return datePattern;
    }

    /**
     * 根据日期格式，返回日期按datePattern格式转换后的字符串
     *
     * @param aDate 日期对象
     * @return 格式化后的日期的页面显示字符串
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }
    
    public static final String getSpecDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(specPattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }
    public static void main(String[] args) {
		System.out.println(getSpecDate(new Date()));
	}
    
    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param aMask 输入字符串的格式
     * @param strDate 一个按aMask格式排列的日期的字符串描述
     * @return  Date 对象
     * @see java.text.SimpleDateFormat
     * @throws ParseException
     */
    public static final Date convertStringToDate(String aMask, String strDate)
      throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                      + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * yyyy/MM/dd HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * This method returns the current date in the format: yyyy/MM/dd
     * 
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * 
     * @see java.text.SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 根据日期格式，返回日期按datePattern格式转换后的字符串
     * 
     * @param aDate 
     * @return 
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }

    public static final String convertDateToStringHaveHourAndMinite(Date aDate) {
        return getDateTime(DATETIME_FORMAT, aDate);
    }
    /**
     * 按照日期格式，将字符串解析为日期对象
     * 
     * @param strDate (格式 yyyy/MM/dd)
     * @return 
     * 
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate)
      throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + datePattern);
            }

            aDate = convertStringToDate(datePattern, strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate
                      + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(),
                                     pe.getErrorOffset());
                    
        }

        return aDate;
    }

    /**
     * 获取中文格式的时间
     * @param date
     * @return
     */
    public static String getChineseDate(Date date){

    	Calendar calendar = Calendar.getInstance();
    	if(date != null)
    		calendar.setTime(date);
    	String year = String.valueOf(calendar.get(Calendar.YEAR));
    	int month= calendar.get(Calendar.MONTH)+1;
    	int day = calendar.get(Calendar.DAY_OF_MONTH);
    	String[] arrNum = {"\u96f6","\u4e00","\u4e8c","\u4e09","\u56db","\u4e94","\u516d","\u4e03","\u516b","\u4e5d","\u5341","\u5341\u4e00","\u5341\u4e8c"};
    	String strTmp="";
    	for(int i=0,j=year.length();i<j;i++){
    		strTmp += arrNum[Integer.parseInt(String.valueOf(year.charAt(i)))];
    	}
    	strTmp += "\u5e74";
    		strTmp += arrNum[month]+"\u6708";
    	if(day<10)
    		strTmp += arrNum[day];
    	else if (day <20)
    		strTmp += "\u5341"+((day-10)==0?"":arrNum[day-10]);
    	else if (day <30 )
    		strTmp += "\u4e8c\u5341"+((day-20)==0?"":arrNum[day-20]);
    	else 
    		strTmp += "\u4e09\u5341"+((day-30)==0?"":arrNum[day-30]);
    	strTmp += "\u65e5";
    	
    	return strTmp;
    }
    

}
