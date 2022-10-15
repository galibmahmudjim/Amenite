package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerChooseBeautyServiceBinding;

public class CustomerChooseBeautyServiceActivity extends AppCompatActivity {
    private ActivityCustomerChooseBeautyServiceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerChooseBeautyServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        binding.CustomerChooseBeautyserviceBeautycareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerChooseBeautyServiceActivity.this,CustomerBeautyAppoinmentConfirmActivity.class);
                intent1.putExtra("Name", intent.getStringExtra("Name"));
                intent1.putExtra("PhoneNumber",intent.getStringExtra("PhoneNumber"));
                intent1.putExtra("PhoneNumber2",intent.getStringExtra("PhoneNumber2"));
                intent1.putExtra("Email",intent.getStringExtra("Email") );
                intent1.putExtra("AddressMap",intent.getStringExtra("AddressMap"));
                intent1.putExtra("AddressDetails",intent.getStringExtra("AddressDetails") );
                intent1.putExtra("Latitue",intent.getDoubleExtra("Latitude",0.000));
                intent1.putExtra("Longitude",intent.getDoubleExtra("Longitude",0.00));
                intent1.putExtra("Service","Beauty Care");
                startActivity(intent1);
            }
        });
        binding.CustomerChooseBeautyserviceHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerChooseBeautyServiceActivity.this,CustomerBeautyAppoinmentConfirmActivity.class);
                intent1.putExtra("Name", intent.getStringExtra("Name"));
                intent1.putExtra("PhoneNumber",intent.getStringExtra("PhoneNumber"));
                intent1.putExtra("PhoneNumber2",intent.getStringExtra("PhoneNumber2"));
                intent1.putExtra("Email",intent.getStringExtra("Email") );
                intent1.putExtra("AddressMap",intent.getStringExtra("AddressMap"));
                intent1.putExtra("AddressDetails",intent.getStringExtra("AddressDetails") );
                intent1.putExtra("Latitue",intent.getDoubleExtra("Latitude",0.000));
                intent1.putExtra("Longitude",intent.getDoubleExtra("Longitude",0.00));
                intent1.putExtra("Service","Hair");
                startActivity(intent1);
            }
        });
        binding.CustomerChooseBeautyserviceMakeupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerChooseBeautyServiceActivity.this,CustomerBeautyAppoinmentConfirmActivity.class);
                intent1.putExtra("Name", intent.getStringExtra("Name"));
                intent1.putExtra("PhoneNumber",intent.getStringExtra("PhoneNumber"));
                intent1.putExtra("PhoneNumber2",intent.getStringExtra("PhoneNumber2"));
                intent1.putExtra("Email",intent.getStringExtra("Email") );
                intent1.putExtra("AddressMap",intent.getStringExtra("AddressMap"));
                intent1.putExtra("AddressDetails",intent.getStringExtra("AddressDetails") );
                intent1.putExtra("Latitue",intent.getDoubleExtra("Latitude",0.000));
                intent1.putExtra("Longitude",intent.getDoubleExtra("Longitude",0.00));
                intent1.putExtra("Service","MakeUp");
                startActivity(intent1);
            }
        });
    }
}