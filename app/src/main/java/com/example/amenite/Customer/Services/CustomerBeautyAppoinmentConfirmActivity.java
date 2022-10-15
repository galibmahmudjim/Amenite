package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerBeautyAppoinmentConfirmBinding;

import java.util.Calendar;

public class CustomerBeautyAppoinmentConfirmActivity extends AppCompatActivity {
    private ActivityCustomerBeautyAppoinmentConfirmBinding binding;

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerBeautyAppoinmentConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.CustomerBeautyAppoinmentConfirmActivityDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePick();
            }
        });
        binding.CustomerBeautyAppoinmentConfirmActivityTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timepick();
            }
        });
        binding.CustomerBeautyAppoinmentConfirmActivityConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(CustomerBeautyAppoinmentConfirmActivity.this);
                dialog.setContentView(R.layout.activity_customer_pop_up_confirm_window);
                dialog.getWindow().setGravity(Gravity.TOP|Gravity.RIGHT);
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.x = 10;
                layoutParams.y = 10;
                dialog.getWindow().setAttributes(layoutParams);
                dialog.show();


            }
        });

    }

    private void Timepick() {
        final Calendar calendar = Calendar.getInstance();
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                CustomerBeautyAppoinmentConfirmActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(0,0,0,i,i1);
                        binding.CustomerBeautyAppoinmentConfirmActivityTimeButton.setText(DateFormat.format("hh:m aa",calendar));
                    }
                },12,0,false
        );
        timePickerDialog.show();
    }

    private void DatePick() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CustomerBeautyAppoinmentConfirmActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.CustomerBeautyAppoinmentConfirmActivityDateButton.setText(i2 + "-" + (i1 + 1) + "-" + i);
            }
        }, year,month,day);
        datePickerDialog.show();

    }
}