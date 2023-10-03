package com.bacchuserpshop.common.sms;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;


/*
 * 다바다 주문에서 현재일 기준으로 업체별 주문을 체크하여 알람처리함.
 */
public class SmsSend {

	private static final String LOG_TAG = "SmsSend";
	
	String smsmsg = "";
	String destnumber = "";
	
	Context context = null;
	
    // SMS 
    PendingIntent pi = null;   
    SmsManager sms 	 = null;

	//-------------------------------------------------------------------------
	// 폰sms를 이용한  문자 보내기.
	public SmsSend(Context ctx) { 
		this.context 	 = ctx;
		
        sms = SmsManager.getDefault();
        pi  = PendingIntent.getActivity(this.context, 0,  new Intent(), PendingIntent.FLAG_IMMUTABLE);
	} 
	
	// AS접수 : AS접수확인자 메시지 전송
	public void AsRecpt_SmsAsRecptCellNo_MsgSend(String smsmsg, String destnumber) {
		  
		Log.i(LOG_TAG, "== SMS AsRecpt_SmsAsRecptCellNo_MsgSend() getSms_as_recpt_cell_no() => " + destnumber + ", smsmsg => " + smsmsg);
		
		if (!destnumber.equals("")) {
            // 삼우FID[02-502-2994] 놀부 거래처 AS(수리)가 접수되었습니다.
            sms.sendTextMessage(destnumber.replace("-", ""), null, smsmsg, pi, null);
		}		
	}
	
	// AS접수 : 매출처 대표자  메시지 전송
	public void AsRecpt_UpSmsReprsCellNo_MsgSend(String smsmsg, String destnumber) {
		  
		Log.i(LOG_TAG, "== SMS AsRecpt_UpSmsReprsCellNo_MsgSend() getUp_sms_reprs_cell_no() => " + destnumber + ", smsmsg => " + smsmsg);
		
		if (!destnumber.equals("")) {
            // 삼우FID[02-502-2994] 놀부 거래처 AS(수리)가 접수되었습니다.
            sms.sendTextMessage(destnumber.replace("-", ""), null, smsmsg, pi, null);
		}		
	}
	  
	// AS접수 : AS납품처 대표자  메시지 전송
	public void AsRecpt_SmsReprsCellNo_MsgSend(String smsmsg, String destnumber) {
		  
		Log.i(LOG_TAG, "== SMS AsRecpt_SmsReprsCellNo_MsgSend() getSms_reprs_cell_no => " + destnumber + ", smsmsg => " + smsmsg);
		
		if (!destnumber.equals("")) {
            // 삼우FID[02-502-2994] 놀부 거래처 AS(수리)가 접수되었습니다.
            sms.sendTextMessage(destnumber.replace("-", ""), null, smsmsg, pi, null);
		}		
	}
	
	
	// AS요청 : AS접수후 AS담당자요청  메시지 전송
	public void AsReq_AsChrgrPhoneNo_MsgSend(String smsmsg, String destnumber) {
		  
		Log.i(LOG_TAG, "== SMS AsReq_AsChrgrPhoneNo_MsgSend() getAs_chrgr_phone_no() => " + destnumber + ", smsmsg => " + smsmsg);
		
		if (!destnumber.equals("")) {
            // 삼우FID[02-502-2994] 놀부 거래처 AS(수리)가 접수되었습니다.
            sms.sendTextMessage(destnumber.replace("-", ""), null, smsmsg, pi, null);
		}		
	}
	
	
	// AS수신 : 승인자담당자  승인요청 메시지 전송
	public void AsRecv_ConfmrPhoneNo_MsgSend(String smsmsg, String destnumber) {
		  
		Log.i(LOG_TAG, "== SMS AsRecv_ConfmrPhoneNo_MsgSend() getConfmr_phone_no() => " + destnumber + ", smsmsg => " + smsmsg);
		
		if (!destnumber.equals("")) {
            // 삼우FID[02-502-2994] 놀부 거래처 AS(수리)가 승인요청 되었습니다.
            sms.sendTextMessage(destnumber.replace("-", ""), null, smsmsg, pi, null);
		}		
	}
	
	
	// AS승인 : AS승인완료후 AS담당자  메시지 전송
	public void AsAppr_AsChrgrPhoneNO_MsgSend(String smsmsg, String destnumber) {
		  
		Log.i(LOG_TAG, "== SMS AsAppr_AsChrgrPhoneNO_MsgSend() destnumber => " + destnumber + ", smsmsg => " + smsmsg);
		
		if (!destnumber.equals("")) {
            // 삼우FID[02-502-2994] 놀부 거래처 AS(수리)가 접수되었습니다.
            sms.sendTextMessage(destnumber.replace("-", ""), null, smsmsg, pi, null);
		}		
	}
	
	// AS승인 : AS승인완료후 매출처 대표자  메시지 전송
	public void AsAppr_UpSmsReprsCellNo_MsgSend(String smsmsg, String destnumber) {
		  
		Log.i(LOG_TAG, "== SMS AsAppr_UpSmsReprsCellNo_MsgSend() getUp_sms_reprs_cell_no() => " + destnumber + ", smsmsg => " + smsmsg);
		
		if (!destnumber.equals("")) {
            // 삼우FID[02-502-2994] 놀부 거래처 AS(수리)가 접수되었습니다.
            sms.sendTextMessage(destnumber.replace("-", ""), null, smsmsg, pi, null);
		}		
	}
  
}
