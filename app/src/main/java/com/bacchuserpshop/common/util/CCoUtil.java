package com.bacchuserpshop.common.util;

import java.text.*;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

/**
 * <PRE>
 * Filename : CCoUtil.java
 * Class    : CCoUtil
 * Function : utility
 * Comment  : utility
 * </PRE>
 * @version   1.0
 */

/**
 * <PRE>
 * Function List
 *
 *---------------------------------*
 *-------- 문자변환 처리 -----------*
 *---------------------------------*
 * public static String toDB(String str)   : "8859_1" character set 문자열을 Database character set 문자열로 변환
 * public static String fromDB(String str) : 데이타베이스로부터 얻은 문자열을 "8859_1" 문자열로 변환
 * public static String DB2Local(String str) : 데이타베이스로부터 얻은 문자열을 Local chracter set 문자열로 변환
 * public static String Local2Uni(String localStr) : Local character set 문자열을 "8859_1" character set 문자열로 변환
 * public static String Uni2Local(String unicodeStr) : "8859_1" character set 문자열을 Local character set 문자열로 변환
 *
 * public static String enCode(String str)   : "8859_1" character set 문자열을 "KSC5601" 문자열로 변환
 * public static String toKorean(String str) : 문자열을 "8859_1" 문자열로 변환
 *
 *---------------------------------*
 *-------- 날짜정보 처리 -----------*
 *---------------------------------*
 * public static String getLocalDate() : 현재(System TimeZone 및 Locale 기준 ) 날짜정보를 얻는다.
 * public static String getLocalDateTime() : 현재(System TimeZone 및 Locale 기준 ) 날짜/시간정보를 얻는다.
 * public static String getGMTDate() : GMT 날짜정보를 얻는다.
 * public static String getGMTDateTime() : GMT 기준 날짜/시간정보를 얻는다.
 * protected static String makeTowDigit(int num) : 숫자를 문자열로 변환하는데, 2자리수 미만이면 두자리수로 맞춘다.
 * public static long getTimeInterval(String dateCompare, String dateCompared, int type) : 두 날짜의 간격을 계산하여 리턴함.
 * public static String getLocalDateTime(String format) : 현재(한국기준) 시간정보를 얻는다.
 * public static String getDatewithSpan(String date, long Day) : 입력한 날짜 기준으로 몇일 전,후
 * public static String getDatewithSpan(long day) : 현재 시간을 기준으로 몇일후 시간, 시간의 형태는 (yyyy-mm-dd)
 * public static String getKSTDate() : 현재(한국기준) 날짜정보를 얻는다.
 * public static String getKSTDateTime() : 현재(한국기준) 날짜시간정보를 얻는다.
 * public static String makeDateType(String dateString) : 날짜문자열을 날짜표시타입으로 변환한다.
 * public static String makeDateType2(String dateString) : 날짜문자열을 날짜표시타입으로 변환한다.
 * public static String makeDateTimeType(String dateString) : 날짜문자열을 informix DATETIME형태로 변환
 * public static String convertDateType(String dateString) : 날짜문자열을 날짜표시타입으로 변환한다.
 * public static String convertDateType2(String dateString) : 날짜문자열을 날짜표시타입으로 변환한다.
 *
 *------------------------------------*
 *-------- 금액관련처리 처리 ----------*
 *------------------------------------*
 * public static String makeMoneyType(String moneyString, String delimeter) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeMoneyType(String moneyString) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeMoneyType(int intMoneyString, String delimeter) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeMoneyType(int intMoneyString) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeMoneyType(long longMoneyString, String delimeter) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeMoneyType(long longMoneyString) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeUsMoneyType(double dblMoneyString, String delimeter) : 금액문자열(double)을 금액표시타입으로 변환한다.
 * public static String makeUsMoneyType(double dblMoneyString) : 금액문자열(double)을 금액표시타입으로 변환한다.
 * public static String makeMoneyType(String moneyString, String delimeter, int offset) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeMoneyType(int intMoneyString, String delimeter, int offset) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeMoneyType(long longMoneyString, String delimeter, int offset) : 금액문자열을 금액표시타입으로 변환한다.
 * public static String makeNoMoneyType(String moneyString, String delimeter) : 금액표시타입을 금액문자열로 변환한다.
 *
 *------------------------------------*
 *-------- 문자열관련처리 처리 --------*
 *------------------------------------*
 * public static int stoi(String str) : String을 int값으로 변환한다.
 * public static String itos(int i) : int값을 String으로 변환한다.
 * public static long stol(String str) : String을 long값으로 변환한다.
 * public static String ltos(long l) : long값을 String으로 변환한다.
 * public static String quote(String str) : 문자열을 받아서 단일 따옴표로 감싸 반환한다.
 * public static String convertRN(String str, int  nFlag) : 문자열을 받아서 Enter Key를 특정문자열(`)로 변환하거나 특정문자열을 Enter key로 변환함.
 * public static String[] getToken(String src, String token) : string token 반환
 * public static String[][] getTokenArray(String src, String token, String subtoken) : 이중배열에 string token구하기
 * public static String[] getToken(String src, String token, int idx) : 원하는 길이 만큼의 string token구하기
 * public static byte[] getStringFormat(String Src, String Chr, int Loc, int Len) : 스트링의 왼쪽 혹은 오른쪽에 원하는 문자를 채워, 원하는 길이만큼 byte[]로 변환 해서 리턴한다.
 * public static String makeString(String srcString, int iLength) : 원하는 길이만큼 소스스트링을 반복하여 문자열 생성
 * 
 * public static String chkNull(String srcString, boolean bEdit) : 문자열을 받아서 NULL체크하여 True이면서 NULL경우 ""을 반환한다.
 * public static String chkNull(String srcString) : 문자열을 받아서 NULL체크하여 NULL경우 ""을 반환한다.
 * public static String replace(String sSource, String sOld, String sNew) : 원본문자열에서 특정문자열(sOld)을 해당문자열(sNew)로 대체한다.
 * public static String remove(String str, char tok) : 원본문자열에서 특정문자를 제거한다.
 *
 *-------------------------------------------*
 *------ 주민/사업자번호/전화번호체크 및 화일관련처리 ------*
 *-------------------------------------------*
 * public static String makeResidNoType(String residNoString) : 주민번호 문자열을 주민번호표시타입으로 변환한다.
 * public static String makeBusiNoType(String busiNoString) : 사업자번호 문자열을 사업자번호표시타입으로 변환한다.
 * public static int	makeTelNoType(String telNoString) : 전화번호 문자열 정상여부를 체크한다.
 * public static String getConvertFileName(String src, String des) : 파일의 이름을 변환
 * public static void saveAsFileName(String src, String des) throws Exception : 다른 이름으로 저장
 *
 * public static void printStackTrace(PrintWriter out, Exception e) : exception message를 browser에  나타낸다.
 * public static void printAlertNoBack(PrintWriter out, String str) : exception 발생시에 alert 메세지를 보여준다.
 *
 * public static String parseQueryString(String queryString, String indexParam) : query string을 parsing한다.
 * public static String parsePostString(String queryString, String indexParam) : post string을 parsing한다.
 * public static String getNextStringTypePK(String prefix, String pk) : String타입 Primary Key에 대한 다음 Primary Key를 구한다.
 * public static String getNextStringTypePK(String pk) : String타입 Primary Key에 대한 다음 Primary Key를 구한다.
 * <PRE>
 * 
 * public static boolean isMobilePhoneCheck(String phone_no) : 모바일번호등록 체크를 한다.
 * 
 */

