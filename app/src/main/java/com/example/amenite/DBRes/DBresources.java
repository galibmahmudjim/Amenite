package com.example.amenite.DBRes;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://amenite-default-rtdb.firebaseio.com/");
    public void Signup(String Email, String Password, String PhoneNumber, String Username)
    {
        databaseReference.child("Profile").child(Username).child("Username").setValue(Username);
        databaseReference.child("Profile").child(Username).child("Email").setValue(Email);
        databaseReference.child("Profile").child(Username).child("Phone_Number").setValue(PhoneNumber);
        databaseReference.child("Profile").child(Username).child("Password").setValue(Password);
    }

}
