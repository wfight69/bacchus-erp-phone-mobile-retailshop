package com.bacchuserpshop.common.util;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CLog
{
	private Context context		= null;
	
	private static String logPath = null;		
    private static PrintWriter 	  PWLog;
    private static BufferedWriter BWLog;
    
    // logPath = /mnt/sdcard/bacchus_erp_sms/log/
    public static boolean isWrite(Context ctx)
    {
    	//
    	logPath = ConfigUtils.getFileLogPath(ctx);
    	
    	if(ConfigUtils.getLogFileWriteYN(ctx).equals("Y")) {
    		return true;
    	}
    	return false;
    }
    
    // logPath = /mnt/sdcard/bacchus_erp_sms/log/
    public static synchronized void Write(String strMsg)
    {
    	String errorTime 	= DateUtils.getToday("yyyy-MM-dd HH:mm:ss");
        try
        {
        	String logDate  	= DateUtils.getToday("YYYY-MM-DD");
        	String logFileName  = logPath + logDate + ".txt";				// /mnt/sdcard/bacchus_erp_sms/2016-03.17.log

            PWLog = new PrintWriter(new FileWriter(logFileName, true), true);
            PWLog.println(errorTime + ": " + strMsg);
        }
        catch(Exception e)
        {
        	PWLog.println(errorTime + ": " + e.getMessage());
            //PWLog = new PrintWriter(System.err);
        }
    }
    
    // logPath = /mnt/sdcard/bacchus_erp_sms/log/
    public static synchronized void Write(String logPath, String strMsg)
    {
    	String errorTime 	= DateUtils.getToday("yyyy-MM-dd HH:mm:ss");
        try
        {
        	String logDate  	= DateUtils.getToday("YYYY-MM-DD");
        	String logFileName  = logPath + logDate + ".txt";				// /mnt/sdcard/bacchus_erp_sms/2016-03.17.log

            PWLog = new PrintWriter(new FileWriter(logFileName, true), true);
            PWLog.println(errorTime + ": " + strMsg);
        }
        catch(Exception e)
        {
        	PWLog.println(errorTime + ": " + e.getMessage());
            //PWLog = new PrintWriter(System.err);
        }
    }
    
    // logPath = /mnt/sdcard/bacchus_erp_sms/log/
    public static synchronized void BWFWrite(String logPath, String strMsg)
    {    	
        try
        {
        	String errorTime 	= DateUtils.getToday("yyyy-MM-dd HH:mm:ss");
        	String logDate  	= DateUtils.getToday("YYYY-MM-DD");
        	String logFileName  = logPath + logDate + ".txt";				// /mnt/sdcard/bacchus_erp_sms/2016-03.17.txt
        	
        	BWLog = new BufferedWriter(new FileWriter(logFileName, true));
        	BWLog.write(errorTime + ": " + strMsg);
        	BWLog.flush();
        	BWLog.close();
        }
        catch(Exception e)
        {
        	try {
				BWLog.write(e.getMessage());
				BWLog.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
        } 
    }

}