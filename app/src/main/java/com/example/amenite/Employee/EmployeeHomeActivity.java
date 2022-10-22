package com.example.amenite.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.amenite.Admin.AdminEditProfileActivity;
import com.example.amenite.Admin.AdminHomeActivity;
import com.example.amenite.Admin.AdminProfileActivity;
import com.example.amenite.Customer.ContactUsFragment;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityAdminHomeBinding;
import com.example.amenite.databinding.ActivityEmployeeHomeBinding;

public class EmployeeHomeActivity extends AppCompatActivity {


    private Button button;
    private ActivityEmployeeHomeBinding activityEmployeeHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        button = findViewById(R.id.employeeProfileButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(EmployeeHomeActivity.this, EmployeeProfileActivity.class ));
            }
        });

    }
}