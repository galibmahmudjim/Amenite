package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerServiceCarrentalConfirmareaBinding;

public class CustomerServiceCarrentalConfirmareaActivity extends AppCompatActivity {
    private ActivityCustomerServiceCarrentalConfirmareaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerServiceCarrentalConfirmareaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.appbartitle.setText("Car Rental");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}