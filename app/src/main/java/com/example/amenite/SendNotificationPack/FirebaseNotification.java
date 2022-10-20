package com.example.amenite.SendNotificationPack;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotification extends FirebaseMessagingService {
    private static final String TAG = "Amenite_check";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if(message.getNotification()!=null)
        {
            String title = message.getNotification().getTitle();
            String body = message.getNotification().getBody();
            NotificationHelper.DisplayNotification(getApplicationContext(),title,body);

        }
    }
}
