package com.example.myapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationChannel {

    public static final String CHANNEL_ID = "channelId"; // ID for the channel
    public static final CharSequence CHANNEL_NAME = "Channel_1"; // name shown in settings
    public static final String CHANNEL_DESCRIPTION = "Channel for Example"; // description of the channel

    // the channel connects between my notification and the OS(NotificationManager class):
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // create a new notification channel (required for Android 8.0+)
            android.app.NotificationChannel channel = new android.app.NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            // register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }
    }
}
