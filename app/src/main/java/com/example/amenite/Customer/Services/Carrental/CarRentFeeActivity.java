package com.example.amenite.Customer.Services.Carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCarRentFeeBinding;

public class CarRentFeeActivity extends AppCompatActivity {
    private ActivityCarRentFeeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarRentFeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.cartoolbar.appbartitle.setText("Car Rental");
        if(getIntent().getStringExtra("Service_Area").equals("Outside City"))
        {

            binding.cartextview.setText("Car Rental"+"-"+getIntent().getStringExtra("Service_Area"));

        }
        else
        {

            binding.cartextview.setText("Car Rental-"+getIntent().getStringExtra("Time_Limit")+"-"+getIntent().getStringExtra("Service_Area"));

        }
        binding.budgetcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.familycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.groupcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
}