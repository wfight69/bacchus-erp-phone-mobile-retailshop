package com.bacchuserpshop.common.sms;

import android.content.Context;
import android.content.Intent;


/*
 * 다바다 주문에서 현재일 기준으로 업체별 주문을 체크하여 알람처리함.
 */
public class SmsSendThread extends Thread {

	String smsmsg = "";
	String destnumber1 = "";
	
	Context ctx = null;
	
	public SmsSendThread(String smsmsg, String destnumber1) { 
		this.smsmsg 	 = smsmsg;
		this.destnumber1 = destnumber1;
	} 
		
	//-------------------------------------------------------------------------
	// 폰sms를 이용한  문자 보내기.
	public void run()  
	{	
		try
		{
			Intent int_sent 		= new Intent(Intent.ACTION_VIEW);
			
            //PendingIntent pi = PendingIntent.getActivities(this, 0,  new Intent(this, this.ctx), 0); 
			//PendingIntent pi = PendingIntent.getActivities(this, 0,  int_sent, 0);  
			//PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, int_sent, 0);
			
           // SmsManager sms = SmsManager.getDefault();
            //sms.sendTextMessage(this.destnumber1, null, this.smsmsg, pi, null);    
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		} 
	}
  
		
    ///
	public static void main(String[] args) throws Exception {
	
		Context context = null;
		String smsmsg = "다바다 자산관리 Thread 테스트합니다.";
		String destnumber = "010-2270-9085";
			
		// LG070이용한 SMS전송
		SmsSendThread davada = new SmsSendThread( smsmsg, destnumber);
		davada.start();
	}
}
