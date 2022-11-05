package com.example.amenite.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
        binding.toolbarcarrent.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.toolbarcarrent.appbartitle.setText("Car Rental Details");
        binding.toolbarcarrent.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat sample = new SimpleDateFormat("hh:mm aa");
        Log.d(TAG.TAG, "onCreate: " + sample.format(new Date()));
        DBresources dBresources = new DBresources();
        dBresources.database.collection("Appointment").document(intent.getStringExtra("Appointment_Id"))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                if (documentSnapshot.get("Appointment_Status").equals("Accepted")) {
                                    binding.carrentDetailsAcceptButton.setText("Call");
                                    binding.carrentDetailsRejectButton.setText("Cancel");
                                } else if (documentSnapshot.get("Appointment_Status").equals("Completed")) {
                                    binding.carrentDetailsAcceptButton.setText("Call");
                                    binding.carrentDetailsRejectButton.setVisibility(View.GONE);
                                } else if (documentSnapshot.get("Appointment_Status").equals("Pending")) {

                                    binding.carrentDetailsAcceptButton.setText("Accept");
                                    binding.carrentDetailsRejectButton.setText("Reject");
                                } else {

                                    binding.carrentDetailsAcceptButton.setVisibility(View.GONE);
                                    binding.carrentDetailsRejectButton.setVisibility(View.GONE);
                                }

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
                                if (binding.carrentareaTextview.getText().toString().equals("Inside City(Dhaka)")) {
                                    binding.destemp.setVisibility(View.GONE);
                                } else {
                                    binding.carrentDestinationTextview.setText(documentSnapshot.get("Destination_Address").toString());
                                }
                                binding.carrentDateandtimeTextview.setText(documentSnapshot.get("Appointment_Date").toString() + ", " + documentSnapshot.get("Appointment_Time").toString());
                            }
                        }
                    }
                });
        binding.carrentDetailsRejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmlogout = new AlertDialog.Builder(CarrentalDetailsActivity.this);
                confirmlogout.setMessage("Are you sure?");
                confirmlogout.setTitle("Cancel");
                confirmlogout.setCancelable(false);
                confirmlogout.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) ->
                {
                    if( binding.carrentDetailsRejectButton.getText().toString().equals("Reject")) {
                        dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                                .collection("Requested_Employee").whereEqualTo("Email", User.Emailid)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                            dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                                                    .collection("Requested_Employee").document(queryDocumentSnapshot.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                    else
                    {
                        dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                                .update("Appointment_Status","cancelled");

                    }
                    startActivity(new Intent(CarrentalDetailsActivity.this,EmployeeActivity.class));
                });
                confirmlogout.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->

                {
                    dialog.cancel();
                });
                AlertDialog alertDialog = confirmlogout.create();
                alertDialog.show();

            }
        });


        binding.carrentDetailsAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG.TAG, "onClick: "+binding.carrentDetailsAcceptButton.getText());
                if(binding.carrentDetailsAcceptButton.getText().toString().equals("Call"))
                {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:" + binding.carrentDetailsPhone1Textview.getText().toString()));
                    startActivity(dialIntent);
                }
                else {
                    LocalDateTime now = LocalDateTime.now();
                    SimpleDateFormat sample = new SimpleDateFormat("hh:mm aa");
                    dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                            .update("Employee", User.Emailid
                                    , "Accepted_Date", now.getDayOfMonth() + "-" + now.getMonth().toString().substring(0, 3) + "-" + now.getYear()
                                    , "Accepted_Time", sample.format(new Date())
                                    , "Appointment_Status", "Accepted");
                    dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                            .collection("Requested_Employee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                        dBresources.database.collection("Appointment").document(getIntent().getStringExtra("Appointment_Id"))
                                                .collection("Requested_Employee").document(queryDocumentSnapshot.getId())
                                                .delete();
                                    }
                                }
                            });
                    Intent intent1 = new Intent(CarrentalDetailsActivity.this, EmployeeActivity.class);
                    intent1.putExtra("currentFragment", "27");
                    startActivity(intent1);
                }
            }
        });
    }
}