package me.fsfaysalcse.firebasepush.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import me.fsfaysalcse.firebasepush.DetailsActivity;
import me.fsfaysalcse.firebasepush.MainActivity;
import me.fsfaysalcse.firebasepush.R;

public class FirebaseNotificationService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMessagingServ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: ");
        if (remoteMessage.getData().size() > 0) {

            String messageTitle = remoteMessage.getData().get("message_title");
            String messageBody = remoteMessage.getData().get("message_body");
            String websiteLink = remoteMessage.getData().get("website_link");
            String notificationType = remoteMessage.getData().get("notification_type");

            Log.d(TAG, "onMessageReceived: " + messageTitle);

            if (notificationType != null && notificationType.equals("with_link")) {
                showNotification(messageTitle, messageBody, websiteLink);
            } else {
                showNotification(messageTitle, messageBody, null);
            }
        }

        if (remoteMessage.getNotification() != null) {
            String messageTitle = remoteMessage.getNotification().getTitle();
            String messageBody = remoteMessage.getNotification().getBody();
            showNotification(messageTitle, messageBody, null);
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d("FCM", "Refreshed token: " + token);
        /**-- You can save this token into database ---*/
    }

    private void showNotification(String messageTitle, String messageBody, String websiteLink) {

        Intent intent;
        if (websiteLink != null && !websiteLink.isEmpty()) {
            intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("website_link", websiteLink);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        String channelId = this.getString(R.string.notification_channel_id);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_simple_notification_icon)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

         /**-- Show the notification ---*/
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(0, builder.build());
    }


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String channelId = this.getString(R.string.notification_channel_id);
            CharSequence channelName = this.getString(R.string.notification_channel_name);
            String channelDescription = this.getString(R.string.notification_channel_description);

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            channel.setLightColor(Color.RED);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
