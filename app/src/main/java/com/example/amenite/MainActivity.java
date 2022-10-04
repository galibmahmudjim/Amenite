package com.example.amenite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.amenite.Admin.AdminHomeActivity;
import com.example.amenite.Customer.CustomerActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Employee.EmployeeHomeActivity;
import com.example.amenite.PROFILE.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG = "Amenite_check";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                DBresources dBresources = new DBresources();
                if (dBresources.firebaseUser != null) {
                    Task t1 = dBresources.database.collection("User").whereEqualTo("Email", dBresources.firebaseUser.getEmail().toString())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (task.isSuccessful()) {
                                        if (!querySnapshot.isEmpty()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                User.Emailid = document.get("Email").toString();
                                                User.password = document.get("Password").toString();
                                                User.Phonenumber =  document.get("Phone_Number").toString();
                                                User.UserID = document.getId();
                                                User.Role = document.get("Role").toString();
                                                User.Username =  document.get("Username").toString();
                                                Log.d(TAG, ": "+User.Role+" "+User.Username);
                                                if (User.Role.equals("Customer")) {
                                                    startActivity(new Intent(MainActivity.this, CustomerActivity.class));
                                                    finish();
                                                } else if (User.Role.equals("Admin")) {
                                                    startActivity((new Intent(MainActivity.this, AdminHomeActivity.class)));
                                                    finish();
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,Welcome.class));
                }


            }
        }, 3000);
    }

}