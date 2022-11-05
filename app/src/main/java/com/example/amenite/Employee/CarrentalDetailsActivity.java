package com.example.amenite.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Employee.AppointmentReq.EmployeeAppointmentReqListFragment;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.example.amenite.databinding.ActivityAppointmentDetailsBinding;
import com.example.amenite.databinding.ActivityCarrentalDetailsBinding;
import com.example.amenite.databinding.ActivityRequestedEmployeeAppointmentDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CarrentalDetailsActivity extends AppCompatActivity {
    private ActivityCarrentalDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarrentalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();

        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat sample = new SimpleDateFormat("hh:mm aa");
        Log.d(TAG.TAG, "onCreate: "+sample.format(new Date()));
        DBresources dBresources = new DBresources();
        dBresources.database.collection("Appointment").document(intent.getStringExtra("Appointment_Id"))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                binding.RcarrentNameTextview.setText(documentSnapshot.get("Name").toString());
                                binding.carrentEmailTextview.setText(documentSnapshot.get("Email").toString());
                                binding.carrentDetailsPhone1Textview.setText(documentSnapshot.get("Phone_Number").toString());
                                String Address = documentSnapshot.get("AddressMap").toString();
                                if (!documentSnapshot.get("Address_Details").toString().isEmpty() && documentSnapshot.get("Address_Details").toString() != null) {
                                    Address += ",\n" + documentSnapshot.get("Address_Details");
                                }
                                binding.carrentAddressTextview.setText(Address);
                                String Service = documentSnapshot.get("Service").toString();
                                binding.carrentareaTextview.setText(documentSnapshot.get("Service_Area").toString());
                                binding.carrentDateandtimeTextview.setText(documentSnapshot.get("Appointment_Date").toString() + ", " + documentSnapshot.get("Appointment_Time").toString());
                            }
                        }
                    }
                });
        binding.carrentDetailsRejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                        .collection("Requested_Employee").whereEqualTo("Email", User.Emailid)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult())
                                {
                                    dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                                            .collection("Requested_Employee").document(queryDocumentSnapshot.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                }
                            }
                        });
                onBackPressed();
            }
        });


        binding.carrentDetailsAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDateTime now = LocalDateTime.now();
                SimpleDateFormat sample = new SimpleDateFormat("hh:mm aa");
                dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                        .update("Employee",User.Emailid
                                ,"Accepted_Date", now.getDayOfMonth() + "-" + now.getMonth().toString().substring(0, 3) + "-" + now.getYear()
                                ,"Accepted_Time", sample.format(new Date())
                                ,"Status","Accepted");
                dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                        .collection("Requested_Employee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult())
                                {
                                    dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                                            .collection("Requested_Employee").document(queryDocumentSnapshot.getId())
                                            .delete();
                                }
                            }
                        });
                Intent intent1 = new Intent(CarrentalDetailsActivity.this,EmployeeActivity.class);
                intent1.putExtra("currentFragment","27");
                startActivity(intent1);
            }
        });
    }
}