package com.example.amenite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.amenite.login.LoginActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onClickWelcomeLoginButton(View view) {
        Intent i = new Intent(Welcome.this, LoginActivity.class);
        startActivity(i);
    }
}