package com.example.amenite.Customer.Services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.Customer.CustomerActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.SendNotificationPack.FcmNotificationsSender;
import com.example.amenite.databinding.ActivityCustomerAppointmentConfirmBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        binding.toolbar.appbartitle.setText("Appointment");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        User.RetriveData();
        binding.CustomerAppointmentConfirmNameTextview
                .setText(intent.getStringExtra("Name"));
        binding.CustomerAppointmentConfirmPhoneNumberTextview.setText(intent.getStringExtra("PhoneNumber"));

        binding.CustomerAppointmentConfirmPhonenumber2Textview.setText(intent.getStringExtra("PhoneNumber2"));

        binding.CustomerAppointmentConfirmEmailTextview.setText(intent.getStringExtra("Email"));

        String address = intent.getStringExtra("AddressMap");
        String Latitude = String.valueOf(intent.getStringExtra("Latitude"));
        String Longitude = String.valueOf(intent.getStringExtra("Longitude"));

        if (!intent.getStringExtra("AddressDetails").isEmpty() && intent.getStringExtra("AddressDetails") != null) {
            address = address + ",\n" + intent.getStringExtra("AddressDetails") + ".";
        }
        binding.CustomerAppointmentConfirmAddressTextview.setText(address);

        binding.CustomerAppointmentConfirmServiceTextview.setText(getIntent().getStringExtra("Service"));

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

        binding.CustomerAppointmentConfirmCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerAppointmentConfirmActivity.this, CustomerActivity.class));
            }
        });
        binding.CustomerAppointmentConfirmConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> appointment = new HashMap<>();
                Intent intent1 = getIntent();
                appointment.put("Name", intent.getStringExtra("Name"));
                appointment.put("Phone_Number", intent.getStringExtra("PhoneNumber"));
                appointment.put("Phone_Number 2", intent.getStringExtra("PhoneNumber2"));
                appointment.put("Service", intent.getStringExtra("Service"));
                appointment.put("Choose_Service", intent.getStringExtra("ChooseService"));
                appointment.put("Additional_Service", intent.getStringExtra("Additional Service"));
                appointment.put("Email", intent.getStringExtra("Email"));
                appointment.put("AddressMap", intent.getStringExtra("AddressMap"));
                appointment.put("Longitude", Longitude);
                appointment.put("Latitude", Latitude);
                appointment.put("Address_Details", intent.getStringExtra("AddressDetails"));
                appointment.put("Appointment_Date", intent.getStringExtra("Date"));
                appointment.put("Appointment_Time", intent.getStringExtra("Time"));
                appointment.put("Base_Fare",  binding.CustomerAppointmentConfirmBasefareTextview.getText().toString());
                appointment.put("Additional_Charge", binding.CustomerAppointmentConfirmAddchargeTextview.getText().toString());
                appointment.put("Vat", binding.CustomerAppointmentConfirmVatTextview.getText().toString());
                appointment.put("Total_Fare", binding.CustomerAppointmentConfirmTotalTextview.getText().toString());

                Log.d(TAG, "onSuccess: " + Latitude);

                DBresources dBresources = new DBresources();
                String key = new String();
                key = "APPB" + User.Phonenumber.substring(User.Phonenumber.length() - 3, User.Phonenumber.length()) + User.Username.substring(0, 3).toUpperCase();
                int[] appoint_no = {1};
                Task t1 = dBresources.database.collection("Appointment").whereEqualTo("Email", User.Emailid).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        appoint_no[0]++;
                                    }
                                }
                            }
                        });
                String finalKey = key;
                Task t2 = Tasks.whenAllSuccess(t1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> objects) {
                        LocalDateTime now = LocalDateTime.now();
                        String appointment_ID = finalKey + String.valueOf(appoint_no[0]);
                        appointment.put("Appointment_Id", appointment_ID);
                        appointment.put("Appointment_Status", "Pending");
                        appointment.put("Request_Date", now.getDayOfMonth() + "-" + now.getMonth().toString().substring(0, 3) + "-" + now.getYear());
                        SimpleDateFormat sample = new SimpleDateFormat("hh:mm aa");
                        appointment.put("Request_Time", sample.format(new Date()));
                        dBresources.database.collection("Appointment").document(appointment_ID).set(appointment)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });

                    }
                });
                HashMap<String, List<String>> appReqSend = new HashMap<>();
                List<String> str = new ArrayList<>();
                Task t3 = Tasks.whenAllSuccess(t2).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> objects) {
                        dBresources.database.collection("User").whereEqualTo("Role", "Employee")
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int n = 0;
                                            for (QueryDocumentSnapshot qs : task.getResult()) {
                                                if (qs.contains("Latitude") && qs.contains("Longitude")) {
                                                    float[] results = new float[1];
                                                    Location.distanceBetween(Double.parseDouble(appointment.get("Latitude")), Double.parseDouble(appointment.get("Longitude")),
                                                            Double.parseDouble(qs.get("Latitude").toString()), Double.parseDouble(qs.get("Longitude").toString()),
                                                            results);

                                                    if (results[0] <= 2000) {
                                                        n++;
                                                        DocumentReference dref = dBresources.database.collection("Appointment")
                                                                .document(appointment.get("Appointment_Id").toString());
                                                        HashMap<String, String> hmap = new HashMap<String, String>();
                                                        hmap.put("Email", qs.get("Email").toString());
                                                        dref.collection("Requested_Employee").document(qs.getId()).set(hmap);
                                                        dBresources.database.collection("Token").document(qs.get("Email").toString())
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if(task.isSuccessful())
                                                                        {
                                                                            DocumentSnapshot documentSnapshot = task.getResult();
                                                                            if(documentSnapshot.exists())
                                                                            {
                                                                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(documentSnapshot.get("Email").toString()
                                                                                        ,"channel1","New Appointment Request","You have a new appointment request\nName: "+User.Fullname+"\nAddress: "
                                                                                        +appointment.get("Address")+"\nTime and Date: "+appointment.get("Appointment_Date")+", "+appointment.get("Appointment_Time")
                                                                                        ,getApplicationContext(),CustomerAppointmentConfirmActivity.this);
                                                                            }
                                                                        }
                                                                    }
                                                                });

                                                    }
                                                }
                                            }
                                            dBresources.database.collection("Token").document(User.Emailid)
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                                if(documentSnapshot.exists())
                                                                {
                                                                    Log.d(TAG, "onComplete: "+documentSnapshot.get("Token").toString());
                                                                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(documentSnapshot.get("Token").toString()
                                                                            ,"Channel1","New Appointment","Your Appointment\nName: "+User.Fullname+"\nAddress: "
                                                                            +appointment.get("AddressMap")+"\nTime and Date: "+appointment.get("Appointment_Date")+", "+appointment.get("Appointment_Time")
                                                                            ,getApplicationContext(),CustomerAppointmentConfirmActivity.this);
                                                                    notificationsSender.SendNotifications();
                                                                }
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                });
                Tasks.whenAllSuccess(t3).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> objects) {
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