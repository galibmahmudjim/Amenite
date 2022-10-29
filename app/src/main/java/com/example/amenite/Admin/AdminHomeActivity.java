package com.example.amenite.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityAdminHomeBinding;
//import com.example.amenite.databinding.ActivityCustomerBinding;

public class AdminHomeActivity extends AppCompatActivity {

    private static final String TAG = "Amenite_check";
    private ActivityAdminHomeBinding activityAdminHomeBinding;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        //setContentView(R.layout.activity_customer);
        button = findViewById(R.id.profileButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: bismillah");
                startActivity(new Intent(AdminHomeActivity.this, AdminProfileActivity.class ));
            }
        });

        findViewById(R.id.AdminCustomer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminHomeActivity.this, CustomerListActivity.class));

            }
        });
        findViewById(R.id.AdminEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminHomeActivity.this, EmployeeListActivity.class));

            }
        });

    }
}