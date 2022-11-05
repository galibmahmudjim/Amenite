package com.example.amenite.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.Admin.AdminHomeActivity;
import com.example.amenite.Admin.EmployeeSignupActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.example.amenite.databinding.ActivityAppointmentDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CustomerAppointmentDetailsActivity extends AppCompatActivity {
    private ActivityAppointmentDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbardetails.appbartitle.setText("Appointment Details");
        binding.toolbardetails.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        final String[] emailemp = {""};
        DBresources dBresources = new DBresources();
        dBresources.database.collection("Appointment").document(intent.getStringExtra("Appointment_Id"))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                binding.CustomerAppointmentDetailsNameTextview.setText(documentSnapshot.get("Name").toString());
                                binding.CustomerAppointmentDetailsAppointmentidTextview.setText(documentSnapshot.get("Appointment_Id").toString());
                                binding.CustomerAppointmentDetailsAppointmentstatusTextview.setText(documentSnapshot.get("Appointment_Status").toString());
                                binding.CustomerAppointmentDetailsEmailTextview.setText(documentSnapshot.get("Email").toString());
                                binding.CustomerAppointmentPhone1Textview.setText(documentSnapshot.get("Phone_Number").toString());
                                if(documentSnapshot.get("Appointment_Status").equals("Accepted"))
                                {
                                    emailemp[0] = (String) documentSnapshot.get("Emoloyee");
                                }
                                if (!documentSnapshot.get("Phone_Number 2").toString().isEmpty() && documentSnapshot.get("Phone_Number 2").toString() != null)
                                    binding.CustomerAppointmentPhone2Textview.setText(documentSnapshot.get("Phone_Number 2").toString());
                                else
                                    binding.CustomerAppointmentPhone2Linearlayout.removeAllViews();

                                String Address = documentSnapshot.get("AddressMap").toString();
                                if (!documentSnapshot.get("Address_Details").toString().isEmpty() && documentSnapshot.get("Address_Details").toString() != null) {
                                    Address += ",\n" + documentSnapshot.get("Address_Details");
                                }
                                binding.CustomerAppointmentDetailsAddressTextview.setText(Address);
                                String Service = documentSnapshot.get("Service").toString();
                                if (documentSnapshot.contains("Choose_Service")) {
                                    if ( documentSnapshot.get("Choose_Service") != null) {
                                        Service += ", " + documentSnapshot.get("Choose_Service");
                                    }
                                }
                                if (!documentSnapshot.get("Additional_Service").toString().isEmpty() && documentSnapshot.get("Additional_Service").toString() != null) {
                                    Service += ",\n" + documentSnapshot.get("Additional_Service");
                                }
                                binding.CustomerAppointmentDetailsServiceTextview.setText(Service);
                                binding.CustomerAppointmentDetailsDateandtimeTextview.setText(documentSnapshot.get("Appointment_Date").toString() + ", " + documentSnapshot.get("Appointment_Time").toString());
                                binding.CustomerAppointmentBasefareTextview.setText(documentSnapshot.get("Base_Fare").toString());
                                binding.CustomerAppointmentVatTextview.setText(documentSnapshot.get("Vat").toString());
                                binding.CustomerAppointmentAddchargeTextview.setText(documentSnapshot.get("Additional_Charge").toString());
                                binding.CustomerAppointmentTotalTextview.setText(documentSnapshot.get("Total_Fare").toString());
                            }
                        }
                    }
                });
        binding.CustomerAppointmentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmlogout = new AlertDialog.Builder(CustomerAppointmentDetailsActivity.this);
                confirmlogout.setMessage("Are you sure?");
                confirmlogout.setTitle("Cancel");
                confirmlogout.setCancelable(false);
                confirmlogout.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) ->
                {

                   dBresources.database.collection("Appointment").document(binding.CustomerAppointmentDetailsAppointmentidTextview.getText().toString())
                                   .update("Appointment_Status","Cancelled");
                    startActivity(new Intent(CustomerAppointmentDetailsActivity.this, CustomerActivity.class));
                    finish();
                });
                confirmlogout.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->

                {
                    dialog.cancel();
                });
                AlertDialog alertDialog = confirmlogout.create();
                alertDialog.show();
            }
        });
        binding.CustomerAppointmentCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] num = {new String()};
              Task t1 =  dBresources.database.collection("User").whereEqualTo("Email",emailemp[0]).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot queryDocumentSnapshotL : queryDocumentSnapshots)
                                {
                                    num[0] = queryDocumentSnapshotL.get("Phone_Number").toString();
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", queryDocumentSnapshotL.get("Phone_Number").toString(), null));
                                    startActivity(intent);
                                }
                            }
                        });
              t1.addOnSuccessListener(new OnSuccessListener() {
                  @Override
                  public void onSuccess(Object o) {

                  }
              });
            }
        });
    }
}