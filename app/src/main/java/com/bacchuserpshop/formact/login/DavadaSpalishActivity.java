package com.bacchuserpshop.formact.login;

import com.bacchuserpshop.R;

import com.bacchuserpshop.formact.login.pems.PermissionsActivity;
import com.bacchuserpshop.formact.login.pems.PermissionsChecker;
import com.bacchuserpshop.formact.main.WebViewMangActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.app.Activity;


public class DavadaSpalishActivity extends Activity {

    private static final String LOG_TAG = "SmartDavadaSpalishActivity";

    private static final String EXTRA_KEY 	= "com.davada.formact.login.SmartDavadaSpalishActivity";
    private static final Integer SPALISH_TIME = 2300;		// 5초

    String auto_update_check_url    = null;		// this.getResources().getString(R.string.url_auto_update_check);
    String auto_update_down_url 		= null;		// this.getResources().getString(R.string.url_auto_update_down);
    String auto_update_file_name 	= null; 	// "/mnt/sdcard//Download/SalesMan.apk";
    String down_version_name		= "1.0";
    String curr_version_name		= "1.0";

    Handler SplashHandler;
    Thread 	SplashThread;

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
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            };
    private PermissionsChecker checker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.smart_davada_spalish);

        try {

            // 버전 28이상은 환경설정 앱별 권한처리 체크를 하게 해야한다.(저장장치,전화,sms등)
            checker = new PermissionsChecker(this);
            if (checker.lacksPermissions(PERMISSIONS)) {
                // 권한처리 시작
                PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
            } else {
                // 로그인 이동
                //goToLoginScreen();

                goToWebLoginScreen();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 로그인 이동
    public void goToLoginScreen() {

        // 메인화면 스플레쉬 처리..
        SplashHandler = new Handler();

        SplashThread = new Thread() {
            @Override
            public void run() {
                try {

                    // 1초간 중지시킨다.(단위 : 밀리세컨드)
                    Thread.sleep(SPALISH_TIME);

                    SplashHandler.post(new Runnable() {
                        public void run() {

                            // 로그인 이동
                            //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            //startActivity(intent);

                         // 메인 Activty종료
                            onDestroy();
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        SplashThread.start();
    }


    // 웹뷰 로그인 이동
    public void goToWebLoginScreen() {

        // 메인화면 스플레쉬 처리..
        SplashHandler = new Handler();

        SplashThread = new Thread() {
            @Override
            public void run() {
                try {

                    // 1초간 중지시킨다.(단위 : 밀리세컨드)
                    Thread.sleep(SPALISH_TIME);

                    SplashHandler.post(new Runnable() {
                        public void run() {

                            // 다바다ERP ksnet웹뷰 호출
                            Intent intent = new Intent(getApplicationContext(), WebViewMangActivity.class);

                            // 금결원 ktfc웹뷰호출
                            //Intent intent = new Intent(getApplicationContext(), KtfcWebViewMangActivity.class);
                            startActivity(intent);

                            //finish();

                            // 메인 Activty종료
                            onDestroy();
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        SplashThread.start();
    }

	/*
	@Override
	protected void onResume() {
		super.onResume();

		SplashThread = new Thread() {
			@Override
			public void run() {
				try {

					Thread.sleep(SPALISH_TIME);

					SplashHandler.post(new Runnable() {
						public void run() {
							goToNextScreen();
						}
					});

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		SplashThread.start();
	}
	*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        //
        android.os.Process.killProcess(android.os.Process.myPid() );
    }
}