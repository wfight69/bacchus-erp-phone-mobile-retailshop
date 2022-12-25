package com.bacchuserpshop.formact.main;

import com.bacchuserpshop.R;
//
import com.bacchuserpshop.common.util.FileUtils;
import com.bacchuserpshop.common.vo.GpsInfo;
//
import com.bacchuserpshop.formact.login.PopConfigActivity;
import com.bacchuserpshop.formact.login.pems.PermissionsActivity;
import com.bacchuserpshop.formact.login.pems.PermissionsChecker;
import com.bacchuserpshop.formact.main.common.CustomWebChromeClient;
import com.bacchuserpshop.formact.main.common.CustomWebViewClient;

import com.bacchuserpshop.common.dialog.MyProgressDialog;
import com.bacchuserpshop.common.util.ConfigUtils;
//
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.ArrayList;
//import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import me.leolin.shortcutbadger.ShortcutBadger;

public class WebViewMangActivity  extends AppCompatActivity {

    private static final String LOG_TAG = "WebViewMangActivity";

    SharedPreferences pref = null;

    private String cell_tel_no;             // 사용자 휴대폰 번호
    private String fcm_token    = "";       // FCM토큰
    private String latitude     = "0.0";    // 위도
    private String longitude    = "0.0";    // 경도
    private String badgeCount   = "0";

    public WebView webview;
    public MyProgressDialog progressDialog;

    //PermissionChecker를 권한 체크를 위한 값 선언
    private static final int REQUEST_CODE = 0;
    /*요청받은 권한을 설정합니다. 여기선 저장소와 카메라를 설정
     * 	android.Manifest.permission.INTERNET,				// 인터넷
         android.Manifest.permission.WRITE_EXTERNAL_STORAGE,	// SD카드
        android.Manifest.permission.CALL_PHONE,				// 전화
        android.Manifest.permission.READ_PHONE_STATE,
        android.Manifest.permission.READ_PHONE_NUMBERS,		// 번호가져오기-이거안하면 삼성폰 에러 => Android 8.0(oreo) : targetSdkVersion API-28
        android.Manifest.permission.READ_CONTACTS,			// 주소록
        android.Manifest.permission.CAMERA,					// 카메라
        android.Manifest.permission.RECORD_AUDIO,			// 마이크
        android.Manifest.permission.NFC,
        android.Manifest.permission.ACCESS_FINE_LOCATION	// GPS처리
     * */
    private static final String[] PERMISSIONS =
            new String[]{
                    android.Manifest.permission.INTERNET,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.NFC,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.SEND_SMS
            };
    private PermissionsChecker checker;

    //======================================================

