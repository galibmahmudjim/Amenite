package com.example.amenite.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.amenite.R;

public class AdminProfileActivity extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        button = findViewById(R.id.adminButton);

    }

}