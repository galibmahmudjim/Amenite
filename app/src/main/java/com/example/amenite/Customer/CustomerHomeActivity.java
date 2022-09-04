package com.example.amenite.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.Customer.Services.BeautyServiceActivity;
import com.example.amenite.Customer.Services.ElecrtronicandAppliancesServiceActivity;
import com.example.amenite.Customer.Services.HomeServiceActivity;
import com.example.amenite.Customer.Services.TravelServiceActivites;

public class CustomerHomeActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User user = new User();
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_customer_home);
       Button customerHomeActivityTravelButton = findViewById(R.id.CustomerHomeActivityTravelButton);
       Button customerHomeActivityHomeserviceButton = findViewById(R.id.CustomerHomeActivityHomeserviceButton);
       Button customerHomeActivityContactusButton = findViewById(R.id.CustomerHomeActivityContactusButton);
       Button customerHomeActivityDonationButton = findViewById(R.id.CustomerHomeActivityDonationButton);
       Button customerHomeActivityBeautyButton = findViewById(R.id.CustomerHomeActivityBeautyButton);
       Button customerHomeActivityElectronicandappliancesButton = findViewById(R.id.CustomerHomeActivityElectronicandappliancesButton);
        Log.d(TAG, "onCreate: "+user.Username);
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
       customerHomeActivityDonationButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(CustomerHomeActivity.this, CustomerDonationActivity.class));
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