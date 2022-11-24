/*****************************************************************************
* 파일명 : DateUtils.java
* 패키지 : com.epc.common.util
* 생성일 : 2010. 07. 07.
* 작성자 : 
* ===========================================================================
* 변경이력:
* DATE             AUTHOR      DESCRIPTION
* ---------------------------------------------------------------------------
* 변경 이력은 이곳에 추가 합니다.
*****************************************************************************/
package com.bacchuserpshop.common.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 날짜 관련 유틸리티
 *  - 기본적으로 Joda Time API(http://joda-time.sourceforge.net)를 사용
 *  - 양력/음력 변환은 IBM 패키지를 사용 (icu4j.jar)
 *
 * @author 
 */

public class DateUtils {

    // 하루(1일)의 Millisecond
    public static final long MILLIS_PER_DAY = 86400000L;

    private DateUtils() {
    }

	private static DateTime getDateTime(String dateStr, String format) {

		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		return fmt.parseDateTime(dateStr);

	}
	
	public static String getOgMuppet() {
	      
	       return "adsf";
	   }

	/**
	 * 날짜를 Long타입으로 리턴한다.
	 *
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return long
	 */
	public static long getMillis(int year,int month,int day) {

		return (new DateTime(year, month, day, 0, 0, 0, 0)).getMillis();
	}

	/**
	 * 날짜를 Long 타입으로 리턴한다.
	 *
	 * @param String dateStr
	 * @param int format
	 * @return String
	 */
	public static long getMillis(String dateStr, String format) {

		return getDateTime(dateStr, format).getMillis();

	}

	/**
	 * 날짜를 Date타입으로 리턴한다.
	 *
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return Date
	 */
	public static Date getDate(int year,int month,int day) {

		return (new DateTime(year, month, day, 0, 0, 0, 0)).toDate();
	}

	/**
	 * 날짜를 Date 타입으로 리턴한다.
	 *
	 * @param String dateStr
	 * @param int format
	 * @return long
	 */
	public static Date getDate(String dateStr, String format) {

		return getDateTime(dateStr, format).toDate();
	}


	/**
	 * 날짜를 Calendar 타입으로 리턴한다.
	 *
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return Calendar
	 */
	public static Calendar getCalendar(int year,int month,int date) {

		return (new DateTime(year, month, date, 0, 0, 0, 0)).toCalendar(null);
	}

	/**
	 * 날짜를 Calendar 타입으로 리턴한다.
	 *
	 * @param String dateStr
	 * @param int format
	 * @return Calendar
	 */
	public static Calendar getCalendar(String dateStr, String format) {

		return getDateTime(dateStr, format).toCalendar(null);
	}

	/**
	 * 시간에 대한 셋팅
	 *
	 * @param String format
	 * @return String
	 */
	public static String getTime(String format) {

		if(format == null || format.length() == 0)
			return "";

		DateTime dt = new DateTime();
		DateTimeFormatter fmt = null;

		if(format.equals("A")) {
			fmt = DateTimeFormat.forPattern("a");
			fmt = fmt.withLocale(Locale.ENGLISH);
		} else if(format.equals("a")) {
			fmt = DateTimeFormat.forPattern("a");
			fmt = fmt.withLocale(Locale.KOREA);
		} else {

			dt = new DateTime();
			format = format.replace("M", "m");
			format = format.replace("S", "s");
			fmt = DateTimeFormat.forPattern(format);

		}

		return fmt.print(dt);
	}

	/**
	 * 오늘날짜 리턴
	 * 예) getToday("yyyy-MM-dd a hh:mm:ss") ==> 2007-06-28 오후 05:37:46
	 * @param String format
	 * @return String
	 */
	public static String getToday(String format) {

		if(format == null || format.length() == 0)
			return "";

		DateTime dt = new DateTime();
		format = format.replace("Y", "y");
		format = format.replace("D", "d");
		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);

		return fmt.print(dt);

	}

	/**
	 * 오늘날짜 리턴(YYYYMMDD)
	 * @return java.lang.String
	 */
	public static String getToday() {

		return getToday("YYYYMMDD");
	}
	
	/**
	 * 문자열 날짜 포멧으로 리턴
	 * 예) getStringToDate("20090902","yyyy-MM-dd") ==> 2009-09-02
	 * @param String date, String format
	 * @return String
	 */
	public static String getStringToDate(String date, String format) {
		if(format == null || format.length() == 0)
			return "";
		
		if(date.length() != 8)
			return date;
		
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(4,6));
		int day = Integer.parseInt(date.substring(6,8));
		
		DateTime dt = new DateTime(year, month, day, 0, 0, 0, 0);
		
		format = format.replace("Y", "y");
		format = format.replace("D", "d");
		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);

		return fmt.print(dt);
	}

	/**
	 * 현재요일을 리턴한다.
	 *
	 * @param Locale locale Locale.ENGLISH, Locale.KOREA
	 * @param boolean flag true = 짤은 요일명, false = 긴 요일명
	 * @return String
	 */
	public static String getWeekText(Locale locale, boolean flag) {

		DateTime dt = new DateTime();
		
		DateTime.Property pDoW = dt.dayOfWeek();
		
		if(flag)
			return pDoW.getAsShortText(locale);
		else
			return pDoW.getAsText(locale);
	}
	
	
    /**
     * 지정된 날짜에 대한 현재요일을 리턴한다.
     * 지정된 날짜의 형식은 반드시 "yyyyMMdd"를 지켜야 한다.
     *
     * @param String date
     * @param Locale locale Locale.ENGLISH, Locale.KOREA
     * @param boolean flag true = 짤은 요일명, false = 긴 요일명
     * @return String
     */
	public static String getWeekText(String date, Locale locale, boolean flag) {
	    DateTime dt = getDateTime(date, "yyyyMMdd");
	    DateTime.Property pDoW = dt.dayOfWeek();
        
        if(flag)
            return pDoW.getAsShortText(locale);
        else
            return pDoW.getAsText(locale);
	} 

	/**
	 * 현재요일을 숫자를 리턴한다.
	 *
	 * @param String date
	 * @return int [1.일 ... 7.토]
	 */
	public static int getWeek(String date) {

		DateTime dt = getDateTime(date, "yyyyMMdd");

		return dt.getDayOfWeek()%7 +1;
	}

	/**
	 * 오늘부터 year, month, day까지의 날짜 수
	 *
	 * @param int year
	 * @param int month
	 * @param int day
	 * @return int
	 */
	public static int daysBetween(int year, int month, int day)	{

		DateTime dt = new DateTime();
		DateTime sdt = new DateTime(year, month, day, 0, 0, 0, 0);

		return Days.daysBetween(sdt.toLocalDateTime(), dt.toLocalDateTime()).getDays();

	}

	 /**
	 * 두 날짜사이의 일수차이 구함
	 *
	 * @param int endDay
	 * @param int startMonth
	 * @param int startDay
	 * @param int endYear
	 * @param int endYear
	 * @param int endYear
	 * @return int
	 */
	public static int daysBetween(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {

		DateTime sdt = new DateTime(startYear, startMonth, startDay, 0, 0, 0, 0);
		DateTime edt = new DateTime(endYear, endMonth, endDay, 0, 0, 0, 0);

		return Days.daysBetween(sdt.toLocalDateTime(), edt.toLocalDateTime()).getDays();
	}

	/**
	 * 오늘부터 year, month, day까지의 날짜 수
	 *
	 * @param String date
	 * @return int
	 */
	public static int daysBetween(String date)  	{

		if(date == null || date.length() != 8)
			return -1;

		return daysBetween(Integer.parseInt(date.substring(0,4)), Integer.parseInt(date.substring(4,6)), Integer.parseInt(date.substring(6)));
	}

	/**
	 * 두 날짜사이의 일수차이 구함
	 *
	 * @param String startDate
	 * @param String endDate
	 * @return int
	 */
	public static int daysBetween(String startDate,String endDate) {

		int year = Integer.parseInt(startDate.substring(0,4));
		int month = Integer.parseInt(startDate.substring(4,6));
		int day =  Integer.parseInt(startDate.substring(6));

		int year2 = Integer.parseInt(endDate.substring(0,4));
		int month2 = Integer.parseInt(endDate.substring(4,6));
		int day2 =  Integer.parseInt(endDate.substring(6));

		return daysBetween(year,month,day,year2,month2,day2);
	}

	/**
	 * 달의 날 수를 계산한다.(해당년 해당월의 말일을 구해서 리턴)
	 * @param int year
	 * @param int month
	 * @return int
	 */
	public static int getMonthDays(int year, int month) {

		DateTime dt = new DateTime(year, month+1, 1, 0, 0, 0, 0);
		dt = dt.minusDays(1);
		return dt.getDayOfMonth();
	}


	/**
	 * 오늘부터 year 만큼의 년을 뺀 날짜 리턴
	 * @param int year
	 * @return int
	 */
	public static String minusYears(int year)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime();
		dt = dt.minusYears(year);
		return fmt.print(dt);
	}
	
	public static String minusYears(int year, String format)	{
		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		DateTime dt = new DateTime();
		dt = dt.minusYears(year);
		return fmt.print(dt);
	}

	/**
	 * 오늘부터 month 만큼의 월을 뺀 날짜 리턴
	 * @param int month
	 * @return int
	 */
	public static String minusMonths(int month)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime();
		dt = dt.minusMonths(month);
		return fmt.print(dt);
	}
	
	/**
	 * 오늘부터 month 만큼의 월을 뺀 날짜 리턴
	 * @param int month
	 * @return int
	 */
	public static String minusMonths(int month, String format)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		DateTime dt = new DateTime();
		dt = dt.minusMonths(month);
		return fmt.print(dt);
	}


	/**
	 * 오늘부터 week 만큼의 주을 뺀 날짜 리턴
	 * @param int week
	 * @return int
	 */
	public static String minusWeeks(int week)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime();
		dt = dt.minusWeeks(week);
		return fmt.print(dt);
	}

	/**
	 * 오늘부터 day 만큼의 날짜를 뺀 날짜 리턴
	 * @param int day
	 * @return int
	 */
	public static String minusDays(int day, String format)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		DateTime dt = new DateTime();
		dt = dt.minusDays(day);
		
		return fmt.print(dt);
	}
	
	/**
	 * 오늘부터 day 만큼의 날짜를 뺀 날짜 리턴
	 * @param int day
	 * @return int
	 */
	public static String minusDays(int day)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime();
		dt = dt.minusDays(day);
		return fmt.print(dt);
	}

	/**
	 * 특정날짜로부터 year 만큼의 년을 뺀 날짜 리턴
	 * @param String date
	 * @param int year
	 * @return int
	 */
	public static String minusYears(String date, int year)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.minusYears(year);
		return fmt.print(dt);
	}

	/**
	 * 특정날짜로부터 month 만큼의 월을 뺀 날짜 리턴
	 * @param String date
	 * @param int month
	 * @return int
	 */
	public static String minusMonths(String date, int month)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.minusMonths(month);
		return fmt.print(dt);
	}

	/**
	 * 특정날짜로부터 week 만큼의 주을 뺀 날짜 리턴
	 * @param String date
	 * @param int week
	 * @return int
	 */
	public static String minusWeeks(String date, int week)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.minusWeeks(week);
		return fmt.print(dt);
	}

	/**
	 * 특정날짜로부터 day 만큼의 날짜를 뺀 날짜 리턴
	 * @param String date
	 * @param int day
	 * @return int
	 */
	public static String minusDays(String date, int day)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.minusDays(day);
		return fmt.print(dt);
	}

	/**
	 * 오늘부터 year 만큼의 년을 더한 날짜 리턴
	 * @param int year
	 * @return int
	 */
	public static String plusYears(int year)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime();
		dt = dt.plusYears(year);
		return fmt.print(dt);
	}

	/**
	 * 오늘부터 month 만큼의 월을 더한 날짜 리턴
	 * @param int month
	 * @return int
	 */
	public static String plusMonths(int month)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime();
		dt = dt.plusMonths(month);
		return fmt.print(dt);
	}

	/**
	 * 오늘부터 week 만큼의 주을 더한 날짜 리턴
	 * @param int week
	 * @return int
	 */
	public static String plusWeeks(int week)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime();
		dt = dt.plusWeeks(week);
		return fmt.print(dt);
	}

	/**
	 * 오늘부터 day 만큼의 날짜를 더한 날짜 리턴
	 * @param int day
	 * @return int
	 */
	public static String plusDays(int day)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime();
		dt = dt.plusDays(day);
		return fmt.print(dt);
	}
	
	/**
	 * 특정날짜로부터 day 만큼의 날짜를 더한 날짜 리턴
	 * @param String date
	 * @param int day
	 * @return int
	 */
	public static String plusDays(String date, int day, String format)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.plusDays(day);
		return fmt.print(dt);
	}
	
	/**
	 * 오늘부터 month 만큼의 월을 더한 날짜 리턴
	 * @param int month
	 * @return int
	 */
	public static String plusMonthDays(String date, int month, String format)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.plusMonths(month);
		return fmt.print(dt);
	}

	/**
	 * 특정날짜로부터 year 만큼의 년을 더한 날짜 리턴
	 * @param String date
	 * @param int year
	 * @return int
	 */
	public static String plusYears(String date, int year)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.plusYears(year);
		return fmt.print(dt);
	}

	/**
	 * 특정날짜로부터 month 만큼의 월을 더한 날짜 리턴
	 * @param String date
	 * @param int year
	 * @return int
	 */
	public static String plusMonths(String date, int month)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.plusMonths(month);
		return fmt.print(dt);
	}

	/**
	 * 특정날짜로부터 week 만큼의 주을 더한 날짜 리턴
	 * @param String date
	 * @param int year
	 * @return int
	 */
	public static String plusWeeks(String date, int week)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.plusWeeks(week);
		return fmt.print(dt);
	}

	/**
	 * 특정날짜로부터 day 만큼의 날짜를 더한 날짜 리턴
	 * @param String date
	 * @param int day
	 * @return int
	 */
	public static String plusDays(String date, int day)	{

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = getDateTime(date, "yyyyMMdd");
		dt = dt.plusDays(day);
		return fmt.print(dt);
	}
	
	/**
	 * 한달전 date
	 * @return
	 */
	public static String getMonthAgoDate(String s_format) {
	     Calendar cal = Calendar.getInstance();
	     cal.add(Calendar.MONTH ,-1); 
	     Date monthago = cal.getTime();
	     SimpleDateFormat formatter = new SimpleDateFormat(s_format, Locale.getDefault());
	     return formatter.format(monthago); 
	
	}
	
	/**
	 * 특정년월의 마지막 날짜를 리턴
	 * @param int year
	 * @param int month
	 * @return int
	 */
	public static int lastDay(int year, int month)	{

		int arrMonth[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		// 윤년
	     if ( (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0) ) {
	        arrMonth[2] = 29;
	     } else {
	        arrMonth[2] = 28;
	     }
	     
	     return arrMonth[month];
	}

    public static void main(String[] args) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = fmt.parseDateTime("20070627");

		fmt = DateTimeFormat.forPattern("HH:mm:ss");
		dt = new DateTime();

		fmt = DateTimeFormat.forPattern("a");
		fmt = fmt.withLocale(Locale.KOREA);

		String format = "YYYY";
		format = format.replace("Y", "y");
		format = format.replace("D", "d");
		fmt = DateTimeFormat.forPattern(format);

		DateTime.Property pDoW = dt.dayOfWeek();

		DateTime tmp = new DateTime(2001, 1, 21, 0, 0, 0, 0);

		tmp = new DateTime(2009, 1, 1, 0, 0, 0, 0);
		tmp = tmp.minusDays(1);

		tmp = new DateTime();
    }

}
