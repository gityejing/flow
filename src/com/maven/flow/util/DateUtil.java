
package com.maven.flow.util;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ����ת����
 * ת��һ�� java.util.Date ����һ���ַ����Լ�
 * һ���ַ�����һ�� java.util.Date ����. 
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
     * Return ȱʡ�����ڸ�ʽ (yyyy/MM/dd)
     * @return ��ҳ������ʾ�����ڸ�ʽ
     */
    public static String getDatePattern() {
        return datePattern;
    }

    /**
     * �������ڸ�ʽ���������ڰ�datePattern��ʽת������ַ���
     *
     * @param aDate ���ڶ���
     * @return ��ʽ��������ڵ�ҳ����ʾ�ַ���
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
     * �������ڸ�ʽ�����ַ�������Ϊ���ڶ���
     *
     * @param aMask �����ַ����ĸ�ʽ
     * @param strDate һ����aMask��ʽ���е����ڵ��ַ�������
     * @return  Date ����
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
     * �������ڸ�ʽ���������ڰ�datePattern��ʽת������ַ���
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
     * �������ڸ�ʽ�����ַ�������Ϊ���ڶ���
     * 
     * @param strDate (��ʽ yyyy/MM/dd)
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
     * ��ȡ���ĸ�ʽ��ʱ��
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
