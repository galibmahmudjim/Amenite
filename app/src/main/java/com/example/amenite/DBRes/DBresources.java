package com.example.amenite.DBRes;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DBresources {
    private static final String TAG = "DBResources";
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://amenite-default-rtdb.firebaseio.com/");
    public FirebaseFirestore database = FirebaseFirestore.getInstance();
    public Map<String,String> Signup(String Email, String Password, String PhoneNumber, String Username)
    {
        Map<String, String> user = new HashMap<>();
        user.put("Email",Email);
        user.put("Username",Username);
        user.put("Phone_Number",PhoneNumber);
        user.put("Password",Password);
        return user;
    }

}
