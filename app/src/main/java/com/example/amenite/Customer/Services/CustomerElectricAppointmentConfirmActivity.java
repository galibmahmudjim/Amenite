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
import com.example.amenite.databinding.ActivityCustomerElectricAppointmentConfirmBinding;

import java.util.Calendar;

public class CustomerElectricAppointmentConfirmActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerElectricAppointmentConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerElectricAppointmentConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.appbartitle.setText("Electronic and Appliance Repair");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.CustomerElectricAppointmentConfirmActivityDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePick();
            }
        });
        binding.CustomerElectricAppointmentConfirmActivityTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timepick();
            }
        });
        Intent intent = getIntent();
        binding.CustomerElectricAppointmentConfirmActivityConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+binding.CustomerElectricAppointmentConfirmActivityTimeButton.getText().toString());
                if (binding.CustomerElectricAppointmentConfirmActivityDateButton.getText().toString().equals("Select Date")) {
                    Toast.makeText(CustomerElectricAppointmentConfirmActivity.this, "Select Appointment Date", Toast.LENGTH_SHORT).show();
                    binding.CustomerElectricAppointmentConfirmActivityDateButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                } else if (binding.CustomerElectricAppointmentConfirmActivityTimeButton.getText().toString().equals("Select Time")) {
                    Toast.makeText(CustomerElectricAppointmentConfirmActivity.this, "Select Appointment Time", Toast.LENGTH_SHORT).show();
                    binding.CustomerElectricAppointmentConfirmActivityTimeButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                } else {
                    Bundle extra =getIntent().getExtras();
                    Intent intent1 = new Intent(CustomerElectricAppointmentConfirmActivity.this,CustomerAppointmentConfirmActivity.class);
                    intent1.putExtras(extra);
                    intent1.putExtra("Date",binding.CustomerElectricAppointmentConfirmActivityDateButton.getText().toString());
                    intent1.putExtra("Date",binding.CustomerElectricAppointmentConfirmActivityDateButton.getText().toString());
                    intent1.putExtra("Time",binding.CustomerElectricAppointmentConfirmActivityTimeButton.getText().toString());
                    intent1.putExtra("Additional Service",binding.CustomerElectricAppointmentConfirmActivityAdditionalserviceExittext.getText().toString());
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
        Intent intent1 = new Intent(CustomerElectricAppointmentConfirmActivity.this,ElectricchoiceActivity.class);
        intent1.putExtras(extras);
        startActivity(intent1);
        finish();
    }



    private void Timepick() {
        final Calendar calendar = Calendar.getInstance();
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                CustomerElectricAppointmentConfirmActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(0, 0, 0, i, i1);
                        binding.CustomerElectricAppointmentConfirmActivityTimeButton.setText(DateFormat.format("hh:mm aa", calendar));
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
                CustomerElectricAppointmentConfirmActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.CustomerElectricAppointmentConfirmActivityDateButton.setText(i2 + "-" + (i1 + 1) + "-" + i);
            }
        }, year, month, day);
        datePickerDialog.show();

    }
}