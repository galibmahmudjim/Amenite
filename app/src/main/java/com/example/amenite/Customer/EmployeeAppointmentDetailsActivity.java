package com.example.amenite.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityAppointmentDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class EmployeeAppointmentDetailsActivity extends AppCompatActivity {
    private ActivityAppointmentDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
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
                                if (!documentSnapshot.get("Choose_Service").toString().isEmpty() && documentSnapshot.get("Choose_Service").toString() != null) {
                                    Service += ", " + documentSnapshot.get("Choose_Service");
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
    }
}