public class CCoUtil
{
    protected static CCoUtil m_instance;

    /**
     * <pre>
     * singleton 클래스 생성자. <BR>
     * </pre>
     *
     * @param    none
     * @return   none
     */
    protected CCoUtil()
    {
        m_instance = null;
    }

    /**
     * <pre>
     * CCoUtil object의 Instance 반환. <br>
     * init을 통해 CCoUtil이 초기화 된 후에 호출되어야 함.
     * </pre>
     *
     * @param    none
     * @return   CCoUtil의 object reference
     * @exception Exception CCoUtil Object의 Instance 획득 실패
     */
    public static CCoUtil getInstance() throws Exception
    {
        if (m_instance == null)
        {
            throw new Exception("System is not initialized properly: "+"CCoUtil instance is null");
        }

        return m_instance;
    }

    /**
     * <PRE>
     * CCoUtil의 내부 변수를 초기화 시켜주는 함수. <br>
     * CCoUtil 객체 생성(Singleton).
     * </PRE>
     *
     * @param    none
     * @return   none
     * @exception   Exception   CCoUtil 객체 생성 실패
     */
    public static void init() throws Exception
    {
        if (m_instance == null)
        {
            m_instance = new CCoUtil();
        }
    }


    /**
     * <pre>
     * "8859_1" character set 문자열을 Database character set 문자열로 변환
     * </pre>
     *
     * @param   str         "8859_1" character set의 문자열
     * @return  newString   Database character set으로 변환된 문자열
     */
    public static String toDB(String str)
    {
        String dbCharSet = new String();

        try
        {
            //dbCharSet = CCoPropertyManager.getInstance().getProperty("nafis.db.charset");
            dbCharSet = "8859_1";

            if (str == null)
            {
                return null;
            }
            else if (dbCharSet.equals("8859_1"))
            {
                // character set이 동일할경우 변경하지 않는다.
                return str;
            }
            else
            {
                return new String (str.getBytes("8859_1"), dbCharSet);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            return null;
        }
    }


    /**
     * <pre>
     * 데이타베이스로부터 얻은 문자열을 "8859_1" 문자열로 변환
     * </pre>
     *
     * @param   str         DB에서 가져온 문자열
     * @return  newString   "8859_1" character set으로 변환된 문자열
     */
    public static String fromDB(String str)
    {
        String  sysCharSet = new String();
        String  dbCharSet = new String();

        try
        {
            // Database Character set
            //dbCharSet = CCoPropertyManager.getInstance().getProperty("nafis.db.charset");
            dbCharSet = "8859_1";

            if (str == null)
            {
                return null;
            }
            else if (dbCharSet.equals("8859_1"))
            {
                // character set 이 동일할 경우 변환하지 않는다.
                return str;
            }
            else
            {
                // return new String (str.getBytes(dbCharSet), sysCharSet);
                return new String (str.getBytes(dbCharSet), "8859_1");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * <pre>
     * 데이타베이스로부터 얻은 문자열을 Local chracter set 문자열로 변환
     * </pre>
     *
     * @param   str         DB에서 가져온 문자열
     * @return  newString   Local character set으로 변환된 문자열
     */
    public static String DB2Local(String str)
    {
        String  sysCharSet = new String();
        String  dbCharSet = new String();

        try
        {
            // Database Character set
            //dbCharSet = CCoPropertyManager.getInstance().getProperty("nafis.db.charset");
            // System (Local) Character set
            //sysCharSet = CCoPropertyManager.getInstance().getProperty("nafis.system.charset");

            dbCharSet  = "8859_1";
            sysCharSet = "8859_1";

			if (str == null)
            {
                return null;
            }
            else if (dbCharSet.equals(sysCharSet))
            {
                // character set이 동일할 경우 변환하지 않는다.
                return str;
            }
            else
            {
                return new String (str.getBytes(dbCharSet), sysCharSet);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * <pre>
     * Local character set 문자열을 "8859_1" character set 문자열로 변환
     * </pre>
     *
     * @param   localStr    Local character set을 가진 문자열
     * @return  String      "8859_1" character set으로 변환된 문자열
     */
    public static String Local2Uni(String localStr)
    {
        String sysCharSet = new String();

        try
        {
            //sysCharSet = CCoPropertyManager.getInstance().getProperty("nafis.system.charset");
            sysCharSet = "8859_1";
            if (localStr == null)
            {
                return null;
            }
            else if (sysCharSet.equals("8859_1"))
            {
                // character set이 동일할 경우 변환하지 않는다.
                return localStr;
            }
            else
            {
                return new String (localStr.getBytes(sysCharSet), "8859_1");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            return null;
        }

    }

    /**
     * <pre>
     * "8859_1" character set 문자열을 Local character set 문자열로 변환
     * </pre>
     *
     * @param   UnicodeStr  "8859_1" chracter set을 가진 문자열
     * @return  String      Local chracter set 으로 변환된 문자열
     */
    public static String Uni2Local(String unicodeStr)
    {
        String sysCharSet = new String();

        try
        {
           // sysCharSet = CCoPropertyManager.getInstance().getProperty("nafis.system.charset");
            sysCharSet = "8859_1";

            if (unicodeStr == null)
            {
                return null;
            }
            else if (sysCharSet.equals("8859_1"))
            {
                // character set이 동일할 경우 변환하지 않는다.
                return unicodeStr;
            }
            else
            {
                return new String (unicodeStr.getBytes("8859_1"), sysCharSet);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * <pre>
     * "8859_1" character set 문자열을 "KSC5601" 문자열로 변환
     * </pre>
     *
     * @param   Str		"8859_1"  chracter set을 가진 문자열
     * @return  String  "KSC5601" chracter set 으로 변환된 문자열
     */
	public static String enCode(String str) {
		String returnStr = null;
		try{
			returnStr = new String(str.getBytes("8859_1"), "KSC5601");
		} catch(Exception e) {
			returnStr = str;
		}
		return returnStr;
	}

	/**
	 * 한글변환 메소드
	 */
	public static String toKorean(String data){
		if(data==null) return null;
		
		try {
			return new String(data.getBytes("8859_1"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}


    /**
     * <PRE>
     * 현재(System TimeZone 및 Locale 기준 ) 날짜정보를 얻는다.
     * </PRE>
     *
     * @param    none
     * @return   String "yyyyMMdd" 형태의 스트링을 반환한다.
     */
    public static String getLocalDate()
    {
        Calendar calLocal = Calendar.getInstance();
        return "" + calLocal.get(Calendar.YEAR)
                + makeTowDigit(calLocal.get(Calendar.MONTH) + 1)
                + makeTowDigit(calLocal.get(Calendar.DATE));
    }

    /**
     * <PRE>
     * 현재(System TimeZone 및 Locale 기준 ) 날짜/시간정보를 얻는다.
     * </PRE>
     *
     * @param    none
     * @return   String "yyyyMMddHHmmss" 형태의 스트링을 반환한다.
     */
    public static String getLocalDateTime()
    {
        Calendar calLocal = Calendar.getInstance();
        return "" + calLocal.get(Calendar.YEAR)
                + makeTowDigit(calLocal.get(Calendar.MONTH) + 1)
                + makeTowDigit(calLocal.get(Calendar.DATE))
                + makeTowDigit(calLocal.get(Calendar.HOUR_OF_DAY))
                + makeTowDigit(calLocal.get(Calendar.MINUTE))
                + makeTowDigit(calLocal.get(Calendar.SECOND));
    }

    /**
     * <PRE>
     * GMT 날짜정보를 얻는다.
     * </PRE>
     *
     * @param    none
     * @return   String "yyyyMMdd" 형태의 스트링을 반환한다.
     */
    public static String getGMTDate()
    {
        Calendar calGMT = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00"));
        return "" + calGMT.get(Calendar.YEAR)
                + makeTowDigit(calGMT.get(Calendar.MONTH) + 1)
                + makeTowDigit(calGMT.get(Calendar.DATE));
    }

    /**
     * <PRE>
     * GMT 기준 날짜/시간정보를 얻는다.
     * </PRE>
     *
     * @param    none
     * @return   String "yyyyMMddHHmmss" 형태의 스트링을 반환한다.
     */
    public static String getGMTDateTime()
    {
        Calendar calGMT = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00"));
        return "" + calGMT.get(Calendar.YEAR)
                + makeTowDigit(calGMT.get(Calendar.MONTH) + 1)
                + makeTowDigit(calGMT.get(Calendar.DATE))
                + makeTowDigit(calGMT.get(Calendar.HOUR_OF_DAY))
                + makeTowDigit(calGMT.get(Calendar.MINUTE))
                + makeTowDigit(calGMT.get(Calendar.SECOND));
    }

    /**
     * <PRE>
     * 숫자를 문자열로 변환하는데, 2자리수 미만이면 두자리수로 맞춘다.
     * </PRE>
     *
     * @param    none
     * @return   String "00" 형태의 스트링을 반환한다.
     */
    protected static String makeTowDigit(int num)
    {
        return (num < 10 ? "0" : "") + num;
    }

    /**
     * <PRE>
     * 두 날짜의 간격을 계산하여 리턴함.
     * </PRE>
     *
     * @param   dateCompare String  비교할 날짜 스트링(yyyyMMddHHmmss) - 기준이 됨
     * @param   dateCompared    String 비교될 날짜 스트링(yyyyMMddHHmmss)
     * @param   type    int return value 의 유형
     * @            1. Calendar.DATE
     * @            2. Calendar.HOUR
     * @            3. Calendar.MINUTE
     * @            4. Calendar.SECOND
     * @            5. Calendar.MILLISECOND
     * @return   long   두 날짜 사이의 시간 간격
     */
    public static long getTimeInterval(String dateCompare, String dateCompared, int type)
    {
        if(dateCompare.length() < 14 || dateCompared.length() < 14) return 0;

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal.set(Integer.parseInt(dateCompare.substring(0, 4)),
                Integer.parseInt(dateCompare.substring(4, 6)),
                Integer.parseInt(dateCompare.substring(6, 8)),
                Integer.parseInt(dateCompare.substring(8, 10)),
                Integer.parseInt(dateCompare.substring(10, 12)),
                Integer.parseInt(dateCompare.substring(12, 14)) );
        cal2.set(Integer.parseInt(dateCompared.substring(0, 4)),
                Integer.parseInt(dateCompared.substring(4, 6)),
                Integer.parseInt(dateCompared.substring(6, 8)),
                Integer.parseInt(dateCompared.substring(8, 10)),
                Integer.parseInt(dateCompared.substring(10, 12)),
                Integer.parseInt(dateCompared.substring(12, 14)) );

        Date dt = cal.getTime();
        Date dt2 = cal2.getTime();

        long interval = dt.getTime() - dt2.getTime();

        switch (type)
        {
            case Calendar.MILLISECOND:
                break;
            case Calendar.SECOND:
                interval = interval / 1000;
                break;
            case Calendar.MINUTE:
                interval = interval / 1000 / 60;
                break;
            case Calendar.HOUR:
                interval = interval / 1000 / 60 / 60;
                break;
            case Calendar.DATE:
                interval = interval / 1000 / 60 / 60 / 24;
                break;
        }

        return interval;
    }


    /**
     * <PRE>
     * 현재(한국기준) 시간정보를 얻는다. <br>
     *  (ex1) format string "yyyyMMddhh" : return value is 1998121011 (0~23 hour type). <br>
     *  (ex2) format string "yyyyMMddHHmmss" : return value is 19990114232121
     * </PRE>
     *
     * @param    format      time format
     * @return   formatted   현재(한국기준) 시간
     */
    public static String getLocalDateTime(String format)
    {
        SimpleDateFormat fmt= new SimpleDateFormat(format);

        long time = System.currentTimeMillis();
        String strTime = fmt.format(new java.util.Date(time));

        return strTime;
    }

    /**
     * <PRE>
     * 입력한 날짜 기준으로 몇일 전,후
     * (주의)입력날짜는 구분자가 없는 string형
     * </PRE>
     *
     * @param   date    string date (19991002)
     * @param   Day     기준이 되는 시간
     * @return  String
     */

    public static String getDatewithSpan(String date, long Day)
    {
        int millisPerHour = 60 * 60 * 1000;
        SimpleDateFormat fmt= new SimpleDateFormat("yyyy-MM-dd");
        SimpleTimeZone timeZone = new SimpleTimeZone(9*millisPerHour,"KST");
        fmt.setTimeZone(timeZone);

        int year = Integer.valueOf(date.substring(0,4)).intValue();
        int month = Integer.valueOf(date.substring(4,6)).intValue();
        int day = Integer.valueOf(date.substring(6,8)).intValue();

        java.util.Calendar aCal = java.util.Calendar.getInstance();
        java.util.Calendar bCal = java.util.Calendar.getInstance();

        aCal.set(year,month,day);
        bCal.set(1970,1,1);

        aCal.add(java.util.Calendar.MONTH, -1);
        bCal.add(java.util.Calendar.MONTH, -1);

        java.util.Date aDay = aCal.getTime();
        java.util.Date bDay = bCal.getTime();

        // java.util.Date aDay = new java.util.Date(year,month,day);
        // java.util.Date bDay = new java.util.Date(1970,1,1);
        java.util.Date cDay = new java.util.Date(aDay.getTime() - bDay.getTime() + (Day*(24*millisPerHour)));

        return fmt.format(cDay);
    }

    /**
     * <pre>
     * 현재 시간을 기준으로 몇일후 시간, 시간의 형태는 (yyyy-mm-dd)
     * </pre>
     *
     * @param   day     더하려는 날짜
     * @return  현재 시간에 입력 시간을 더한 DATE형 시간 "yyyy-MM-dd"
     */
    public static String getDatewithSpan(long day)
    {
        int millisPerHour = 60 * 60 * 1000;
        SimpleDateFormat fmt= new SimpleDateFormat("yyyy-MM-dd");
        SimpleTimeZone timeZone = new SimpleTimeZone(9*millisPerHour,"KST");
        fmt.setTimeZone(timeZone);

        long time = System.currentTimeMillis();
        long span = (24*millisPerHour) * day;    // 하루에 대한 millisecond...
        long time2 = time + span;

        return fmt.format(new java.util.Date(time2));
    }

    /**
     * <PRE>
     * 현재(한국기준) 날짜정보를 얻는다.
     * </PRE>
     *
     * @param   none
     * @return  yyyy-mm-dd형태의 현재 한국시간을 String object로 리턴.
     */
    public static String getKSTDate()
    {
        int millisPerHour = 60 * 60 * 1000;
        SimpleTimeZone pdt = new SimpleTimeZone(9*millisPerHour,"KST");

        // Format the current time.
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyy-MM-dd");
        Date currentTime = new Date();

        return fmt.format(currentTime);
    }

    /**
     * <PRE>
     * 현재(한국기준) 날짜시간정보를 얻는다.
     * </PRE>
     *
     * @param    none
     * @return   yyyy-mm-dd hh:mm:ss 형태의 현재 한국시간을 String object로 리턴.
     */
    public static String getKSTDateTime()
    {
        int millisPerHour = 60 * 60 * 1000;
        SimpleTimeZone pdt = new SimpleTimeZone(9*millisPerHour,"KST");

        // Format the current time.
        SimpleDateFormat formatter  = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        Date currentTime = new Date();

        return formatter.format(currentTime);
    }

    /**
     * <pre>
     * 날짜문자열을 날짜표시타입으로 변환한다. <BR>
     * (예) 19981210 --> 1998-12-10     delimeter(-)        <BR>
     * </pre>
     *
     * @param    dateString  날짜문자열 구분자 존재하지 않는 숫자로만 구성된 날짜 (yyyymmdd)
     * @return   변경된 날짜 문자열(구분자가 첨가된 날짜 형태) (yyyy-mm-dd)
     */
    public static String makeDateType(String dateString)
    {
        if (dateString.length() == 0 || dateString == null) return "";
        if (dateString.length() != 8) return "invalid length";

        String year = dateString.substring(0,4);
        String month = dateString.substring(4,6);
        String date = dateString.substring(6,8);

        return (year+"-"+month+"-"+date);
    }


    /**
     * <pre>
     * 날짜문자열을 날짜표시타입으로 변환한다. <BR>
     * 문자열이 8자 이상인 경우도 처리해준다.
     * (예) 1998-12-10  --> 19981210     delimeter(-)        <BR>
     * </pre>
     *
     * @param    dateString  날짜문자열 구분자 존재하는 구성된 날짜 (yyyy-mm-dd)
     * @return   변경된 날짜 문자열(구분자가 첨가된 날짜 형태) (yyyymmdd)
     */
    public static String makeDateType2(String dateString)
    {
        if (dateString.length() == 0 || dateString == null) return "";

        String year = dateString.substring(0,4);
        String month = dateString.substring(5,7);
        String date = dateString.substring(8,10);

        return (year+month+date);
    }


    /**
     * <pre>
     * 주민번호 문자열을 주민번호표시타입으로 변환한다. <BR>
     * (예) 1234567890123 --> 123456-7890123     delimeter(-)        <BR>
     * </pre>
     *
     * @param    residNoString  주민번호 문자열, 구분자 존재하지 않는 숫자로만 구성된 주민번호 (1234567890123)
     * @return   변경된 주민번호 문자열(구분자가 첨가된 주민번호 형태) (123456-7890123)
     */
    public static String makeResidNoType(String residNoString)
    {
        if (residNoString.length() == 0 || residNoString == null) return "";
        if (residNoString.length() != 13) return "invalid length";

        String res1 = residNoString.substring(0,6);
        String res2 = residNoString.substring(6,13);

        return (res1+"-"+res2);
    }


    /**
     * <pre>
     * 사업자번호 문자열을 사업자번호표시타입으로 변환한다. <BR>
     * (예) 1231212345 --> 123-12-12345 delimeter(-)        <BR>
     * </pre>
     *
     * @param    busiNoString  사업자번호 문자열, 구분자 존재하지 않는 숫자로만 구성된 사업자번호 (1231212345)
     * @return   변경된 사업자번호 문자열(구분자가 첨가된 사업자번호 형태) (123-12-12345)
     */
    public static String makeBusiNoType(String busiNoString)
    {
        if (busiNoString.length() == 0 || busiNoString == null) return "";
        if (busiNoString.length() != 10) return "invalid length";

        String busi1 = busiNoString.substring(0,3);
        String busi2 = busiNoString.substring(3,5);
        String busi3 = busiNoString.substring(5,10);

        return (busi1+"-"+busi2+"-"+busi3);
    }


    /**
     * <pre>
     * 날짜문자열을 informix DATETIME형태로 변환<BR>
     * (예) 19981210101010 --> 1998-12-10 10:10:10<BR>
     * </pre>
     *
     * @param    dateString  날짜문자열 구분작 존재하지 않는 숫자로만 구성된 날짜 (yyyymmddhhmmss)
     * @return   변경된 날짜 문자열.(구분자가 첨가된 날짜 형태)(yyyy-mm-dd hh:mm:ss)
     */
    public static String makeDateTimeType(String dateString)
    {
        if (dateString.length() == 0) return "";
        if (dateString.length() != 14) return "invalid length";

        String year     = dateString.substring(0,4);
        String month    = dateString.substring(4,6);
        String date     = dateString.substring(6,8);
        String hour     =dateString.substring(8,10);
        String min      =dateString.substring(10,12);
        String sec      =dateString.substring(12,14);

        return (year+"-"+month+"-"+date+" "+hour+":"+min+":"+sec);
    }

    /**
     * <pre>
     * 날짜문자열을 날짜표시타입으로 변환한다. <BR>
     * (예) 1998-12-10 --> 12/10/1999         <BR>
     * </pre>
     *
     * @param    dateString  날짜문자열 구분자 존재하지 않는 숫자로만 구성된 날짜 (yyyy-MM-dd)
     * @return   변경된 날짜 문자열.(구분자가 첨가된 날짜 형태) (MM/dd/yyyy)
     */
    public static String convertDateType(String dateString)
    {
        if (dateString.length() == 0 || dateString == null) return "";
        if (dateString.length() != 10) return "invalid length";

        String year = dateString.substring(0,4);
        String month = dateString.substring(5,7);
        String date = dateString.substring(8,10);

        return (month+"/"+date+"/"+year);
    }

    /**
     * <pre>
     * 날짜문자열을 날짜표시타입으로 변환한다. <BR>
     * (예) yyyyMMdd --> Month dd, yyyy       <BR>
     * </pre>
     *
     * @param    dateString  날짜문자열 구분작 존재하지 않는 숫자로만 구성된 날짜 (yyyyMMdd)
     * @return   변경된 날짜 문자열(Month dd, yyyy)
     */
    public static String convertDateType2(String dateString)
    {
        if (dateString == null || dateString.length() == 0) return "";
        if (dateString.length() < 8) return "invalid length";

        String year = dateString.substring(0,4);
        String month = dateString.substring(4,6);
        String date = dateString.substring(6,8);

        StringBuffer sb = new StringBuffer();

        switch(Integer.parseInt(month))
        {
            case 1:
                sb.append("January");
                break;
            case 2:
                sb.append("February");
                break;
            case 3:
                sb.append("March");
                break;
            case 4:
                sb.append("April");
                break;
            case 5:
                sb.append("May");
                break;
            case 6:
                sb.append("June");
                break;
            case 7:
                sb.append("July");
                break;
            case 8:
                sb.append("August");
                break;
            case 9:
                sb.append("September");
                break;
            case 10:
                sb.append("October");
                break;
            case 11:
                sb.append("November");
                break;
            case 12:
                sb.append("December");
                break;
            default:
                sb.append("None");
                break;
        }

        sb.append(" ").append(date).append(", ").append(year);
        return sb.toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * </pre>
     *
     * @param    moneyString 금액문자열.
     * @param    delimeter   금액표시구분자.
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(String moneyString, String delimeter)
    {
        if ( moneyString == null || moneyString.length() == 0 ) return "0";

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(delimeter.charAt(0));
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Long.parseLong(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * delemeter를 주지 않는 경우 처리         <BR>
     * </pre>
     *
     * @param    moneyString 금액문자열.
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(String moneyString)
    {
        if ( moneyString == null || moneyString.length() == 0 ) return "0";

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(',');
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Long.parseLong(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * </pre>
     *
     * @param    moneyString 금액 (정수형).
     * @param    delimeter   금액표시구분자.
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(int intMoneyString, String delimeter)
    {
        String moneyString = new Integer(intMoneyString).toString();

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(delimeter.charAt(0));
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Long.parseLong(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * delemeter를 주지 않는 경우 처리         <BR>
     * </pre>
     *
     * @param    moneyString 금액 (정수형).
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(int intMoneyString)
    {
        String moneyString = new Integer(intMoneyString).toString();

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(',');
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Long.parseLong(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * </pre>
     *
     * @param    moneyString 금액 (long형).
     * @param    delimeter   금액표시구분자.
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(long longMoneyString, String delimeter)
    {
        String moneyString = new Long(longMoneyString).toString();

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(delimeter.charAt(0));
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Long.parseLong(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * delemeter를 주지 않는 경우 처리         <BR>
     * </pre>
     *
     * @param    moneyString 금액 (long형).
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(long longMoneyString)
    {
        String moneyString = new Long(longMoneyString).toString();

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(',');
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Long.parseLong(moneyString))).toString();
    }


    /**
     * <pre>
     * 금액(double형)을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678.1 --> 12,345,678.10           <BR>
     * </pre>
     *
     * @param    moneyString 금액 (double형).
     * @param    delimeter   금액표시구분자.
     * @return   변경된 금액 문자열.
     */
    public static String makeUsMoneyType(double dblMoneyString, String delimeter)
    {
        String moneyString = new Double(dblMoneyString).toString();

        String format = "#,###.00";
        DecimalFormat df = new DecimalFormat(format);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(delimeter.charAt(0));
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Double.parseDouble(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액(String형)을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678.1 --> 12,345,678.10           <BR>
     * </pre>
     *
     * @param    moneyString 금액 (String형).
     * @param    delimeter   금액표시구분자.
     * @return   변경된 금액 문자열.
     */
    public static String makeUsMoneyType(String moneyString, String delimeter)
    {
        String format = "#,###.00";
        DecimalFormat df = new DecimalFormat(format);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(delimeter.charAt(0));
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Double.parseDouble(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액(double)을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678.1 --> 12,345,678.10         <BR>
     * delemeter를 주지 않는 경우 처리           <BR>
     * </pre>
     *
     * @param    moneyString 금액 (double형).
     * @return   변경된 금액 문자열.
     */
    public static String makeUsMoneyType(double dblMoneyString)
    {
        String moneyString = new Double(dblMoneyString).toString();

        String format = "#,###.00";
        DecimalFormat df = new DecimalFormat(format);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(',');
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Double.parseDouble(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액(String)을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678.1 --> 12,345,678.10         <BR>
     * delemeter를 주지 않는 경우 처리           <BR>
     * </pre>
     *
     * @param    moneyString 금액 (String형).
     * @return   변경된 금액 문자열.
     */
    public static String makeUsMoneyType(String moneyString)
    {
        String format = "#,###.00";
        DecimalFormat df = new DecimalFormat(format);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(',');
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Double.parseDouble(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * </pre>
     *
     * @param    moneyString 금액문자열 (String).
     * @param    delimeter   금액표시구분자.
     * @param    offset      금액표시구분 간격.
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(String moneyString, String delimeter, int offset)
    {
        if ( moneyString == null || moneyString.length() == 0 ) return "0";

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(delimeter.charAt(0));
        df.setGroupingSize(offset);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Double.parseDouble(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * </pre>
     *
     * @param    moneyString 금액 (정수형).
     * @param    delimeter   금액표시구분자.
     * @param    offset      금액표시구분 간격
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(int intMoneyString, String delimeter, int offset)
    {
        String moneyString = new Integer(intMoneyString).toString();

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(delimeter.charAt(0));
        df.setGroupingSize(offset);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Double.parseDouble(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액문자열을 금액표시타입으로 변환한다. <BR>
     * (예) 12345678 --> 12,345,678            <BR>
     * </pre>
     *
     * @param    moneyString 금액 (long형).
     * @param    delimeter   금액표시구분자.
     * @param    offset      금액표시구분 간격
     * @return   변경된 금액 문자열.
     */
    public static String makeMoneyType(long longMoneyString, String delimeter, int offset)
    {
        String moneyString = new Long(longMoneyString).toString();

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(delimeter.charAt(0));
        df.setGroupingSize(offset);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Double.parseDouble(moneyString))).toString();
    }

    /**
     * <pre>
     * 금액표시타입을 금액문자열로 변환한다. <BR>
     * (예) 12,345,678 --> 12345678          <BR>
     * </pre>
     *
     * @param    moneyString 금액표시문자열.
     * @param    delimeter   금액표시구분자.
     * @return   금액문자열.
     */
    public static String makeNoMoneyType(String moneyString, String delimeter)
    {
        if ( moneyString == null || moneyString.length() == 0 ) return "0";

        StringTokenizer st = new StringTokenizer(moneyString,delimeter);
        String out = "";
        String temp = null;
        while (st.hasMoreTokens())
        {
            temp = st.nextToken();
            out = out + temp;
        }
        return out;
    }

    /**
     * <pre>
     * query string을 parsing한다.             <BR>
     * </pre>
     *
     * @param    queryString queryString.
     * @param    indexParam  parsing하고자 하는 인덱스문자열.
     * @return   인덱스문자열에 의해 parsing된 value값.
     */
    public static String parseQueryString(String queryString, String indexParam)
    {
        int start;
        int howLong;

        queryString += "&";                 // 끝표시 추가
        indexParam += "=";                  // '='추가
        start = queryString.indexOf(indexParam, 0); // 원하는 indexParam의 value 첫 위치를 알아낸다.

        if (start == -1) return "";

        start += indexParam.length();
        howLong = queryString.indexOf("&", start);  // value의 길이를 알아낸다.

        if (howLong < (start + 1))  return "";          // last value==NULL

        String temp = queryString.substring(start, howLong);
        int idx = temp.indexOf("%20");

        while (idx != -1)
        {
            temp = temp.substring(0,idx) +" "+ temp.substring(idx+3,temp.length());
            idx = temp.indexOf("%20");
        }

        return temp;
    }

    /**
     * <pre>
     * post string을 parsing한다.              <BR>
     * </pre>
     *
     * @param    queryString poststring.
     * @param    indexParam  parsing하고자 하는 인덱스문자열.
     * @return   인덱스문자열에 의해 parsing된 value값.
     */
    public static String parsePostString(String queryString, String indexParam)
    {
        int start;
        int howLong;

        queryString += "&";                             // 끝표시 추가
        indexParam += "=";                              // '='추가
        start = queryString.indexOf(indexParam, 0);     // 원하는 indexParam의 value 첫 위치를 알아낸다.

        if (start == -1) return "";

        start += indexParam.length();
        howLong = queryString.indexOf("&", start);      // value의 길이를 알아낸다.

        if (howLong < (start + 1)) return "";           // last value==NULL

        return queryString.substring(start, howLong);   // value를 알아낸다.
    }

    /**
     * <pre>
     * String을 int값으로 변환한다.           <BR>
     * </pre>
     *
     * @param    str     int값으로 변환될 String문자열.
     * @return   변환된 int 값.
     */
    public static int stoi(String str)
    {
	    try
	    {
			if (str == null) return 0;
			return (Integer.valueOf(str).intValue());
 	    }
	    catch (Exception ex)
	    {
			return 0;
	    }
    }

    /**
     * <pre>
     * int값을 String으로 변환한다.           <BR>
     * </pre>
     *
     * @param    i   String으로 변환될 int 값.
     * @return   변환된 String 값.
     */
    public static String itos(int i)
    {
        return (new Integer(i).toString());
    }

    /**
     * <pre>
     * String을 long값으로 변환한다.           <BR>
     * </pre>
     *
     * @param    str     long값으로 변환될 String문자열.
     * @return   변환된 long 값.
     */
    public static long stol(String str)
    {
        if (str == null || str == "") return 0;
        return (Long.valueOf(str).longValue());
    }

    /**
     * <pre>
     * long값을 String으로 변환한다.           <BR>
     * </pre>
     *
     * @param    l   String으로 변환될 long 값.
     * @return   변환된 String 값.
     */
    public static String ltos(long l)
    {
        return (new Long(l).toString());
    }


    /**
     * <pre>
     * exception message를 browser에  나타낸다.              <BR>
     * </pre>
     *
     * @param    out     servlet ui class의 PrintWriter object
     * @param    e       servlet ui class의 exception object
     * @return   none
     */
     public static void printStackTrace(PrintWriter out, Exception e)
    {
        out.println("<H3>");
        out.println("Exception<BR>");
        out.println("</H3><PRE>");
        e.printStackTrace(out);
        out.println("</PRE>");
    }

    /**
     * <pre>
     * exception 발생시에 alert 메세지를 보여준다.           <BR>
     *          화면이 이전으로 돌아가지 않는다.                      <BR>
     * </pre>
     *
     * @param    out     PrintWriter object
     * @param    str     cct exception message
     * @return   none
     */

    public static void printAlertNoBack(PrintWriter out, String str)
    {
        out.println("<HTML>\n");
        out.println("<HEAD>\n");
        out.println("<SCRIPT LANGUAGE = \"JavaScript\"> ");
        //out.println("    history.back()     ");
        //out.println("    alert(\""+e.getMessage()+"\")");

        if (str == null)
        {
            str = "Servlet UI Class Exception \\n \\n Please Check Your Code ";
        }

        out.println("    alert(\""+str+"\")");
        out.println("</SCRIPT>");
        out.println("</HEAD>\n");
        out.println("<BODY>");
        out.println("<H3>");
        out.println("Error<BR>");
        out.println("</H3><PRE>");
        out.println("CctException : "+ str);
        out.println("</PRE></BODY>\n");
        out.println("</HTML>\n");
    }


    /**
     * <pre>
     * 문자열을 받아서 단일 따옴표로 감싸 반환한다.
     * - SQL문을 생성시 사용키 위함.
     * </pre>
     *
     * @param   String
     * @return  'String'
     */
    public static String quote(String str)
    {
        if (str == null)
        {
            return "''";
        }
        else
        {
            return "'" + str + "'" ;
        }
    }

    /**
     * <pre>
     * 문자열을 받아서 Enter Key를 특정문자열(`)로 변환하거나
     * 특정문자열을 Enter key로 변환함...
     * - Informix thin driver Bug 때문에 SQL문을 생성시 사용키 위함.
     * </pre>
     *
     * @param   String      변환 대상
     * @param   nFlag       변환 방향
     * @return  'String'
     */
    public static String convertRN(String str, int  nFlag)
    {
        String  strTemp = "";
        if (str == null)
        {
            return "";
        }
        else
        {
            if (nFlag > 0)  //양수이면 Enter Key를 `로 변환
            {
                StringTokenizer sb = new StringTokenizer(str, "\r\n");

                while (sb.hasMoreTokens())
                {
                    strTemp += sb.nextToken() + "``";
                }
                return strTemp;
            }
            else            //음수이면 `를 Enter Key로 변환
            {
                StringTokenizer sb = new StringTokenizer(str, "``");

                while (sb.hasMoreTokens())
                {
                    strTemp += sb.nextToken() + "\r\n";
                }

                //str = str.replace('¶', '\r' );
                //str = str.replace('†', '\n' );
            }
        }
        return  strTemp;
    }


    /**
     * <pre>
     * 파일의 이름을 변환
     * </pre>
     *
     * @param src 파일명
     * @param des 변환 파일명
     * @return 변환된 파일명
    */
    public static String getConvertFileName(String src, String des)
    {
        if(src==null || src.length()<=0) return "";
        if(des==null || des.length()<=0) return src;

        int idx = src.lastIndexOf(File.separatorChar);

        String fileDir  = src.substring(0, idx);
        String fileName = "";
        String ext = "";

        idx = src.lastIndexOf(".");
        if(idx != -1)   ext = src.substring(idx);

        idx = des.lastIndexOf(".");
        if(idx != -1)
        {
            return fileDir + File.separatorChar + des;
        }
        else
        {
            return fileDir + File.separatorChar + des + ext;
        }
    }

    public static void saveAsFileName(String src, String des) throws Exception
    {
        try{

           if(! new File(src).renameTo(new File(des)) ) throw new Exception();

        }catch(Exception e){throw new Exception();}
    }



    /**
     * <pre>
     * string token 반환
     * </pre>
     *
     * @param src   String Source
     * @param token token
     * @return toekn 배열
    */
    public static String[] getToken(String src, String token)
    {
        if(src == null || src.length()<0) src = "";
        if(token == null || token.length()<0) token = "";

        StringTokenizer sb = new StringTokenizer(src, token);

        String[] rtn  = new String[sb.countTokens()];
        for(int i=0; sb.hasMoreTokens(); i++)
        {
            rtn[i] = sb.nextToken();
        }
        return rtn;
    }


    /**
     * <pre>
     * 원하는 길이 만큼의 string token구하기
     * </pre>
     *
     * @param src   String Source
     * @param token token
     * @param idx 리턴 token 크기
     * @return toekn 배열
    */
    public static String[] getToken(String src, String token, int idx)
    {
        if(src == null || src.length()<0) src = "";
        if(token == null || token.length()<0) token = "";

        StringTokenizer sb = new StringTokenizer(src, token);

        String[] rtn  = new String[idx];
        int cntToken  = sb.countTokens();
        for(int i=0; i<rtn.length; i++)
        {
            if(i >= cntToken)
            {
                rtn[i] = "";
            }
            else
            {
                rtn[i] = sb.nextToken();
            }
        }
        return rtn;
    }

	/**
	  * <pre>
	  * string token 반환
	  * </pre>
	  *
	  * @param src   String Source
	  * @param token token, subtoken 상세토큰
	  * @return toekn 이중배열 
	  * add by kjc 2004. 3. 5일
	 */
	public static String[][] getTokenArray(String src, String token, String subtoken)
	{
		if(src == null || src.length()<0) src = "";
		if(token == null || token.length()<0) token = "";
		if(subtoken == null || subtoken.length()<0) subtoken = "";		

		String str[] = getToken(src, token);
		StringTokenizer sb = new StringTokenizer(str[0], subtoken);

		String[][] rtn  = new String[str.length][sb.countTokens()];
		for(int i=0; i < str.length; i++)
		{
			StringTokenizer sbx = new StringTokenizer(str[i], subtoken);
			for(int j=0; sbx.hasMoreTokens(); j++) {
				rtn[i][j] = sbx.nextToken();
			}
		}
		return rtn; 
	}	 	

	
	/////////////////////////////////////////////////////////
	//// 문자열 처리
	/////////////////////////////////////////////////////////
	// 토큰에 해당하는 배열을 넘겨준다..
	public static String[] RtnTokenList(String strVal, String token) {
		try { 
			StringTokenizer st = new StringTokenizer(strVal, token );
			String strToken[]  = new String[st.countTokens()];
			int i = 0;
			while ( st.hasMoreTokens() ) {
				strToken[i] = st.nextToken();
				i++;
			}
			
			return strToken;
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return null;
	}	
	
    /**
     * <pre>
     * String타입 Primary Key에 대한 다음 Primary Key를 구한다.
     * </pre>
     *
     * @param Prefix(형태는 EB20010103)
     * @param Prefix를 포함한 Primary Key(형태는 EB20010103+00012)
     * @return Next Primary Key (형태는 Prefix + 00000013)
    */
    public static String getNextStringTypePK(String prefix, String pk)
    {
        try
        {
            if(prefix == null) prefix = "";

            if(pk == null || pk.length()<=prefix.length()) return "";

            String nextPk = new String().valueOf(Long.parseLong(pk.substring(prefix.length())) + 1);
            String tmp = "";

            for(int i=0; i< pk.length()-prefix.length(); i++) tmp += "0";

            return prefix + (tmp + nextPk).substring(nextPk.length());
        }
        catch (Exception e)
        {
            return "Invalid Parameter";
        }

    }

    /**
     * <pre>
     * String타입 Primary Key에 대한 다음 Primary Key를 구한다.
     * </pre>
     *
     * @param Primary Key(형태는 00000012)
     * @return Next Primary Key (형태는 Prefix + 00000013)
    */
    public static String getNextStringTypePK(String pk)
    {
        try{

            if(pk == null || pk.length()<0) return "";

            String nextPk = new String().valueOf(Long.parseLong(pk) + 1);
            String tmp = "";

            for(int i=0; i< pk.length(); i++) tmp += "0";

            return (tmp + nextPk).substring(nextPk.length());
        }
        catch (Exception e)
        {
            return "Invalid Parameter";
        }
    }


    /**
    *   <pre>
    *   스트링의 왼쪽 혹은 오른쪽에 원하는 문자를 채워, 원하는 길이만큼 byte[]로 변환 해서 리턴한다.
    *   </pre>
    *
    *   @param  1.Src   : 소스 스트링
    *           2.Chr   : 채우고자하는 문자 스트링
    *           3.Loc   : Chr을 채울 위치 0=왼쪽, 1=오른쪽
    *           4.Len   : 리턴받을 길이
    *   @return byte 배열
    */
    public static byte[] getStringFormat(String Src, String Chr, int Loc, int Len)
    {
        if(Src == null || Chr == null || (Loc != 0 && Loc != 1) || Len == 0) return null;

        if(Src.length() == Len) return Src.substring(0, Len).getBytes();

        if(Src.length() > Len)
        {
            if(Loc == 0){
                return Src.substring(0, Len).getBytes();
            }
            else if(Loc == 1){
                return Src.substring(Src.length() - Len).getBytes();

            }


        }

        String Dest = "";
        String Tmp  = "";
        for(int i=0; i<Len ; i++)  Tmp += Chr;

        if(Loc == 0)
        {
            Dest = Src + Tmp;

            Dest = Dest.substring(0, Len);
        }
        else if(Loc == 1)
        {
            Dest = Tmp + Src;

            Dest = Dest.substring(Src.length());

        }

        return Dest.getBytes();
    }

    /**
    *   <pre>
    *   원하는 길이만큼 소스스트링을 반복하여 문자열 생성
    *   </pre>
    *
    *   @param  srcString  - Strinbg : 소스 스트링
    *           iLength    - int     : 길이
    *   @return convString - String  : 생성 문자열
    */
    public static String makeString(String srcString, int iLength)
    {
        String convString = "";

        for(int i=0; i < iLength; i++)
        {
            convString += srcString;
        }

        return convString;
    }


	/** null을 체크하여 빈문자열이나 &nbsp;로 변환 */
	public static String chkNull(String str, boolean bEdit)
	{
		if (str == null) 
		{
			if (bEdit) return "";
			else return "&nbsp;";
		}
		else return str;
	} 

	public static String chkNull(String str)
	{
		try {
			if (str == null || str.equals("")) 
			{
				return "";
			}
			else return str;		
		} catch (Exception e) {
			return "";
		}
	} 

	public static String chkNull(String str, String tgt_str)
	{
		try {
			if (str == null || str.equals("")) 
			{
				return tgt_str;
			}
			else return str;		
		} catch (Exception e) {
			return tgt_str;
		}
	} 
	

    /**
    *   <pre>
    *   문자열에서 특정 문자열 치환
    *   </pre>
    *
    *   @param  sSource  - Strinbg : 소스 스트링
    *           sOld     - Strinbg : 대체되어질 문자열
    *           sNew     - Strinbg : 대체할     문자열
    *   @return sTarget  - String  : 생성 문자열
    */
	public static String replace(String sSource, String sOld, String sNew) throws Exception
	{
		String sTarget = sSource;
		String sOldStr = sOld;
		String sNewStr = sNew;
		int nPos, nOffset=0;
		
		if (sOldStr == null || sOldStr.length() == 0)	return sTarget;
		if (sNewStr == null)	sNewStr = "";
		
		while ((nPos = (sTarget.substring(nOffset, sTarget.length())).indexOf(sOldStr)) > -1)
		{
			sTarget = sTarget.substring(0, nOffset + nPos) + sNewStr + sTarget.substring(nOffset + nPos + sOldStr.length(), sTarget.length());
			nOffset = nOffset + nPos + sNewStr.length();
		}
		
		return sTarget;
	}


	/**
    *   <pre>
    *   문자열에서 특정 문자제거
    *   </pre>
    *
    *   @param  str		- Strinbg : 소스 스트링
    *           tok		- Strinbg : 대체되어질 문자
    *   @return sResult - String  : 생성 문자열
    */

	/** 문자열에서 특정 문자제거 */
	public static String remove(String str, char tok) throws Exception
	{ 
		String sResult="";
		if (str == null) return sResult;		
		
		for(int i = 0; i < str.length(); i++) 
		{
			if(str.charAt(i) != tok) sResult = sResult + str.charAt(i); 
		} 
		return sResult;
	}



	/**
	 * 자바스크립트의 alert 기능을 리턴해 주는 메소드 
	 */
	public static String scriptAlert(String alertContent) {
		String alert = " <script language='javascript'> alert('"+ alertContent +"'); </script> ";
		return alert;
	}
	
	public static String scriptAlert(String alertContent, String error) {
		String alert = " <script language='javascript'> alert('"+ alertContent + "\" \"" + error + "'); </script> ";
		return alert;
	}


	/**
	 * 실행명령에 내용을 리턴해 주는 메소드 
	 */
	public static String[] commandExecute(String winCmd, String unixCmd[], String token) 
	{
		Runtime runtime				= Runtime.getRuntime();
        Process process				= null;
		InputStream standardInput	= null;
        InputStream standardError	= null;

		BufferedReader inputBReader	= null;
		BufferedReader errorBReader	= null;

		StringBuffer stdinput		= new StringBuffer();
        StringBuffer stderror		= new StringBuffer();
        try {
			if (winCmd != null)
			{
				process = runtime.exec(winCmd);
			} else {
				process = runtime.exec(unixCmd);
			}
			//int exitVal = process.waitFor();

			standardInput	= process.getInputStream();	
			standardError	= process.getErrorStream();

			inputBReader		= new BufferedReader(new InputStreamReader(standardInput));
			errorBReader		= new BufferedReader(new InputStreamReader(standardError));

			String line = null;
			while ( (line = inputBReader.readLine()) != null) {
				stdinput.append(line).append(token);
			}

			line = null;
			while ( (line = errorBReader.readLine()) != null) {
				stderror.append(line).append(token);
			}

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally
		{
			try
			{
				if (standardInput != null) standardInput.close();
				if (standardError != null) standardError.close();
				if (inputBReader != null)  inputBReader.close();
				if (errorBReader != null)  errorBReader.close();
			}
			catch (Exception e) {}
		}

		String rtnMsg[] = new String[2];
		rtnMsg[0] = stdinput.toString();
		rtnMsg[1] = stderror.toString();

		return rtnMsg;
	}

	/**
	 * 리스트에 해당데몬명이 실행되어있는지 여부를  리턴해 주는 메소드 
	 */
	public static boolean isExecChkDaemon(String strRtnCmdMsg, String token, String strDaemonName) 
	{
		try
		{
			String mainToken[] =  getToken(strRtnCmdMsg, token);
			String tmpName	   =  null;
			for(int i=0; i < mainToken.length-2; i++)
			{
				String subToken[]  = getToken(mainToken[i], " ");
				tmpName = subToken[8] + " " + subToken[subToken.length-1];	// VoiceServer + 1
				if (tmpName.trim().equals(strDaemonName))
				{
					return true;
				}
			}
		}
		catch (Exception e)
		{
            e.printStackTrace();
		}
		return false;
	}

	//  전화번호 문자열 정상여부를 체크한다.
    public static int  makeTelNoType(String telNoString) 
	{
		try
		{
			String strTelNo  = remove(telNoString, '-');	// 원본문자열에서 특정문자를 제거한다.
			String strAreaNo = null;

			// 전화번호 길이체크
			if (strTelNo.length() < 9 || strTelNo.length() > 11)
			{
				return 0;
			}

			// 서울(02)이면
			if (strTelNo.substring(0,2).equals("02"))
			{
				strAreaNo = "002";
			} else {
				strAreaNo = strTelNo.substring(0,3);
			}

			// 지역번호 체크시
			if (!isAreaNoCheck(strAreaNo))
			{
				return 0;
			}
		}
		catch (Exception e)
		{
			return 0;		// 전화번호형식 에러
		}

		return 1;
	}

	/** 지역번호 체크 */
	public static boolean isAreaNoCheck(String strAreaNo)
	{
		String[] telAreaNo = {	"002","031","032","033","041","042","043","051",
								"052","053","054","055","061","062","063","064",
								"010","011","016","017","018","019","070" };

		for(int i = 0; i < telAreaNo.length; i++)
		{
			if (strAreaNo.equals(telAreaNo[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	  * 모바일번호 정규식 패턴 검증
	  * @param phone_no
	  * @return
	  */
	 public static boolean isMobilePhoneCheck(String phone_no){
		  boolean okPattern = false;
		  String regex =  "^01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
		  
		  //String regex = "^(01[0|1|6|7|8|9])-(\\d{4}|\\d{3})-(\\d{4})$";
		  
		  okPattern = Pattern.matches(regex, phone_no);
		  return okPattern;
	 }

}//end class
