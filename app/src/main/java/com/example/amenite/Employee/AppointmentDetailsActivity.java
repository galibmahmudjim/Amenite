package com.example.amenite.Employee;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.databinding.ActivityAppointmentDetailsBinding;
import com.example.amenite.databinding.ActivityEmployeeAppointmentDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class AppointmentDetailsActivity extends AppCompatActivity {
    private  ActivityEmployeeAppointmentDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeAppointmentDetailsBinding.inflate(getLayoutInflater());
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
                                binding.EmployeeAppointmentDetailsNameTextview.setText(documentSnapshot.get("Name").toString());
                                binding.EmployeeAppointmentDetailsAppointmentidTextview.setText(documentSnapshot.get("Appointment_Id").toString());
                                binding.EmployeeAppointmentDetailsAppointmentstatusTextview.setText(documentSnapshot.get("Appointment_Status").toString());
                                binding.EmployeeAppointmentDetailsEmailTextview.setText(documentSnapshot.get("Email").toString());
                                binding.EmployeeAppointmentPhone1Textview.setText(documentSnapshot.get("Phone_Number").toString());
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
                            }
                        }
                    }
                });
    }
}