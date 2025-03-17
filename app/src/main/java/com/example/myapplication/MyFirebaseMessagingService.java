package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "Vision_Channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String messageTitle = remoteMessage.getNotification().getTitle();
            String messageBody = remoteMessage.getNotification().getBody();

            Log.d(TAG, "Message Received - Title: " + messageTitle + ", Body: " + messageBody);

            if (messageBody != null && !messageBody.trim().isEmpty()) {
                sendNotification(messageTitle, messageBody);
                sendNotificationToSystem(messageBody);
            } else {
                Log.w(TAG, "Received empty or null message body, ignoring.");
            }
        }
    }

    private void sendNotification(String title, String body) {
        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(getApplicationContext(),
                        "Title: " + (title != null ? title : "No Title") +
                                "\nMessage: " + body,
                        Toast.LENGTH_LONG).show()
        );
    }

    private void sendNotificationToSystem(String messageBody) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            if (!notificationManager.areNotificationsEnabled()) {
                Log.w(TAG, "Notifications are disabled. Requesting permission...");
                return;
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Vision")
                        .setContentText(messageBody)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            createNotificationChannel(notificationManager);
            notificationManager.notify(1, notificationBuilder.build());
        } else {
            Log.e(TAG, "NotificationManager is null, cannot send notification.");
        }
    }

    private void createNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Vision Notifications",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.setDescription("Channel for Vision app notifications");
        notificationManager.createNotificationChannel(channel);
    }
}
