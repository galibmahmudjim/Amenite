package com.example.amenite.SendNotificationPack;

import android.util.Log;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class Token {

    private static final String TAG = "Amenite_check";
    DBresources dBresources;
    public void saveToken()
    {
        dBresources = new DBresources();
        FirebaseMessaging.getInstance().subscribeToTopic("Appointment");
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                HashMap<String,String> userToken = new HashMap<>();
                userToken.put("Token",s);
                userToken.put("Email", User.Emailid);
                dBresources.databaseReference.child("Token").child(User.UserID).setValue(userToken);

            }
        });
    }
}
