package com.bacchuserpshop.fcmpush;

import com.bacchuserpshop.common.net.HttpService;
import com.bacchuserpshop.common.util.ConfigUtils;
import com.bacchuserpshop.common.vo.RetailOrderVo;
import com.bacchuserpshop.formact.main.AndroidBridge;
import com.bacchuserpshop.formact.main.WebViewMangActivity;
import com.bacchuserpshop.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.io.IOException;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String LOG_TAG = "MyFirebaseMessagingService";

    private static final String NOTIFICATION_CHANNEL = "me.leolin.shortcutbadger.example";
    private int notificationId = 0;

    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /*
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(LOG_TAG, "Refreshed onNewToken : " + token);

        // Save the Fcm Token
        SharedPreferences pref  = getApplicationContext().getSharedPreferences("bacchus_erp", 0);
        SharedPreferences.Editor editor = pref.edit();
        //
        editor.putString("config_fcm_token", token);			// FCM토큰저장
        editor.commit();
    }

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.i(LOG_TAG, " == onMessageReceived() FROM : " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(LOG_TAG, "== onMessageReceived() getData() : " + remoteMessage.getData());

            // 뱃지 처리
            int badgeCount = BadgeCount();

            // JSONObject data => title, content
            String push_type = remoteMessage.getData().get("push_type");
            String title     = remoteMessage.getData().get("title");
            String content   =  remoteMessage.getData().get("content");

            Log.d(LOG_TAG, "== onMessageReceived() getNotification() data push_type  : " + push_type);
            Log.d(LOG_TAG, "== onMessageReceived() getNotification() data title      : " + title);
            Log.d(LOG_TAG, "== onMessageReceived() getNotification() data content    : " + content);

            // 음성출력
            // push_type => NOTICE:공지사항, SALES:주류판매계산서, CONTAINER:주류판매계산서,  PAYMENT:갤재금액, TAX:세금계산서,
            MusicPlayer(push_type);

            // FCM Message Notification
            sendNotification(title, content, badgeCount);
        }
    }

    // 뱃지 처리
    private int BadgeCount() {
        SharedPreferences pref = getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
        String cfg_badgeCount   = ConfigUtils.getBadgeCount(getApplicationContext());

        Log.d(LOG_TAG, "== onMessageReceived() cfg_badgeCount: " + cfg_badgeCount);

        int badgeCount = Integer.parseInt(cfg_badgeCount) + 1;

        // json data에서 notification을 없애니 badger가 먹힘 ㅠ.ㅠ
        // 뱃지건수 설정
        ShortcutBadger.applyCount(getApplicationContext(), badgeCount);

        // 메시지 수신시 Badger Count Update
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("config_badge_count",	Integer.toString(badgeCount));
        editor.commit();

        return badgeCount;
    }

    /*
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String body, int badgeCount) {

            Intent intent = new Intent(this, WebViewMangActivity.class);
            intent.putExtra("test", "test");

            PendingIntent pendingIntent = null;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Log.d(LOG_TAG, "== sendNotification() FLAG_MUTABLE...");
                pendingIntent = PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            } else {
                Log.d(LOG_TAG, "== sendNotification() FLAG_IMMUTABLE...");
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            }
            mNotificationManager.cancel(notificationId);
            notificationId++;

            // 아이콘이나 제목을 수정해 줄 수 있다.
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.icon_sms)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)                            // 선택시 자동으로 삭제되도록 설정.
                    //.setContentIntent(pendingIntent)                // 알림을 눌렀을때 실행할 인텐트 설정.
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupNotificationChannel();
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL);
            }

            Notification notification = notificationBuilder.build();
            ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
            mNotificationManager.notify(notificationId, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setupNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, "ShortcutBadger Sample",
                NotificationManager.IMPORTANCE_DEFAULT);

        mNotificationManager.createNotificationChannel(channel);
    }
//
//    @Override
//    public void handleIntent(Intent intent) {
//        //
//    }

    // 음성출력
    private void MusicPlayer(String push_type) {

        Log.i(LOG_TAG, "== onMessageReceived() MusicPlayer() Start.. ");

        // 알림메시지 음성 사용여부(Y/N)
        String cfg_voiceuseyn   = ConfigUtils.getNfcVoiceUseYn(getApplicationContext());
        if (cfg_voiceuseyn.toUpperCase().equals("Y")) {
        }
        //
        // push_type => NOTICE:공지사항, SALES:주류판매계산서, CONTAINER:주류판매계산서,  PAYMENT:갤재금액, TAX:세금계산서,
        int resid = 1;
        if (push_type.equals("NOTICE")) {
            resid = R.raw.notice_ment;
        } else if (push_type.equals("SALES")) {
            resid = R.raw.sales_invoice_ment;
        } else if (push_type.equals("CONTAINER")) {
            resid = R.raw.container_invoice_ment;
        } else if (push_type.equals("PAYMENT")) {
            resid = R.raw.payment_invoice_ment;
        } else if (push_type.equals("TAX")) {
            resid = R.raw.tax_invoice_ment;
        }
        //
        MediaPlayer music = MediaPlayer.create(this, resid);
        //music.setLooping(true);
        if(music.isPlaying()){
            // 재생중이면 실행될 작업 (정지)
            music.stop();
            try {
                music.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            music.seekTo(0);
        }else{
            // 재생중이 아니면 실행될 작업 (재생)
            music.start();
        }
    };
}
