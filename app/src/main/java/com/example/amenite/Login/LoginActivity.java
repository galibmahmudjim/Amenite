package com.example.amenite.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amenite.Admin.AdminHomeActivity;
import com.example.amenite.Customer.CustomerHomeActivity;
import com.example.amenite.R;

public class LoginActivity extends AppCompatActivity {
    private EditText loginActivityEmailEditText;
    private EditText loginActivityPasswordEditText;
    private Button loginActivityLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivityEmailEditText = findViewById(R.id.LoginActivityEmailEdittext);
        loginActivityPasswordEditText = findViewById(R.id.LoginActivityPasswordEdittext);
        loginActivityLoginButton = findViewById(R.id.LoginActivityLoginButton);

        loginActivityLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginActivityEmailEditText.getText().toString().equals(new String("admin")))
                {

                    startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                }
                else
                startActivity(new Intent(LoginActivity.this, CustomerHomeActivity.class));
                }
        });
    }

}