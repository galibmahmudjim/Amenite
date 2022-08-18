package com.example.amenite.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText signupActivityUsernameEdittext = (EditText) findViewById(R.id.SignupActivityUsernameEdittext);
        EditText signupActivityEmailEdittext = (EditText) findViewById(R.id.SignupActivityEmailEdittext);
        EditText signupActivityPasswordEdittext = (EditText) findViewById(R.id.SignupActivityPasswordEdittext);
        EditText signupActivityConfirmPasswordEdittext = (EditText) findViewById(R.id.SignupActivityConfirmPasswordEdittext);
        findViewById(R.id.SignupSubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Signup.signup(signupActivityUsernameEdittext,signupActivityEmailEdittext,signupActivityPasswordEdittext,signupActivityConfirmPasswordEdittext);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}