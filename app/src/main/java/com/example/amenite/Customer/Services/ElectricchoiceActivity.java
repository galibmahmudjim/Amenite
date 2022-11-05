package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityElectricchoiceBinding;

public class ElectricchoiceActivity extends AppCompatActivity {
    private ActivityElectricchoiceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityElectricchoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.electhostoolbar.appbartitle.setText("Service");
        binding.electhostoolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = new Intent(ElectricchoiceActivity.this,CustomerElectricAppointmentConfirmActivity.class);
        intent.putExtras(getIntent().getExtras());
        binding.Acbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("ChooseService","AC Repair");
                startActivity(intent);
            }
        });
        binding.fributton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("ChooseService","Refrigerator Repair");
                startActivity(intent);
            }
        });
        binding.homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("ChooseService","Home Electrification");
                startActivity(intent);
            }
        });
        binding.applibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("ChooseService","Appliances Repair");
                intent.putExtra("ChooseService","Appliances Repair");
                startActivity(intent);
            }
        });
    }
}