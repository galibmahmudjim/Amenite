package com.example.amenite.Employee;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.example.amenite.databinding.ActivityAppointmentDetailsBinding;
import com.example.amenite.databinding.ActivityEmployeeAppointmentDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class AppointmentDetailsActivity extends AppCompatActivity {
    private ActivityEmployeeAppointmentDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeAppointmentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.toolbar.appbartitle.setText("Order Details");
        binding.toolbar.appbartitle.setText("Appointment Details");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        Home();

        DBresources dBresources = new DBresources();
        binding.EmployeeAppointmentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmlogout = new AlertDialog.Builder(AppointmentDetailsActivity.this);
                confirmlogout.setMessage("Are you sure?");
                confirmlogout.setTitle("Cancel");
                confirmlogout.setCancelable(false);
                confirmlogout.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) ->
                {
                    if( binding.EmployeeAppointmentCancelButton.getText().toString().equals("Reject")) {
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
                    startActivity(new Intent(AppointmentDetailsActivity.this,EmployeeActivity.class));
                });
                confirmlogout.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->

                {
                    dialog.cancel();
                });
                AlertDialog alertDialog = confirmlogout.create();
                alertDialog.show();

            }
        });


        binding.EmployeeAppointmentcallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG.TAG, "onClick: " + binding.EmployeeAppointmentcallButton.getText());

                if (binding.EmployeeAppointmentcallButton.getText().toString().equals("Next")) {
                    Intent intent1 = new Intent(AppointmentDetailsActivity.this,TaskCompleteActivity.class);
                    intent1.putExtra("Email",binding.EmployeeAppointmentDetailsEmailTextview.getText().toString());
                    startActivity(intent1);
                }
               else if (binding.EmployeeAppointmentcallButton.getText().toString().equals("Call")) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:" + binding.EmployeeAppointmentPhone1Textview.getText().toString()));
                    startActivity(dialIntent);
                } else {
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
                    Intent intent1 = new Intent(AppointmentDetailsActivity.this, EmployeeActivity.class);
                    intent1.putExtra("currentFragment", "27");
                    startActivity(intent1);
                }
            }
        });
    }


    public void Home() {

        DBresources dBresources = new DBresources();
        Intent intent = getIntent();
        dBresources.database.collection("Appointment").document(intent.getStringExtra("Appointment_Id"))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                binding.EmployeeAppointmentDetailsNameTextview.setText(documentSnapshot.get("Name").toString());
                                binding.EmployeeAppointmentDetailsAppointmentidTextview.setText(documentSnapshot.get("Appointment_Id").toString());
                                binding.EmployeeAppointmentDetailsAppointmentstatusTextview.setText(documentSnapshot.get("Appointment_Status").toString());
                                binding.EmployeeAppointmentDetailsEmailTextview.setText(documentSnapshot.get("Email").toString());
                                binding.EmployeeAppointmentPhone1Textview.setText(documentSnapshot.get("Phone_Number").toString());
                                if(documentSnapshot.get("Appointment_Status").equals("Completed"))
                                {
                                    binding.EmployeeAppointmentcallButton.setText("Next");
                                    binding.EmployeeAppointmentCancelButton.setVisibility(View.GONE);
                                }
                                if (!documentSnapshot.get("Phone_Number 2").toString().isEmpty() && documentSnapshot.get("Phone_Number 2").toString() != null)
                                    binding.EmployeeAppointmentPhone2Textview.setText(documentSnapshot.get("Phone_Number 2").toString());
                                else
                                    binding.EmployeeAppointmentPhone2Linearlayout.removeAllViews();

                                String Address = documentSnapshot.get("AddressMap").toString();
                                if (!documentSnapshot.get("Address_Details").toString().isEmpty() && documentSnapshot.get("Address_Details").toString() != null) {
                                    Address += ",\n" + documentSnapshot.get("Address_Details");
                                }
                                binding.EmployeeAppointmentDetailsAddressTextview.setText(Address);
                                String Service = documentSnapshot.get("Service").toString();
                                if (!documentSnapshot.get("Choose_Service").toString().isEmpty() && documentSnapshot.get("Choose_Service").toString() != null) {
                                    Service += ", " + documentSnapshot.get("Choose_Service");
                                }
                                if (!documentSnapshot.get("Additional_Service").toString().isEmpty() && documentSnapshot.get("Additional_Service").toString() != null) {
                                    Service += ",\n" + documentSnapshot.get("Additional_Service");
                                }
                                binding.EmployeeAppointmentDetailsServiceTextview.setText(Service);
                                binding.EmployeeAppointmentDetailsDateandtimeTextview.setText(documentSnapshot.get("Appointment_Date").toString() + ", " + documentSnapshot.get("Appointment_Time").toString());
                                binding.EmployeeAppointmentBasefareTextview.setText(documentSnapshot.get("Base_Fare").toString());
                                binding.EmployeeAppointmentVatTextview.setText(documentSnapshot.get("Vat").toString());
                                binding.EmployeeAppointmentAddchargeTextview.setText(documentSnapshot.get("Additional_Charge").toString());
                                binding.EmployeeAppointmentTotalTextview.setText(documentSnapshot.get("Total_Fare").toString());
                                binding.EmployeeAppointmentcallButton.setVisibility(View.VISIBLE);
                                if(documentSnapshot.get("Appointment_Status").equals("Started"))
                                {
                                    binding.EmployeeAppointmentCancelButton.setText("Complete");
                                    binding.EmployeeAppointmentcallButton.setText("Call");
                                }
                                else if(documentSnapshot.get("Appointment_Status").equals("Complete"))
                                {
                                    binding.EmployeeAppointmentCancelButton.setVisibility(View.GONE);
                                    binding.EmployeeAppointmentcallButton.setText("Call");
                                }
                                else if(documentSnapshot.get("Appointment_Status").equals("Pending"))
                                {
                                    binding.EmployeeAppointmentCancelButton.setVisibility(View.VISIBLE);
                                    binding.EmployeeAppointmentcallButton.setText("Call");
                                }
                                else if(documentSnapshot.get("Appointment_Status").equals("Accepted"))
                                {
                                    binding.EmployeeAppointmentCancelButton.setText("Cancel");
                                    binding.EmployeeAppointmentcallButton.setText("Start");
                                }
                                else if(documentSnapshot.get("Appointment_Status").equals("Cancelled"))
                                {
                                    binding.EmployeeAppointmentCancelButton.setVisibility(View.GONE);
                                    binding.EmployeeAppointmentcallButton.setVisibility(View.GONE);
                                }

                            }
                        }
                    }
                });
    }
}