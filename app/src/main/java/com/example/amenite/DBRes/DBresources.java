package com.example.amenite.DBRes;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DBresources {
    private static final String TAG = "DBResources";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://amenite-default-rtdb.firebaseio.com/");
    public void Signup(String Email, String Password, String PhoneNumber, String Username)
    {
        databaseReference.child("Profile").child(Username).child("Email").setValue(Email);
    }
    public void isUsernameExist(EditText Username ) throws InterruptedException {
       final CountDownLatch countDownLatch = new CountDownLatch(1);
       databaseReference.child("Profile").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               System.out.println("Inside");
               countDownLatch.countDown();
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
           }
       });
        countDownLatch.await(2,TimeUnit.SECONDS);
        System.out.println("Outside");
    }
    public boolean listDays()
    {
        final  CountDownLatch done = new CountDownLatch(1);
        databaseReference.child("Profile").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("In");
            }
        });
        System.out.println("Out");
        return false;

    }

}
