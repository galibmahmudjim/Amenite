package com.example.amenite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.DBRes.DBresources;

public class Welcome extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private DBresources dBresources = new DBresources();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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

    @Override
    protected void onRestart() {
        super.onRestart();
        if(dBresources.firebaseUser!=null)
        {
          //  finish();
        }
    }
}