package com.example.amenite.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.amenite.PROFILE.Employee;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;

public class EmployeeProfileActivity extends AppCompatActivity {

    private Button button;

    private TextView Name;
    private TextView PhoneNumber;
    private TextView Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_employee_profile);

        Name= findViewById(R.id.EmployeeProfileName);
        Name.setText(User.Username);

        PhoneNumber= findViewById(R.id.EmployeeProfilePhoneNumber);
        PhoneNumber.setText(User.Phonenumber);

        Email=findViewById(R.id.EmployeeProfileMail);
        Email.setText(User.Emailid);

        button = findViewById(R.id.adminButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(EmployeeProfileActivity.this,EmployeeEditProfileActivity.class ) );

            }
        });

    }

}