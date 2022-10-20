package com.example.amenite.Customer.Services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.amenite.Customer.CustomerActivity;
import com.example.amenite.Customer.CustomerHomeFragment;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerAppointmentConfirmBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CustomerAppointmentConfirmActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerAppointmentConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerAppointmentConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        binding.CustomerAppointmentConfirmNameTextview
                .setText(intent.getStringExtra("Name"));
        binding.CustomerAppointmentConfirmPhoneNumberTextview.setText(intent.getStringExtra("PhoneNumber"));

        binding.CustomerAppointmentConfirmPhonenumber2Textview.setText(intent.getStringExtra("PhoneNumber2"));

        binding.CustomerAppointmentConfirmEmailTextview.setText(intent.getStringExtra("Email"));

        String address = intent.getStringExtra("AddressMap");
        if (!intent.getStringExtra("AddressDetails").isEmpty() && intent.getStringExtra("AddressDetails") != null) {
            address = address + ",\n" + intent.getStringExtra("AddressDetails") + ".";
        }
        binding.CustomerAppointmentConfirmAddressTextview.setText(address);

        binding.CustomerAppointmentConfirmServiceTextview.setText("Beauty, " + intent.getStringExtra("Service"));

        binding.CustomerAppointmentConfirmAddserviceTextview.setText(intent.getStringExtra("AddService"));

        binding.CustomerAppointmentConfirmDateTextview.setText(intent.getStringExtra("Date"));

        binding.CustomerAppointmentConfirmTimeTextview.setText(intent.getStringExtra("Time"));
        DecimalFormat df = new DecimalFormat("0.00");
        double base = 150;
        double add = 50;
        double vat = 15;
        double total = totalpayment(base, add, vat);

        binding.CustomerAppointmentConfirmBasefareTextview.setText(String.valueOf(df.format(base)) + "  ");
        binding.CustomerAppointmentConfirmAddchargeTextview.setText(String.valueOf(df.format(add)) + "  ");

        binding.CustomerAppointmentConfirmVatTextview.setText(String.valueOf(df.format(vat)) + "%");

        binding.CustomerAppointmentConfirmTotalTextview.setText(String.valueOf(df.format(total)) + "  ");
        binding.CustomerAppointmentConfirmAddserviceTextview.setText(intent.getStringExtra("Additional Service"));

        binding.CustomerAppointmentConfirmConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.CustomerAppointmentConfirmConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> appointment = new HashMap<>();
                Intent intent1 = getIntent();
                appointment.put("Name", intent.getStringExtra("Name"));
                appointment.put("Phone Number", intent.getStringExtra("PhoneNumber"));
                appointment.put("Phone Number 2", intent.getStringExtra("PhoneNumber2"));
                appointment.put("Service", intent.getStringExtra("Service"));
                appointment.put("Choose Service", intent.getStringExtra("ChooseService"));
                appointment.put("Additional Service", intent.getStringExtra("Additional Service"));
                appointment.put("Email", intent.getStringExtra("Email"));
                appointment.put("AddressMap", intent.getStringExtra("AddressMap"));
                appointment.put("Longitude", String.valueOf(intent.getDoubleExtra("Longitude", 0.00)));
                appointment.put("Latitude", String.valueOf(intent.getDoubleExtra("Latitude", 0.00)));
                appointment.put("Address Details", intent.getStringExtra("AddressDetails"));
                appointment.put("Appointment Date", intent.getStringExtra("Date"));
                appointment.put("Appointment Time", intent.getStringExtra("Time"));

                DBresources dBresources = new DBresources();
                final String[] key = new String[1];
                Task t1 = dBresources.database.collection("Users Appointment List").document(User.Emailid)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                int appoint_no = 0;
                                key[0] = "APPB" + User.Phonenumber.substring(User.Phonenumber.length() - 3, User.Phonenumber.length()) + User.Username.substring(0, 3).toUpperCase();
                                Log.d(TAG, "onFailed: key" + key[0]);
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot.exists()) {
                                        appoint_no = 1 + Integer.parseInt((String) documentSnapshot.get("Total Appoint"));

                                    } else {
                                        appoint_no = 1;
                                    }
                                    key[0] = key[0] + String.valueOf(appoint_no);
                                    Log.d(TAG, "onFailed: key" + key[0]);
                                }
                            }

                        });
                Tasks.whenAllSuccess(t1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> objects) {
                        LocalDateTime now = LocalDateTime.now();
                        Log.d(TAG, "onClick: " + now.getDayOfMonth() + "-" + now.getMonth() + "-" + now.getYear());
                        appointment.put("Appointment Id", key[0]);
                        appointment.put("Request Date", now.getDayOfMonth() + "-" + now.getMonth().toString().substring(0, 3) + "-" + now.getYear());
                        SimpleDateFormat sample = new SimpleDateFormat("hh:mm aa");
                        appointment.put("Request Time", sample.format(new Date()));
                        dBresources.database.collection("Appointment").document(appointment.get("Appointment Id"))
                                .set(appointment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });
                        startActivity(new Intent(CustomerAppointmentConfirmActivity.this, CustomerActivity.class));

                    }
                });

            }
        });
    }

    private double totalpayment(double base, double add, double vat) {
        double a = base + add;
        a = a * (1 + vat / 100.0);
        return a;
    }
}