package com.bacchuserpshop.fcmpush;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String LOG_TAG = "MyFirebaseInstanceIDService";

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        String RefreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(LOG_TAG, "== New Refreshed Token: " + RefreshedToken);

        // FCM메시지 전송위한 토큰전송
        //new FcmTokenSendThread(RefreshedToken).start();
    }

    // FCM메시지 전송위한 토큰전송
    private class FcmTokenSendThread extends Thread {

        String fcm_token = "";

        //
        FcmTokenSendThread(String RefreshedToken) {
            this.fcm_token = RefreshedToken;
        }

        public void run() {

            try {
                Log.i(LOG_TAG, "== FcmTokenSendThread() Refreshed fcm_token => " + this.fcm_token);

//                LoginMangService service = new LoginMangService(getApplicationContext());
//                Boolean isSave = service.doSaveFcmToken(this.fcm_token);
//                if (isSave) {
//                    Log.i(LOG_TAG, "== FcmTokenSendThread() Send Success.. ");
//                } else {
//                    Log.i(LOG_TAG, "== FcmTokenSendThread() Send Fail.. ");
//                }
            } catch (Exception e) {
                //e.printStackTrace();
                Log.i(LOG_TAG, "== FcmTokenSendThread() Send Exception => " + e.getMessage());
            }
        }
    }
}