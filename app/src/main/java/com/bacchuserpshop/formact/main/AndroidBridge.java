package com.bacchuserpshop.formact.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import com.bacchuserpshop.R;
import com.bacchuserpshop.common.net.HttpService;
import com.bacchuserpshop.common.util.ConfigUtils;
import com.bacchuserpshop.common.vo.RetailOrderVo;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class AndroidBridge {

    private String TAG = "AndroidBridge";

    public MyHandler myHandler = new MyHandler();

    final public Handler handler = new Handler();

    // 새성시 내부적으로 사용할 코드들을 저장합니다.
    private WebView mAppView;
    private WebViewMangActivity mContext;

    ProgressDialog progressDialog = null;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // 기본 생성자 입니다.
    public AndroidBridge(WebView _mAppView, WebViewMangActivity _mContext)
    {
        // 입력받은 값들을 저장하도록 합니다.
        mAppView = _mAppView;
        mContext = _mContext;
    }

    // VueJS에서 Create시에 모바일정보(위도,경도,토큰) 가저오는 메소드 입니다.
    @JavascriptInterface
    public void mobileInfo( final String _message ){

        Log.d(TAG, _message);

        String latitude  = ConfigUtils.getLatitude(mContext);     // 위도
        String longitude = ConfigUtils.getLongitude(mContext);    // 경도
        String fcm_token = ConfigUtils.getFcmToken(mContext);     // FCM토큰

        Map<String, Object> map = new HashMap();
        map.put("latitude",  latitude);        // 위도
        map.put("longitude", longitude);      // 경도
        map.put("fcm_token", fcm_token);      // FCM토큰

        // 로그인시 위도,경도, 환경설정 가져오기
        final String loginInfo = new Gson().toJson(map);

        myHandler.post(new Runnable() {
            @Override
            public void run() {
                //mAppView.loadUrl("javascript:alert('["+ _message +"] 라고 로그를 남겼습니다.')");
                mAppView.evaluateJavascript("javascript:receiveMessage(\'" + loginInfo + "\')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d(TAG, value);
                    }
                });
            }
        });
    }

    // SMS/카톡위한 환경변수및 필터메시지 저장 메소드 입니다.
    @JavascriptInterface
    public void loginCfgSave( final String configJsonMessage ){
        Log.d(TAG, configJsonMessage);
        //
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map map = new Gson().fromJson(configJsonMessage, type);

        String wholesalerUuid = map.get("wholesalerUuid").toString();
        String employeeUuid = map.get("employeeUuid").toString();
        String retailShopUuid = map.get("retailShopUuid").toString();
        String retailShopCode = map.get("retailShopCode").toString();
        String jwtToken = map.get("jwtToken").toString();
        //
        ConfigUtils.saveLoginCfg(mContext,  wholesalerUuid,
                                            employeeUuid,
                                            retailShopCode,
                                            retailShopUuid,
                                            jwtToken);

        final String loginInfo = "환경설정저장";
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                mAppView.loadUrl("javascript:receiveMessage(\'"+ loginInfo +"\')");
//                mAppView.evaluateJavascript("javascript:receiveMessage(\'" + loginInfo + "\')", new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String value) {
//                        Log.d(TAG, value);
//                    }
//                });

                // Get the New and Old Fcm Token
                String newFcmToken = ConfigUtils.getFcmToken(mContext);               // FCM토큰
                Log.i(TAG, "== loginCfgSave newFcmToken => " + newFcmToken);

                // FCM메시지 전송위한 토큰및 GPS위치 전송
                new FcmTokenAndGpsSendThread(mContext, newFcmToken).start();
            }
        });
    }


    class MyHandler extends Handler {

        public void handleMessage(Message msg) {
            progressDialog.dismiss();

            String message = msg.getData().getString("message");

            Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();

            //
            //doPayRslt(message);
        }
    }

    ///////////////////////////////////////////
    // GPS위치 및 FCM메시지 전송위한 토큰전송
    public class FcmTokenAndGpsSendThread extends Thread {

        private static final String LOG_TAG = "FcmTokenAndGpsSendThread";
        Context  context    = null;
        String newFcmToken = null;

        public FcmTokenAndGpsSendThread(Context context, String newFcmToken) {
            this.context = context;
            this.newFcmToken = newFcmToken;
        }

        public void run() {

            Log.d(LOG_TAG, "=== GpsTokenService GpsAndFcmTokenSendThread() Start..");

            // 환경설정 기본정보 정의함
            String wholesalerUuid   = ConfigUtils.getWholesalerUuid(this.context);
            String retailShopUuid = ConfigUtils.getRetailShopUuid(this.context);
            String retailShopUserUuid = ConfigUtils.getEmployeeUuid(this.context);

            // Get the Fcm Token
            //String fcmToken = FirebaseInstanceId.getInstance().getToken();

            ///////////////////////////////////////////////////////
            // 디바디스 토큰객체구성
            RetailOrderVo orderVo = new RetailOrderVo();
            orderVo.setFcmToken(newFcmToken);

            // 데이타 생성
            Gson gson = new Gson();
            String jsonString   = gson.toJson(orderVo);

            Log.i(LOG_TAG, "==  start... jsonString => " + jsonString);
            //Log.i(LOG_TAG, "== FcmTokenAndGpsSendThread start... jwtToken => " + jwtToken);

            String conn_server 	= ConfigUtils.getConnServer(context);
            String subUrl 	= context.getResources().getString(R.string.url_retailshopuser);
            // /api/v1/retail-shop-users/retailShopUserUuid
            String connUrl = conn_server + subUrl + retailShopUserUuid;

            Log.i(LOG_TAG, "== FcmTokenAndGpsSendThread doSendPut() " + connUrl);

            // 토큰설정
            HttpService http = new HttpService();
            http.setReq_method("PUT");
            Boolean isSend 	= http.doSendPost(connUrl, jsonString);
            jsonString = http.getJsonString();
            if (!isSend) {
                Log.i(LOG_TAG, "== FcmTokenAndGpsSendThread doSendPut() " + connUrl +  " Fail...." + http.getJsonString() );
            } else {
                Log.i(LOG_TAG, "== FcmTokenAndGpsSendThread doSendPut() " + connUrl +  " Success.." + http.getJsonString() );
            }

        }
    }
}
