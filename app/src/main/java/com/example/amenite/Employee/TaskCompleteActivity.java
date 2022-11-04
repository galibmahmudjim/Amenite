package com.example.amenite.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityTaskCompleteBinding;

public class TaskCompleteActivity extends AppCompatActivity {
    private ActivityTaskCompleteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();

    }
}