package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.widget.Toast;

import static com.example.myapplication.NotificationChannel.CHANNEL_ID;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

    Notification notification; // notification object to display

    public NotificationService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null; // not using bound services
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // create the notification channel for Android 8.0 (API 26) and above
        NotificationChannel.createNotificationChannel(this);

        // build and display the notification
        build_and_view_Notification(this, intent);

        // start the service in the foreground with the built notification
        startForeground(1, notification);

        return START_STICKY; // if service killed, restart with same intent
    }

    // helper function to build and display a notification
    private void build_and_view_Notification(Context context, Intent intent) {
        String title = "New NOTIFICATION!";
        String message = "Done!";

        // create an intent to open HomePageActivity when notification clicked
        Intent goInfo = new Intent(context, HomePageActivity.class);
        PendingIntent go = PendingIntent.getActivities(context, 100, new Intent[]{goInfo}, PendingIntent.FLAG_IMMUTABLE);

        // build the notification
        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.qwizwizicondrawable)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .addAction(R.drawable.qwizwizicondrawable, "Go To Notification", go)
                .setContentIntent(go)
                .build();

        // check if the app has permission to post notifications
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            // if permission not granted, show a toast and return
            Toast.makeText(context, "NOTIFICATION!", Toast.LENGTH_LONG).show();
            return;
        }

        // display the notification
        NotificationManager nm = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        nm.notify(1, notification);

        Toast.makeText(context, "Notification", Toast.LENGTH_LONG).show();
    }
}
