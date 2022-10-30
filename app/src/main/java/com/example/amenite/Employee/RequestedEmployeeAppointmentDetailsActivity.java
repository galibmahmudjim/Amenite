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
import com.example.amenite.databinding.ActivityRequestedEmployeeAppointmentDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class RequestedEmployeeAppointmentDetailsActivity extends AppCompatActivity {
    private ActivityRequestedEmployeeAppointmentDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestedEmployeeAppointmentDetailsBinding.inflate(getLayoutInflater());
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
                                binding.RequestedEmployeeAppointmentDetailsDetailsNameTextview.setText(documentSnapshot.get("Name").toString());
                                binding.RequestedEmployeeAppointmentDetailsDetailsEmailTextview.setText(documentSnapshot.get("Email").toString());
                                binding.RequestedEmployeeAppointmentDetailsPhone1Textview.setText(documentSnapshot.get("Phone_Number").toString());
                                if (!documentSnapshot.get("Phone_Number 2").toString().isEmpty() && documentSnapshot.get("Phone_Number 2").toString() != null)
                                    binding.RequestedEmployeeAppointmentDetailsPhone2Textview.setText(documentSnapshot.get("Phone_Number 2").toString());
                                else
                                    binding.RequestedEmployeeAppointmentDetailsPhone2Linearlayout.removeAllViews();

                                String Address = documentSnapshot.get("AddressMap").toString();
                                if (!documentSnapshot.get("Address_Details").toString().isEmpty() && documentSnapshot.get("Address_Details").toString() != null) {
                                    Address += ",\n" + documentSnapshot.get("Address_Details");
                                }
                                binding.RequestedEmployeeAppointmentDetailsDetailsAddressTextview.setText(Address);
                                String Service = documentSnapshot.get("Service").toString();
                                if (!documentSnapshot.get("Choose_Service").toString().isEmpty() && documentSnapshot.get("Choose_Service").toString() != null) {
                                    Service += ", " + documentSnapshot.get("Choose_Service");
                                }
                                if (!documentSnapshot.get("Additional_Service").toString().isEmpty() && documentSnapshot.get("Additional_Service").toString() != null) {
                                    Service += ",\n" + documentSnapshot.get("Additional_Service");
                                }
                                binding.RequestedEmployeeAppointmentDetailsDetailsServiceTextview.setText(Service);
                                binding.RequestedEmployeeAppointmentDetailsDetailsDateandtimeTextview.setText(documentSnapshot.get("Appointment_Date").toString() + ", " + documentSnapshot.get("Appointment_Time").toString());
                            }
                        }
                    }
                });
        binding.RequestedEmployeeAppointmentDetailsRejectButton.setOnClickListener(new View.OnClickListener() {
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


        binding.RequestedEmployeeAppointmentDetailsAcceptButton.setOnClickListener(new View.OnClickListener() {
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
                Intent intent1 = new Intent(RequestedEmployeeAppointmentDetailsActivity.this,EmployeeActivity.class);
                intent1.putExtra("currentFragment","27");
                startActivity(intent1);
            }
        });
    }
}