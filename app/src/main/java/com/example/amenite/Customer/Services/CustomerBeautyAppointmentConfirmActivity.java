package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.amenite.databinding.ActivityCustomerBeautyAppointmentConfirmBinding;

import java.util.Calendar;

public class CustomerBeautyAppointmentConfirmActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerBeautyAppointmentConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerBeautyAppointmentConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.appbartitle.setText("Time and Date");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Log.d(TAG, "onClick: "+getIntent().getStringExtra("Latitude"));

        binding.CustomerBeautyAppointmentConfirmActivityDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePick();
            }
        });
        binding.CustomerBeautyAppointmentConfirmActivityTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timepick();
            }
        });
        Intent intent = getIntent();
        binding.CustomerBeautyAppointmentConfirmActivityConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+binding.CustomerBeautyAppointmentConfirmActivityTimeButton.getText().toString());
                if (binding.CustomerBeautyAppointmentConfirmActivityDateButton.getText().toString().equals("Select Date")) {
                    Toast.makeText(CustomerBeautyAppointmentConfirmActivity.this, "Select Appointment Date", Toast.LENGTH_SHORT).show();
                    binding.CustomerBeautyAppointmentConfirmActivityDateButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                } else if (binding.CustomerBeautyAppointmentConfirmActivityTimeButton.getText().toString().equals("Select Time")) {
                    Toast.makeText(CustomerBeautyAppointmentConfirmActivity.this, "Select Appointment Time", Toast.LENGTH_SHORT).show();
                    binding.CustomerBeautyAppointmentConfirmActivityTimeButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                } else {
                    Bundle extra =getIntent().getExtras();
                    Intent intent1 = new Intent(CustomerBeautyAppointmentConfirmActivity.this,CustomerAppointmentConfirmActivity.class);
                    intent1.putExtras(extra);
                    intent1.putExtra("Date",binding.CustomerBeautyAppointmentConfirmActivityDateButton.getText().toString());
                    intent1.putExtra("Date",binding.CustomerBeautyAppointmentConfirmActivityDateButton.getText().toString());
                    intent1.putExtra("Time",binding.CustomerBeautyAppointmentConfirmActivityTimeButton.getText().toString());
                    intent1.putExtra("Additional Service",binding.CustomerBeautyAppointmentConfirmActivityAdditionalserviceExittext.getText().toString());
                   startActivity(intent1);
                   finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle extras =getIntent().getExtras();
        Intent intent1 = new Intent(CustomerBeautyAppointmentConfirmActivity.this,CustomerChooseBeautyServiceActivity.class);
        intent1.putExtras(extras);
        startActivity(intent1);
    }



    private void Timepick() {
        final Calendar calendar = Calendar.getInstance();
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                CustomerBeautyAppointmentConfirmActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(0, 0, 0, i, i1);
                        binding.CustomerBeautyAppointmentConfirmActivityTimeButton.setText(DateFormat.format("hh:mm aa", calendar));
                    }
                }, 12, 0, false
        );
        timePickerDialog.show();
    }

    private void DatePick() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CustomerBeautyAppointmentConfirmActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.CustomerBeautyAppointmentConfirmActivityDateButton.setText(i2 + "-" + (i1 + 1) + "-" + i);
            }
        }, year, month, day);
        datePickerDialog.show();

    }
}