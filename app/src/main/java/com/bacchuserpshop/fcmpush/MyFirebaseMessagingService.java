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

            // ?????? ??????
            int badgeCount = BadgeCount();

            // JSONObject data => title, content
            String push_type = remoteMessage.getData().get("push_type");
            String title     = remoteMessage.getData().get("title");
            String content   =  remoteMessage.getData().get("content");

            Log.d(LOG_TAG, "== onMessageReceived() getNotification() data push_type  : " + push_type);
            Log.d(LOG_TAG, "== onMessageReceived() getNotification() data title      : " + title);
            Log.d(LOG_TAG, "== onMessageReceived() getNotification() data content    : " + content);

            // ????????????
            // push_type => NOTICE:????????????, SALES:?????????????????????, CONTAINER:?????????????????????,  PAYMENT:????????????, TAX:???????????????,
            MusicPlayer(push_type);

            // FCM Message Notification
            sendNotification(title, content, badgeCount);
        }
    }

    // ?????? ??????
    private int BadgeCount() {
        SharedPreferences pref = getSharedPreferences("bacchus_erp", Activity.MODE_PRIVATE);
        String cfg_badgeCount   = ConfigUtils.getBadgeCount(getApplicationContext());

        Log.d(LOG_TAG, "== onMessageReceived() cfg_badgeCount: " + cfg_badgeCount);

        int badgeCount = Integer.parseInt(cfg_badgeCount) + 1;

        // json data?????? notification??? ????????? badger??? ?????? ???.???
        // ???????????? ??????
        ShortcutBadger.applyCount(getApplicationContext(), badgeCount);

        // ????????? ????????? Badger Count Update
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

            // ??????????????? ????????? ????????? ??? ??? ??????.
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.icon_sms)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)                            // ????????? ???????????? ??????????????? ??????.
                    .setContentIntent(pendingIntent)                // ????????? ???????????? ????????? ????????? ??????.
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

    // ????????????
    private void MusicPlayer(String push_type) {

        Log.i(LOG_TAG, "== onMessageReceived() MusicPlayer() Start.. ");

        // ??????????????? ?????? ????????????(Y/N)
        String cfg_voiceuseyn   = ConfigUtils.getNfcVoiceUseYn(getApplicationContext());
        if (cfg_voiceuseyn.toUpperCase().equals("Y")) {
        }
        //
        // push_type => NOTICE:????????????, SALES:?????????????????????, CONTAINER:?????????????????????,  PAYMENT:????????????, TAX:???????????????,
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
            // ??????????????? ????????? ?????? (??????)
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
            // ???????????? ????????? ????????? ?????? (??????)
            music.start();
        }
    };

}
