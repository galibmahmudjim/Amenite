package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.amenite.Customer.CustomerActivity;
import com.example.amenite.Customer.CustomerHomeFragment;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerBeautyAppoinmentConfirmBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CustomerBeautyAppoinmentConfirmActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerBeautyAppoinmentConfirmBinding binding;

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
        Intent intent = getIntent();
        binding.CustomerBeautyAppoinmentConfirmActivityConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+binding.CustomerBeautyAppoinmentConfirmActivityTimeButton.getText().toString());
                if (binding.CustomerBeautyAppoinmentConfirmActivityDateButton.getText().toString().equals("Select Date")) {
                    Toast.makeText(CustomerBeautyAppoinmentConfirmActivity.this, "Select Appoinment Date", Toast.LENGTH_SHORT).show();
                    binding.CustomerBeautyAppoinmentConfirmActivityDateButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                } else if (binding.CustomerBeautyAppoinmentConfirmActivityTimeButton.getText().toString().equals("Select Time")) {
                    Toast.makeText(CustomerBeautyAppoinmentConfirmActivity.this, "Select Appoinment Time", Toast.LENGTH_SHORT).show();
                    binding.CustomerBeautyAppoinmentConfirmActivityTimeButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                } else {
                    Bundle extra =getIntent().getExtras();
                    Intent intent1 = new Intent(CustomerBeautyAppoinmentConfirmActivity.this,CustomerAppoinmentConfirmActivity.class);
                    intent1.putExtras(extra);
                    intent1.putExtra("Date",binding.CustomerBeautyAppoinmentConfirmActivityDateButton.getText().toString());
                    intent1.putExtra("Time",binding.CustomerBeautyAppoinmentConfirmActivityTimeButton.getText().toString());
                    intent1.putExtra("Additional Service",binding.CustomerBeautyAppoinmentConfirmActivityAdditionalserviceExittext.getText().toString());
                    startActivity(intent1);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle extras =getIntent().getExtras();
        Intent intent1 = new Intent(CustomerBeautyAppoinmentConfirmActivity.this,CustomerBeautyServiceActivity.class);
        intent1.putExtras(extras);
        startActivity(intent1);
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
                        calendar.set(0, 0, 0, i, i1);
                        binding.CustomerBeautyAppoinmentConfirmActivityTimeButton.setText(DateFormat.format("hh:mm aa", calendar));
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
                CustomerBeautyAppoinmentConfirmActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.CustomerBeautyAppoinmentConfirmActivityDateButton.setText(i2 + "-" + (i1 + 1) + "-" + i);
            }
        }, year, month, day);
        datePickerDialog.show();

    }
}