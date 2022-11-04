package com.example.amenite.Customer.Services.Carrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCarRentalConfirmBinding;

public class CarRentalConfirmActivity extends AppCompatActivity {
    private ActivityCarRentalConfirmBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarRentalConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.carconfirmtoolbar.appbartitle.setText("Car Rental");
        binding.carconfirmtoolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.carfeecon.setText(getIntent().getStringExtra("Payment"));
        binding.carcatcon.setText(getIntent().getStringExtra("Car_Catagory"));
        if(getIntent().getStringExtra("Car_Catagory").equals("Premium"))
        {
            binding.carpic.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.carpic1,null));
            binding.carconcapacity.setText("4 seats");
        }
        else if(getIntent().getStringExtra("Car_Catagory").equals("Family"))
        {
            binding.carpic.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.carpic2,null));
            binding.carconcapacity.setText("1-7 seats");
        }
        else if(getIntent().getStringExtra("Car_Catagory").equals("Group"))
        {
            binding.carpic.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.group,null));
            binding.carconcapacity.setText("1-12 seats");
        }
        if(getIntent().getStringExtra("Service_Area").equals("Inside City(Dhaka)"))
            binding.carconfirmtextview.setText(getIntent().getStringExtra("Time_Limit")+"-"+getIntent().getStringExtra("Service_Area")+"-"+"Car Rental");
        else
            binding.carconfirmtextview.setText(getIntent().getStringExtra("Service_Area")+"-"+"Car Rental");


    }
}