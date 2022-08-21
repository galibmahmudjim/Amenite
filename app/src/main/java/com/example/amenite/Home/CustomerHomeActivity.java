package com.example.amenite.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.amenite.ContactUsActivity;
import com.example.amenite.R;
import com.example.amenite.Services.BeautyServiceActivity;
import com.example.amenite.Services.ElecrtronicandAppliancesServiceActivity;
import com.example.amenite.Services.HomeServiceActivity;
import com.example.amenite.Services.TravelServiceActivites;

public class CustomerHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_customer_home);
       Button customerHomeActivityTravelButton = findViewById(R.id.CustomerHomeActivityTravelButton);
       Button customerHomeActivityHomeserviceButton = findViewById(R.id.CustomerHomeActivityHomeserviceButton);
       Button customerHomeActivityContactusButton = findViewById(R.id.CustomerHomeActivityContactusButton);
        Button customerHomeActivityBeautyButton = findViewById(R.id.CustomerHomeActivityBeautyButton);
        Button customerHomeActivityElectronicandappliancesButton = findViewById(R.id.CustomerHomeActivityElectronicandappliancesButton);
        customerHomeActivityHomeserviceButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(CustomerHomeActivity.this, HomeServiceActivity.class));
           }
       });
       customerHomeActivityTravelButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(CustomerHomeActivity.this, TravelServiceActivites.class));
           }
       });
       customerHomeActivityElectronicandappliancesButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(CustomerHomeActivity.this, ElecrtronicandAppliancesServiceActivity.class));

           }
       });
       customerHomeActivityBeautyButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(CustomerHomeActivity.this, BeautyServiceActivity.class));

           }
       });
       customerHomeActivityContactusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHomeActivity.this, ContactUsActivity.class));
            }
        });
    }
}