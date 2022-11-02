package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerServicesCarrentalInoutCityBinding;

public class CustomerServicesCarrentalInoutCityActivity extends AppCompatActivity {
    private ActivityCustomerServicesCarrentalInoutCityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerServicesCarrentalInoutCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.appbartitle.setText("Car Rental");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent(CustomerServicesCarrentalInoutCityActivity.this,CustomerAppointmentConfirmActivity.class);
        intent.putExtras(bundle);
        binding.CustomerServiceCarrentalInsideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("Service_Area","Inside");
                startActivity(intent);
            }
        });
        binding.CustomerServiceCarrentalOutsideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("Service_Area","Outside");
                startActivity(intent);
            }
        });
    }
}