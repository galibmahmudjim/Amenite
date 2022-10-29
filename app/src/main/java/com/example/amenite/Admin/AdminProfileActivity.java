package com.example.amenite.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amenite.PROFILE.User;
import com.example.amenite.R;

public class AdminProfileActivity extends AppCompatActivity {

    private Button button;

    private TextView Name;
    private TextView PhoneNumber;
    private TextView Email;
    //ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        Name= findViewById(R.id.AdminProfileeditText1);
        Name.setText(User.Username);

        PhoneNumber= findViewById(R.id.AdminProfileeditText5);
        PhoneNumber.setText(User.Phonenumber);

        Email=findViewById(R.id.adminProfileeditText3);
        Email.setText(User.Emailid);

        button = findViewById(R.id.adminButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminProfileActivity.this,AdminEditProfileActivity.class ) );



            }
        });

    }

}