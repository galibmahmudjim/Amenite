package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.databinding.ActivityCustomerChooseBeautyServiceBinding;

public class CustomerChooseBeautyServiceActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerChooseBeautyServiceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerChooseBeautyServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        binding.CustomerChooseBeautyserviceBeautycareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerChooseBeautyServiceActivity.this, CustomerBeautyAppointmentConfirmActivity.class);
                intent1.putExtras(extras);
                intent1.putExtra("ChooseService","Beauty Care");
                startActivity(intent1);
            }
        });
        binding.CustomerChooseBeautyserviceHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Hair");
                Intent intent1 = new Intent(CustomerChooseBeautyServiceActivity.this, CustomerBeautyAppointmentConfirmActivity.class);
               intent1.putExtras(extras);
                intent1.putExtra("ChooseService","Hair");
                startActivity(intent1);
            }
        });
        binding.CustomerChooseBeautyserviceMakeupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerChooseBeautyServiceActivity.this, CustomerBeautyAppointmentConfirmActivity.class);
                intent1.putExtras(extras);
                intent1.putExtra("ChooseService","Make Up");
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        Intent intent1 = new Intent(CustomerChooseBeautyServiceActivity.this, CustomerBeautyServiceActivity.class);
        intent1.putExtras(extras);
        startActivity(intent1);
    }
}