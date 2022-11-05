package com.example.amenite.Customer.Services.Carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.Customer.CustomerActivity;
import com.example.amenite.Customer.Services.CustomerAppointmentConfirmActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.SendNotificationPack.FcmNotificationsSender;
import com.example.amenite.TAG;
import com.example.amenite.databinding.ActivityCarRentalConfirmBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CarRentalConfirmActivity extends AppCompatActivity {
    private ActivityCarRentalConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarRentalConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.carconfirmtoolbar.appbartitle.setText("Car Rental");
        binding.carconfirmtoolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.carfeecon.setText(getIntent().getStringExtra("Payment"));
        binding.carcatcon.setText(getIntent().getStringExtra("Car_Catagory"));
        if (getIntent().getStringExtra("Car_Catagory").equals("Premium")) {
            binding.carpic.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.carpic1, null));
            binding.carconcapacity.setText("4 seats");
        } else if (getIntent().getStringExtra("Car_Catagory").equals("Family")) {
            binding.carpic.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.carpic2, null));
            binding.carconcapacity.setText("1-7 seats");
        } else if (getIntent().getStringExtra("Car_Catagory").equals("Group")) {
            binding.carpic.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.group, null));
            binding.carconcapacity.setText("1-12 seats");
        }
        if (getIntent().getStringExtra("Service_Area").equals("Inside City(Dhaka)"))
            binding.carconfirmtextview.setText(getIntent().getStringExtra("Time_Limit") + "-" + getIntent().getStringExtra("Service_Area") + "-" + "Car Rental");
        else
            binding.carconfirmtextview.setText(getIntent().getStringExtra("Service_Area") + "-" + "Car Rental");

        binding.carconfirmproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
                startActivity(new Intent(CarRentalConfirmActivity.this, CustomerActivity.class));
            }
        });

        /*float[] results = new float[1];
        Location.distanceBetween(Double.parseDouble(getIntent().getStringExtra("Pickup_Latitude").toString()), Double.parseDouble(getIntent().getStringExtra("Pickup_Longitude").toString()),
                Double.parseDouble(getIntent().getStringExtra("Destination_Latitude").toString()), Double.parseDouble(getIntent().getStringExtra("Destination_Longitude").toString()),
                results);*/
        if(getIntent().getStringExtra("Service_Area").equals("Outside City"))
        binding.carconfirmdistance.setVisibility(View.GONE);
        else
        {
            binding.carconfirmdistance.setVisibility(View.VISIBLE);
            binding.carconfirmdistance.setText("Full day max duration 10 Hours");
        }


    }

    private void upload() {
        Intent intent = getIntent();
        HashMap<String, String> appointment = new HashMap<>();
        Intent intent1 = getIntent();
        appointment.put("Name", intent.getStringExtra("Name"));
        appointment.put("Phone_Number", intent.getStringExtra("PhoneNumber"));
        appointment.put("Phone_Number 2", intent.getStringExtra("PhoneNumber2"));
        appointment.put("Service", "Car Rental");
        appointment.put("Email", intent.getStringExtra("Email"));
        appointment.put("AddressMap", intent.getStringExtra("AddressMap"));
        appointment.put("Address_Details", intent.getStringExtra("AddressDetails"));
        appointment.put("Pickup_Address", intent.getStringExtra("Pickup_Location"));
        appointment.put("Service_Area", intent.getStringExtra("Service_Area"));
        appointment.put("Destination_Address", intent.getStringExtra("Destination_Location"));
        appointment.put("Appointment_Date", intent.getStringExtra("Pickup_Date"));
        appointment.put("Appointment_Time", intent.getStringExtra("Pickup_Time"));
        appointment.put("Pickup_Date", intent.getStringExtra("Pickup_Date"));
        appointment.put("Pickup_Time", intent.getStringExtra("Pickup_Time"));

        DBresources dBresources = new DBresources();
        String key = new String();
        key = "APPC" + User.Phonenumber.substring(User.Phonenumber.length() - 3, User.Phonenumber.length()) + User.Username.substring(0, 3).toUpperCase();
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
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot documentSnapshot = task.getResult();
                                                            if (documentSnapshot.exists()) {
                                                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(documentSnapshot.get("Email").toString()
                                                                        , "channel1", "New Appointment Request", "You have a new appointment request\nName: " + User.Fullname + "\nPickup Location: "
                                                                        + appointment.get("Pickup_Address") + "\nTime and Date: " + appointment.get("Pickup_Date") + ", " + appointment.get("Pickup_Time")
                                                                        , getApplicationContext(), CarRentalConfirmActivity.this);
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                    dBresources.database.collection("Token").document(User.Emailid)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                        if (documentSnapshot.exists()) {
                                                            Log.d(TAG.TAG, "onComplete: " + documentSnapshot.get("Token").toString());
                                                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(documentSnapshot.get("Token").toString()
                                                                    , "Channel1", "New Appointment", "Your Appointment\nName: " + User.Fullname + "\nAddress: "
                                                                    + appointment.get("Pickup_Address") + "\nTime and Date: " + appointment.get("Pickup_Date") + ", " + appointment.get("Pickup_Time")
                                                                    , getApplicationContext(), CarRentalConfirmActivity.this);
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
    }
}