package com.example.amenite.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.R;
import com.example.amenite.Welcome;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText signupActivityUsernameEdittext = (EditText) findViewById(R.id.SignupActivityUsernameEdittext);
        EditText signupActivityEmailEdittext = (EditText) findViewById(R.id.SignupActivityEmailEdittext);
        EditText signupActivityPasswordEdittext = (EditText) findViewById(R.id.SignupActivityPasswordEdittext);
        EditText signupActivityConfirmPasswordEdittext = (EditText) findViewById(R.id.SignupActivityConfirmPasswordEdittext);
        EditText signupActivityPhonenumberEdittext = (EditText)findViewById(R.id.SignupActivityPhonenumberEdittext);
        ProgressBar signupActivityLoadingProgressBar = (ProgressBar) findViewById(R.id.SignupActivityLoadingProgressBar);
        signupActivityLoadingProgressBar.setVisibility(View.GONE);
        findViewById(R.id.SignupActivitySubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Signup.signup(signupActivityUsernameEdittext,signupActivityEmailEdittext,signupActivityPhonenumberEdittext,signupActivityPasswordEdittext,signupActivityConfirmPasswordEdittext,signupActivityLoadingProgressBar);
                 if(signupActivityUsernameEdittext.getError()==null&& signupActivityEmailEdittext.getError()==null&& signupActivityConfirmPasswordEdittext.getError()==null&&
                 signupActivityPhonenumberEdittext.getError()==null)
                 {
                     //startActivity(new Intent(SignupActivity.this,Welcome.class));
                 }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}