    // 파일선택위한 콜벡데이타
    public ValueCallback mFilePathCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_frozen_asview_mang);

        try {
            pref         = getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);

            // 버전 28이상은 환경설정 앱별 권한처리 체크를 하게 해야한다.(저장장치,전화,sms등)
            checker = new PermissionsChecker(this);
            if (checker.lacksPermissions(PERMISSIONS)) {
                // 권한처리 시작
                PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
            } else {
                webview = (WebView)findViewById(R.id.web_frozen_webview);

                // 환경설정 만들기
                configGetandSave(1);

                // 환경설정 저장
                configGetandSave(2);

                String conn_server = ConfigUtils.getConnServer(WebViewMangActivity.this);
                String entprs_cd	= ConfigUtils.getEntprs_cd(WebViewMangActivity.this);

               String connUrl 	= "http://" + conn_server + "/sp/";

                Log.i(LOG_TAG, "== onCreate() start.. => " + connUrl);

                webview.loadUrl(connUrl);

                // 쿠키설정처리
                //setCookieAllow(this.webview);

                // 로딩 -- 에러가 나는데.. 이유가 .
                progressDialog = MyProgressDialog.show(this,"로딩중.","111",true,true,null);

                webview.getSettings().setDefaultTextEncodingName("UTF-8");
                webview.getSettings().setJavaScriptEnabled(true);

                //mWebViewComponent.settings.javaScriptEnabled = true
                //mWebViewComponent.addJavascriptInterface(JSBridge(),"JSBridge")

                webview.getSettings().setBuiltInZoomControls(true);
                webview.getSettings().setMediaPlaybackRequiresUserGesture(false);               // audio 플레이어 자동재생처리
                webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);           // window.open사용시
                webview.getSettings().setSupportMultipleWindows(true);
                //
                webview.getSettings().setDomStorageEnabled(true);
                webview.getSettings().setBlockNetworkImage(false);

                webview.setWebViewClient(new CustomWebViewClient(WebViewMangActivity.this));
                webview.setWebChromeClient(new CustomWebChromeClient(WebViewMangActivity.this));
                /*
                webview.setWebChromeClient(new CustomWebChromeClient(WebViewMangActivity.this) {
                    @Override
                    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)
                    {
                                createWebPrintJob(view);
                });
                */

                // 웹뷰상 파일다운로드 처리위함
                webview.setDownloadListener(new DownloadListener() {
                    @Override
                    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                        try {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                            request.setDestinationInExternalPublicDir(
                                    Environment.DIRECTORY_DOWNLOADS,    //Download folder
                                    "recpt_img");              //Name of file

                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(request);
                            Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e) {
                            if (ContextCompat.checkSelfPermission(WebViewMangActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                if (ActivityCompat.shouldShowRequestPermissionRationale(WebViewMangActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    Toast.makeText(getBaseContext(), "다운로드를 위해\n권한이 필요합니다.", Toast.LENGTH_LONG).show();
                                    ActivityCompat.requestPermissions(WebViewMangActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1004);
                                }
                                else {
                                    Toast.makeText(getBaseContext(), "다운로드를 위해\n권한이 필요합니다.", Toast.LENGTH_LONG).show();
                                    ActivityCompat.requestPermissions(WebViewMangActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1004);
                                }
                            }
                        }
                    }
                });

                // 웹뷰 <--> 웹page 자바스트립트 연결
                AndroidBridge ab = new AndroidBridge(webview, WebViewMangActivity.this );
                webview.addJavascriptInterface( ab, "Android" );
            }

            // NoticeServiceListener 서비스시작
            boolean isPermissionAllowed = isNotiPermissionAllowed();
            if(!isPermissionAllowed) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 알림앱 권한설정처리 체크
    private boolean isNotiPermissionAllowed() {
		Set<String> sets = NotificationManagerCompat.getEnabledListenerPackages(this);
		if (sets != null && sets.contains(getPackageName())) {
			return true;
		} else {
			return false;
		}
    }

    //
    private final static int FILECHOOSER_NORMAL_REQ_CODE = 11;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(LOG_TAG, "== Y_Test_Msg onActivityResult() requestCode => " + Integer.toString(requestCode));

        switch (requestCode) {
            case 1:                                                                     // RFID 태그읽기
                String rslt_msg1 =  data.getStringExtra("tag_json_list");
                //Toast.makeText(getApplicationContext(),"onActivityResult: " + rslt_msg1, Toast.LENGTH_LONG).show();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:doRfidTagReadRslt(" + rslt_msg1 + ")");
                    }
                });
                break;
            case FILECHOOSER_NORMAL_REQ_CODE:                                       // 이미지파일 선택처리 테스트
                //fileChooser 로 파일 선택 후 onActivityResult 에서 결과를 받아 처리함
                if(resultCode == RESULT_OK) {
                    //파일 선택 완료 했을 경우
                    /*
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mFilePathCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                    }else{
                        mFilePathCallback.onReceiveValue(new Uri[]{data.getData()});
                    }
                    */
                    if (data != null) {
                        Uri[]   results = null;
                        ClipData clipData = data.getClipData();

                        //멀티선택 /*
                        if (clipData != null) {
                            results = new Uri[clipData.getItemCount()];
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                ClipData.Item item = clipData.getItemAt(i);
                                results[i] = item.getUri();
                            }
                            mFilePathCallback.onReceiveValue(results);
                        } else if (data.getData() != null) {
                            mFilePathCallback.onReceiveValue(new Uri[]{data.getData()});
                        }
                    }
                    mFilePathCallback = null;
                } else {
                    //cancel 했을 경우
                    if(mFilePathCallback != null) {
                        mFilePathCallback.onReceiveValue(null);
                        mFilePathCallback = null;
                    }
                }
                break;
            default:
                break;
        }
    }

    // 쿠키설정처리
    private void setCookieAllow(WebView mWebview) {
        try {
            mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);        // 쿠키설정처리
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeSessionCookies(new ValueCallback<Boolean>() {
                    @Override
                    public void onReceiveValue(Boolean value) {
                        Log.i(LOG_TAG, "== setCookieAllow() removedSessionCookies..");
                    }
                });
                //webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                cookieManager.setAcceptThirdPartyCookies(mWebview, true);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //boolean result = super.onCreateOptionsMenu(menu);
        //MenuInflater menuInflator = new MenuInflater(this);

        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.saleslogin_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.login_menu_config) {
            PopConfigDialog();
        }

        return false;
    }

    // 환경설정 다이얼로그 처리.
    public void PopConfigDialog(){

        // Acvivity를 이용한 팝업처리
        Intent intent = new Intent(getApplicationContext(), PopConfigActivity.class);
        startActivity(intent);
        //startActivityForResult(intent, 2);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            //finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 자바스크립트 인터페이스 리로드 처리..
    private class JavaScriptInterface {
        private final WebView m_wv;
        public JavaScriptInterface(WebView wv) {
            m_wv = wv;
        }
        public void reload() {
            m_wv.reload();
        }
    }

    // 환경설정내용을 가져오기및 저장한다(cfDiv 1:가져오기, 2:저장하기).
    private void configGetandSave(int cfDiv)
    {
        Log.i(LOG_TAG, "== configGetandSave() start.. cfDiv => " + cfDiv);

        switch(cfDiv) {
            case (1) :
                ///////////////////////////////
                //인스톨 후 맨처음 사용자 id없을시..
                /*
                String config_cell_tel_no = ConfigUtils.getCell_Tel_No(this);
                if (config_cell_tel_no.equals("") || config_cell_tel_no == null) {
                    config_cell_tel_no = ConfigUtils.getMyCellPhoneNo(WebViewMangActivity.this);
                }
                Log.i(LOG_TAG, "== configGetandSave()  config_cell_tel_no => " + config_cell_tel_no);
                */

                /////////////////////////////
                // 맨처음 초기화시  asset 기본sms링화일  음원파일 복사.
                String alarmSrcFile 	= ConfigUtils.getFileAlarmName(WebViewMangActivity.this);			// davada_sms_ring.mp3
                String alarmErrSrcFile 	= ConfigUtils.getFileAlarmErrName(WebViewMangActivity.this);		// davada_err_ring.mp3
                String downPath   		= ConfigUtils.getFileDownPath(WebViewMangActivity.this);			// /mnt/sdcard/bacchus_erp/voice/
                String alarmPath  		= ConfigUtils.getFileAlarmPath(WebViewMangActivity.this);			// /mnt/adcard/bacchus_erp/alarm/
                String imagePath  		= ConfigUtils.getFileImagePath(WebViewMangActivity.this);			// /mnt/adcard/bacchus_erp/image/
                String shinHanPath  	= ConfigUtils.getShinHanBankPath(WebViewMangActivity.this);			// /mnt/adcard/bacchus_erp/bank/88

                // 디렉토리 생성처리
                FileUtils.makeDir(downPath);		// 주문음성화일 다운패스
                FileUtils.makeDir(alarmPath);		// sms알람화일 패스
                FileUtils.makeDir(imagePath);		// 자산이미지화일 패스
                FileUtils.makeDir(shinHanPath);		// 신한뱅크키화일 패스

                Log.i(LOG_TAG, "== configGetandSave() 디렉토리 생성 success.. " + alarmPath);

                // 다바다 주문메시지 => 주문내용이 도착하였습니다.
                String alarmFile  = getResources().getString(R.string.cfg_davada_sms_ring_file);	// /asset/davada_sms_ring.mp3
                String targetFile = alarmPath + alarmFile;											// /davada_salesfrozen/alarm/davada_sms_ring.mp3
                if (!FileUtils.isFileExists(targetFile)) {
                    boolean isMake = ConfigUtils.CopyAssetFromToTargetFile(WebViewMangActivity.this, alarmSrcFile, targetFile);
                    if (isMake) {
                        SharedPreferences.Editor editor = pref.edit();
                        //editor.putString("config_edtxt_delschdule", CONFIG_DELSCHEDULE);
                        editor.putString("config_edtxt_ringfile", targetFile);
                        editor.putString("config_chkbox_davada_sms_ring_yn", "Y");
                        editor.commit();

                        Log.i(LOG_TAG, "== configGetandSave() assets mp3 filecopy success.. ");
                    }
                }

                // 신한은행키복사처리
                String shinhanBankkeyFile  = getResources().getString(R.string.cfg_shinhan_bankkey_file);	// /asset/BankKey.dat
                String targetBankKeyFile = shinHanPath + shinhanBankkeyFile;										// /bacchus_erp/bank/88/BankKey.dat
                if (!FileUtils.isFileExists(targetBankKeyFile)) {
                    boolean isMake = ConfigUtils.CopyAssetFromToTargetFile(WebViewMangActivity.this, shinhanBankkeyFile, targetBankKeyFile);
                    if (isMake) {
                        Log.i(LOG_TAG, "== configGetandSave() assets BankKey filecopy success.. ");
                    }
                }

                fcm_token    = ConfigUtils.getFcmToken(WebViewMangActivity.this);               // FCM토큰
                latitude     = ConfigUtils.getCell_Tel_No(WebViewMangActivity.this);            // 위도
                longitude    = ConfigUtils.getLongitude(WebViewMangActivity.this);              // 경도
                badgeCount  = ConfigUtils.getBadgeCount(WebViewMangActivity.this);

                Log.i(LOG_TAG, "== configGetandSave() fcm_token     ;  " + fcm_token);
                Log.i(LOG_TAG, "== configGetandSave() latitude      ;  " + latitude);
                Log.i(LOG_TAG, "== configGetandSave() longitude     ;  " + longitude);
                Log.i(LOG_TAG, "== configGetandSave() badgeCount    ;  " + badgeCount);

                break;
            case (2) :

                // 위도,경도구하기
                GpsInfo gps = new GpsInfo(WebViewMangActivity.this);
                latitude  =  Double.toString(gps.getLatitude());;     // 위도
                longitude =  Double.toString(gps.getLongitude());;     // 경도

                // Get the token
                fcm_token 	= FirebaseInstanceId.getInstance().getToken();

                Log.i(LOG_TAG, "== onCreate() fcm_token => " + fcm_token + ", Gps latitude => " + latitude + ", longitude => " + longitude);

                Log.i(LOG_TAG, "== configGetandSave(2) fcm_token     ;  " + fcm_token);
                Log.i(LOG_TAG, "== configGetandSave(2) latitude      ;  " + latitude);
                Log.i(LOG_TAG, "== configGetandSave(2) longitude     ;  " + longitude);
                Log.i(LOG_TAG, "== configGetandSave(2) badgeCount    ;  " + badgeCount);

                SharedPreferences.Editor editor = pref.edit();

                // 사용자 휴대폰 번호
                editor.putString("config_cell_tel_no",  cell_tel_no);	 	// 사용자 휴대폰 번호

                // FCM토큰
                editor.putString("config_fcm_token",    fcm_token);			// FCM토큰

                // GPS현재위치(위도,경도) 저장
                editor.putString("config_latitude",     latitude);			// 위도
                editor.putString("config_longitude",	longitude);		    // 경도

                // 담당자별 위치전송 여부(Y/N)
                editor.putString("config_gps_send_yn",	"Y");

                // 메시지 수신시 Badger Count
                editor.putString("config_badge_count",	"0");

                // 뱃지 Count초기화
                boolean success = ShortcutBadger.removeCount(WebViewMangActivity.this);

                //
                editor.commit();
        }
    }


    // 메시지 결과 처리(window.receiveMessage로 데이타 전송)
    public void onJavascriptResultMessage(String json_rslt_msg) {
        //
        Log.d("[Y_Test_Msg] onJavascriptResultMessage json_rslt_msg : ", json_rslt_msg);
        //
        webview.post(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl("javascript:receiveMessage(" + json_rslt_msg + ")");
            }
        });

    }

        // 메시지 결과 처리
    public void onJavascriptResult(int requestCode, String rslt_cd, String rslt_msg) {

        Log.i(LOG_TAG, "== Y_Test_Msg onJavascriptResult() requestCode => " + Integer.toString(requestCode));
        //
        //String rslt_json_msg =  "{" + "\"rslt_cd\":"     + "\"" + rslt_cd + "\"," + "\"rslt_msg\":"    + "\"" + rslt_msg + "\"" + "}";
        String rslt_json_msg =  "{" + "\"rslt_cd\":" + rslt_cd + "," + "\"rslt_msg\":" + rslt_msg + "}";


        //Toast.makeText(getApplicationContext(),"onJavascriptResult: " + rslt_json_msg, Toast.LENGTH_LONG).show();

        switch (requestCode) {
            case 1:                                                                     // Van업체-단말기정보다운로드(ksnet)
                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        //webview.loadUrl("javascript:doInfoDownloadRslt(" + rslt_json_msg + ")");
                        webview.loadUrl("javascript:receiveMessage(" + rslt_json_msg + ")");
                    }
                });
                break;
            case 2:                                                                     // RFID 국세청 단말기승인
                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:doRfidNtsDeviceCertRslt(" + rslt_json_msg + ")");
                    }
                });
                break;
            case 3:                                                                     // RFID 국세청 상품다운로드
                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:doRfidNtsItemDownRslt(" + rslt_json_msg + ")");
                    }
                });
                break;
            case 4:                                                                     // RFID 실제데이타 국세청전송 결과리턴
                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:doRfidNtsSendRslt(" + rslt_json_msg + ")");
                    }
                });
                break;
            case 5:                                                                     // RFID 태그읽기
                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:doRfidTagReadRslt(" + rslt_json_msg + ")");
                    }
                });
                break;
            case 7:                                                                     // 7:주류판매계산서 sms전송
                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:doRecptSmsSendRslt(" + rslt_json_msg + ")");
                    }
                });
                break;
            case 8:                                                                     // 주류통장금액 잔액조회처리
                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:doRemdrViewRslt(" + rslt_json_msg + ")");
                    }
                });
                break;
            case 9:                                                                     // 주류금액 입금처리
                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:doPayRslt(" + rslt_json_msg + ")");
                    }
                });
                break;
            default:
                break;
        }
    }

}
