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

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerBeautyAppoinmentConfirmBinding;
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
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    Log.d(TAG, "onClick: " + size.x);

                    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View view1 = layoutInflater.inflate(R.layout.activity_customer_pop_up_confirm_window, null);
                    PopupWindow popupWindow = new PopupWindow(view1, size.x - 100, -100, false);
                    popupWindow.showAtLocation(binding.getRoot(), Gravity.LEFT | Gravity.CENTER, 30, 80);
                    popupWindow.isTouchable();

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowNameTextview))
                            .setText(intent.getStringExtra("Name"));
                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowPhoneNumberTextview))
                            .setText(intent.getStringExtra("PhoneNumber"));

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowPhonenumber2Textview))
                            .setText(intent.getStringExtra("PhoneNumber2"));

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowEmailTextview))
                            .setText(intent.getStringExtra("Email"));

                    String address = intent.getStringExtra("AddressMap");
                    if(!intent.getStringExtra("AddressDetails").isEmpty()&& intent.getStringExtra("AddressDetails")!=null)
                    {
                        address=address+"\n,"+intent.getStringExtra("AddressDetails")+".";
                    }
                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowAddressTextview))
                            .setText(address);

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowServiceTextview))
                            .setText("Beauty, " + intent.getStringExtra("Service"));

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowAddserviceTextview))
                            .setText(binding.CustomerBeautyAppoinmentConfirmActivityAdditionalserviceExittext.getText().toString());

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowDateTextview))
                            .setText(binding.CustomerBeautyAppoinmentConfirmActivityDateButton.getText().toString());

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowTimeTextview))
                            .setText(binding.CustomerBeautyAppoinmentConfirmActivityTimeButton.getText().toString());
                    DecimalFormat df = new DecimalFormat("0.00");
                    double base = 150;
                    double add = 50;
                    double vat = 15;
                    double total = totalpayment(base,add,vat);

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowBasefareTextview))
                            .setText(String.valueOf(df.format(base))+"  ");
                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowAddchargeTextview))
                            .setText(String.valueOf(df.format(add))+"  ");

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowVatTextview))
                            .setText(String.valueOf(df.format(vat))+"%");

                    ((TextView) popupWindow.getContentView().findViewById(R.id.CustomerServicepopupWindowTotalTextview))
                            .setText(String.valueOf(df.format(total))+"  ");



                    popupWindow.getContentView().findViewById(R.id.customerAppoinmentCancelButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.getContentView().findViewById(R.id.customerAppoinmentConfirmButton)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();

                                }
                            });
                }
            }
        });

    }

    private double totalpayment(double base, double add, double vat)
    {
        double a = base+add;
        a=a*(1+vat/100.0);
        return a;
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