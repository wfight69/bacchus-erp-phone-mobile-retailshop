package com.bacchuserpshop.fcmpush;

import com.bacchuserpshop.common.util.ConfigUtils;
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
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    /*
    @Override
    public void onNewToken(String token) {
        Log.d(LOG_TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        //sendRegistrationToServer(token);
    }
    */

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.i(LOG_TAG, " == onMessageReceived() FROM : " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(LOG_TAG, "== onMessageReceived() getData() : " + remoteMessage.getData());

            // 뱃지 처리
            int badgeCount = BadgeCount();

            // JSONObject data => title, message
            String title   = remoteMessage.getData().get("title");
            String message =  remoteMessage.getData().get("message");

            Log.d(LOG_TAG, "== onMessageReceived() getNotification() data title      : " + title);
            Log.d(LOG_TAG, "== onMessageReceived() getNotification() data message    : " + message);

            // wav_div => 1:공지사항, 2:주류판매계산서, 3:세금계산서, 4:갤재금액처리
            // ex) 공지사항(1), 주류판매계산서(2)
            char wav_div_char = title.charAt(title.length()-2);
            int wav_div = Character.getNumericValue(wav_div_char);

            // 음성출력
            MusicPlayer(wav_div);

            // FCM Message Notification
            String noti_title = title.substring(0, title.length()-3);
            sendNotification(noti_title, message, badgeCount);
        }

        /*
         * json data에서 notification 사용시(개고생함) ==> json.put("notificcation", noti)
         * 이경우는 badger가 동작안함 => https://www.thetopsites.net/article/51823867.shtml
        if (remoteMessage.getNotification() != null) {
            Log.d(LOG_TAG, "== onMessageReceived() getNotification()        : " + remoteMessage.getNotification().toString());
            Log.d(LOG_TAG, "== onMessageReceived() getNotification() body   : " + remoteMessage.getNotification().getBody());

            // 음성출력
            //MusicPlayer();

            //추가한것
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            // FCM Message Notification
            sendNotification(title, body, badgeCount);
        }
        */
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

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String body, int badgeCount) {

            Intent intent = new Intent(this, WebViewMangActivity.class);
            intent.putExtra("test", "test");

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            mNotificationManager.cancel(notificationId);
            notificationId++;

            // 아이콘이나 제목을 수정해 줄 수 있다.
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.icon_sms)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)                            // 선택시 자동으로 삭제되도록 설정.
                    .setContentIntent(pendingIntent)                // 알림을 눌렀을때 실행할 인텐트 설정.
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


    // 음성출력
    private void MusicPlayer(int wav_div) {

        Log.i(LOG_TAG, "== onMessageReceived() MusicPlayer() Start.. ");

        // 알림메시지 음성 사용여부(Y/N)
        String cfg_voiceuseyn   = ConfigUtils.getNfcVoiceUseYn(getApplicationContext());
        if (cfg_voiceuseyn.toUpperCase().equals("Y")) {
            //
            int resid = 1;
            if (wav_div == 1) {
                resid = R.raw.notice_ment;
            } else if (wav_div == 2) {
                resid = R.raw.sales_invoice_ment;
            } else if (wav_div == 3) {
                resid = R.raw.tax_invoice_ment;
            } else if (wav_div == 4) {
                resid = R.raw.payment_ment;
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
        }
    };

}
