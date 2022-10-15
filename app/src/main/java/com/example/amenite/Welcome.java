package com.example.amenite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;

public class Welcome extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private DBresources dBresources = new DBresources();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.d(TAG, "Welcome: "+ User.Role+" "+User.Emailid);
        findViewById(R.id.WelcomeLoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Welcome.this, LoginActivity.class));

            }
        });
        findViewById(R.id.WelcomeSignupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Welcome.this, SignupActivity.class));

            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}