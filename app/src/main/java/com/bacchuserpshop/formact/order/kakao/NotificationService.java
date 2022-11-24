package com.bacchuserpshop.formact.order.kakao;

import com.bacchuserpshop.R;

import com.bacchuserpshop.common.vo.RetailOrderVo;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.bacchuserpshop.common.net.HttpService;
import com.bacchuserpshop.common.util.ConfigUtils;
import com.google.gson.Gson;

import java.util.StringTokenizer;
import java.util.regex.PatternSyntaxException;

public class NotificationService extends NotificationListenerService {

    private String LOG_TAG = "NotificationService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "[davada] onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "[davada] onDestroy()");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i("NotificationListener-onNotificationPosted","ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());

        String packageName = sbn.getPackageName();
        if (!TextUtils.isEmpty(packageName) &&
                (packageName.equals("com.kakao.talk") || packageName.equals("com.google.android.apps.messaging")) ) {

            Log.i("NotificationListener", "[davada] ID() - " + sbn.getId());
            Log.i("NotificationListener", "[davada] onNotificationPosted() - " + sbn.toString());
            Log.i("NotificationListener", "[davada] PackageName:" + sbn.getPackageName());
            Log.i("NotificationListener", "[davada] PostTime:" + sbn.getPostTime());

            Notification notificatin = sbn.getNotification();
            Bundle extras = notificatin.extras;
            String title = extras.getString(Notification.EXTRA_TITLE);
            int smallIconRes = extras.getInt(Notification.EXTRA_SMALL_ICON);
            Bitmap largeIcon = ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
            CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
            CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);

            Log.i("NotificationListener", "[davada] Title:" + title);
            Log.i("NotificationListener", "[davada] Text:" + text);
            Log.i("NotificationListener", "[davada] Sub Text:" + subText);
            //
            if (title != null) {
                // 제목(카톡이름) + 보낸메시지(실제메시지)
                String kakao_msg = title + "\t" +  text;

                Log.i(LOG_TAG, "[davada] kakao_msg:" + kakao_msg);

                //if (title.trim().equals("주연공주") || title.trim().equals("Yuec 유은철팀장님")) {
                    // 알람확인
                    //Toast.makeText(this, kakao_msg, Toast.LENGTH_SHORT).show();
                //}

                ///////////////////////////////////
                // SMS수신시 서버전송여부(관리자 페이지 처리함)
                String mobile_sms_send_yn = ConfigUtils.getMobileSmsSendYN(this.getApplicationContext());
                String remove_talk_msg = "";
                if(mobile_sms_send_yn.equals("Y")) {
                    String  talkId  = title;
                    String  talkMsg = text.toString();

                    // 특수문자  및 제외단어 제거 처리
                    remove_talk_msg = doRemoveSpecialChar(talkMsg, this.getApplicationContext());

                    // 주문체널방식
                    String retailOrderChannel = "KAKAO";                             // SMS, KAKAO
                    //
                    if (packageName.equals("com.google.android.apps.messaging")) {
                        retailOrderChannel = "SMS";
                    } else if (packageName.equals("com.kakao.talk")) {
                        retailOrderChannel = "KAKAO";
                    }
                    // 카카오톡 전송 스레트
                    KkoTalkSendThread kkoTalkThread = new KkoTalkSendThread(this.getApplicationContext(), retailOrderChannel, talkId, remove_talk_msg);
                    kkoTalkThread.start();
                }
                Log.i(LOG_TAG, "= NotificationListener: doKakaoReceive() mobile_sms_send_yn ==> " +  mobile_sms_send_yn + ", remove_talk_msg ==> " +  remove_talk_msg);
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(LOG_TAG,"= onNotificationRemoved ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
    }


    // 특수문자 제거
    private String doRemoveSpecialChar(String smsMsg, Context context) {

        String smsSaveMsg = "";
        String remove_msg = "";
        String []filter_word = {"\\?", "\\~", "\\!", "\\@", "\\#", "\\$", "\\%", "\\^", "\\&", "\\*", "\\?", "\\'", "\\:", "\\;", "\\n", "\\r"};
        for(int i=0;i<filter_word.length;i++){
            remove_msg = smsMsg.replaceAll(filter_word[i],"");
            smsMsg = remove_msg;
        }
        smsSaveMsg = remove_msg;		// 특수문자 제외한 실제 db에 저장될 문자열

        // 예외필터 문자 환경설정 가져옴
        String except_filter  = ConfigUtils.getMsg_ExceptFilter(context) + ",";			// 예외 필터 문자열

        Log.i(LOG_TAG, "== doRemoveSpecialChar() except_filter ==> " +  except_filter);

        // 특수문자 제거 임시 문자열 임시저장
        String tmp_msg = remove_msg;

        StringTokenizer tokenizer = new StringTokenizer(except_filter, ",");
        String tokenMsg = null;
        while(tokenizer.hasMoreTokens()) {
            tokenMsg = tokenizer.nextToken();
            remove_msg 	= tmp_msg.replaceAll(tokenMsg.trim(),"");
            tmp_msg 	= remove_msg;
            tokenMsg 	= "";
        }
        Log.i(LOG_TAG, "== doRemoveSpecialChar() smsMsg ==> " +  smsMsg + ", remove_msg => " + remove_msg + ", smsSaveMsg => " + smsSaveMsg);

        return remove_msg;
    }

    /** 카카오톡 메시지 전처리함 */
    private class KkoTalkSendThread extends Thread {

        Context  context    = null;
        private String packageName = null;
        private String retailOrderChannel = "KAKAO";        // 주문체널 : SMS, KAKAO
        private String retailOrderTelephone = null;          // 주문번호 OR 명 :주연공주, yuec 유은철팀장님.
        private String orderDescription = null;              // 주문내용

        private Boolean isOrdrFilterExist	= true;        // 기본 필터체크 없을시는 무조건 전송함

        KkoTalkSendThread(Context context, String retailOrderChannel, String talkId, String talkMsg) {
            this.context = context;
            this.retailOrderChannel = retailOrderChannel;
            this.retailOrderTelephone = talkId;
            this.orderDescription = talkMsg;
        }
        //
        public void run() {

            try {
                // 환경설정 기본정보 정의함
                String wholesalerUuid   = ConfigUtils.getWholesalerUuid(this.context);
                String employeeUuid = ConfigUtils.getEmployeeUuid(this.context);
                String entprs_cd   = ConfigUtils.getEntprs_cd(this.context);
                String isPlayFile  = ConfigUtils.getSmsPlayFile(this.context);	// cfg_sms_ring_file=>  "/mnt/sdcard/bacchus_erp_sms/alarm/davdaa_sms_ring.mp3";
                String jwtToken = ConfigUtils.getJwtToken(this.context);

                // 내폰번호(주문접수번호) 가져오기
                String salesman_phone_no = ConfigUtils.getCell_Tel_No(this.context);
                String ordr_msg_filter_yn   = "N";								// 필터문자열 처리여무(Default is N, 미분류 주문처리 경우는 Y)

                // 로그인한 사용자에 대한 필터 가능여부 체크(Y=True, N:False)
                if (ConfigUtils.getFilterUseYN(context)) {
                    isOrdrFilterExist = doMsgFilterCheck(this.orderDescription, this.context);
                }
                Log.i(LOG_TAG, "== KkoTalkSendThread  isOrdrFilterExist => " + isOrdrFilterExist.toString());

                // 1. 필터가 있으면 무조건 전송
                if (isOrdrFilterExist) {
                    ///////////////////////////////////////////////////////
                    // 주문객체구성
                    RetailOrderVo orderVo = new RetailOrderVo();
                    orderVo.setWholesalerUuid(wholesalerUuid);
                    orderVo.setEmployeeUuid(employeeUuid);
                    orderVo.setRetailOrderTelephone(this.retailOrderTelephone);
                    orderVo.setRetailOrderChannel(this.retailOrderChannel);
                    orderVo.setOrderDescription(this.orderDescription);

                    // 데이타 생성
                    Gson gson = new Gson();
                    String jsonString   = gson.toJson(orderVo);

                    Log.i(LOG_TAG, "== KkoTalkSendThread start... jsonString => " + jsonString);
                    Log.i(LOG_TAG, "== KkoTalkSendThread start... jwtToken => " + jwtToken);

                    String conn_server 	= ConfigUtils.getConnServer(context);
                    String subUrl 	= context.getResources().getString(R.string.url_request_orders);
                    //String conn_server 	= "192.168.25.14:8081";
                    //String subUrl 	= "/v1/request-orders";
                    String connUrl = "http://" + conn_server + ":8081" + subUrl;

                    Log.i(LOG_TAG, "== KkoTalkSendThread SmsReceiveService() doSendPost() " + connUrl);

                    // 토큰설정
                    HttpService http = new HttpService(jwtToken);
                    Boolean isSend 	= http.doSendPost(connUrl, jsonString);
                    jsonString = http.getJsonString();
                    if (!isSend) {
                        Log.i(LOG_TAG, "== KkoTalkSendThread SmsReceiveService() doSendPost() " + connUrl +  " Fail...." + http.getJsonString() );
                    } else {
                        Log.i(LOG_TAG, "== KkoTalkSendThread SmsReceiveService() doSendPost() " + connUrl +  " Success.." + http.getJsonString() );
                    }

                    /*
                    // 데이타 파싱
                    Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
                    Map<String, Object> map = gson.fromJson(jsonString, typeOfMap);

                    Log.i(LOG_TAG, "errcode 	= " + map.get("errcode").toString());
                    Log.i(LOG_TAG, "errmsg 	= " + map.get("errmsg").toString());

                    if (map.get("errcode").toString().equals("-1")) return false;
                    */
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    // 환경변수에 문자열 매칭 처리함
    // 1. 전화번호 있으면서 필터있으면  무조건 전송  --> 전송
    // 2. 전화번호 없으면서 필터가 있고, 숫자가 있어 --> 전송
    // 3. 전화번호 없으면서 필터가 있고, 숫자가 없어 --> 미전송
     * 	Boolean isOrdrPhoneExist 	= false;
        Boolean isOrdrFilterExist	= false;
        Boolean isOrdrNumberExist	= false;	// 숫자 1~9 체크
    */
    private boolean doMsgFilterCheck(String smsMsg, Context context)
    {
        boolean isOrdrFilterExist = false;
        boolean isOrdrNumberExist = false;
        boolean isOrdrMsgExist  = false;        // 주문메시지 존재여부
        String tmpMsgFilter = "";
        try
        {
            tmpMsgFilter  = ConfigUtils.getMsg_Filter(context) + ",";			// 필터 문자열

            Log.i(LOG_TAG, "== doMsgFilterCheck() msg => " + smsMsg + ", tmpMsgFilter => " + tmpMsgFilter + "===");

            ///////////////////////
            Integer isFilterCnt = 0;

            // 필터를 두번 체크하여 주문 상품을 골라낸다.
            String tmpFilter[] = { "", ""};

            // 필터문자 체크
            StringTokenizer tokenizer = new StringTokenizer(tmpMsgFilter, ",");
            String tokenMsg = null;
            while(tokenizer.hasMoreTokens()) {
                tokenMsg = tokenizer.nextToken();

                //Log.i(LOG_TAG, "== doMsgFilterCheck() tokenMsg => " + tokenMsg.trim() + ", tokenMsg.length ==> " + tokenMsg.length() );

                //  davada 라는 문자열을 검색 => msg.matches(".*davada.*")
                if (smsMsg.matches(".*"+tokenMsg.trim()+".*")) {
                    tmpFilter[isFilterCnt++] = tokenMsg.trim();	// 필터메시지를 넣는다.
                    isOrdrFilterExist = true;

                    // 두번필터링 처리 위함.
                    if (isFilterCnt >= 2) break;
                }
                tokenMsg = "";
            }
            //Log.i(LOG_TAG, "== doMsgFilterCheck() isOrdrFilterExist => " + isOrdrFilterExist.toString());
            Log.i(LOG_TAG, "== doMsgFilterCheck() isFilterCnt ==> " + Integer.toString(isFilterCnt) + ", tmpFilter[0] => " + tmpFilter[0] + ", tmpFilter[1] => " + tmpFilter[1]);

            // 필터가 존재하나  1자리 이거나 숫자 처리시
            if (isOrdrFilterExist) {

                // 주문 숫자체크
                for (int i=1; i < 10; i++) {
                    if (smsMsg.matches(".*"+ Integer.toString(i).trim()+".*")) {
                        isOrdrNumberExist = true;
                        break;
                    }
                }
                // 필터가 존재하구 숫자가 없으면. 문자 숫자처리 요망
                // 하나,두개,둘,세게,셋,넷,내게
                if (!isOrdrNumberExist) {
                    // s 라는 한글숫자 문자열 배열 선언과 초기화
                    String s[] = { "하나", "한개", "둘", "두개", "셋", "세개", "넷", "네개", "다섯", "여섯", "일곱", "여덟", "아홉", "열", "상자", "박스"};

                    for (int i = 0; i < s.length; i++) {
                        if (smsMsg.matches(".*"+ s[i].trim()+".*")) {
                            isOrdrNumberExist = true;
                            break;
                        }
                    }
                }
            }

            /////////////////////
            // 필터가 존재하고(isOrdrFilterExist=true) and 숫자및 문자숫자(하나,둘등 : isOrdrNumberExist=true) 존재경우
            if( isOrdrFilterExist && isOrdrNumberExist ) {
                isOrdrMsgExist = true;
            }

        } catch (PatternSyntaxException e) {
            System.err.println(e);
        }
        return isOrdrMsgExist;
    }
}