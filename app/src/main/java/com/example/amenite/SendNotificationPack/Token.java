package com.example.amenite.SendNotificationPack;


import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.MainActivity;
import com.example.amenite.PROFILE.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;


public class Token extends FirebaseMessagingService {

    private static final String TAG = "Amenite_check";
    DBresources dBresources = new DBresources();
    public void saveToken()
    {
        dBresources = new DBresources();
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                FirebaseMessaging.getInstance().subscribeToTopic("all");
                HashMap<String,String> userToken = new HashMap<>();
                userToken.put("Token",s);
                userToken.put("Email", User.Emailid);
                dBresources.database.collection("Token").document(User.Emailid)
                        .set(userToken);
            }
        });
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        HashMap<String,String> userToken = new HashMap<>();
        userToken.put("Token",token);
        userToken.put("Email", User.Emailid);
        dBresources.database.collection("Token").document(User.Emailid)
                .set(userToken);

    }
}
