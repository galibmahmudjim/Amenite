package com.example.amenite.SendNotificationPack;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.amenite.R;

import java.nio.channels.Channel;

public class NotificationHelper {
    public static String Channel_ID = "Appointment Request";
    public static String Channel_NAME = "Appointment Request";
    public static String Channel_DESC = "Appointment Request";
    public static void DisplayNotification(Context context, String title, String message)
    {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(Channel_ID,Channel_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(Channel_DESC);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Channel_ID)
                .setSmallIcon(R.drawable.amenite_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1,builder.build());
    }

}
