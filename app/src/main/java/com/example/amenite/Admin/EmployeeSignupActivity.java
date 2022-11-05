package com.example.amenite.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Employee.EmployeeActivity;
import com.example.amenite.R;
import com.example.amenite.Welcome;
import com.example.amenite.databinding.ActivityEmployeeSignupBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class EmployeeSignupActivity extends AppCompatActivity {
    private ActivityEmployeeSignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        DBresources dBresources = new DBresources();
        dBresources.database.collection("User").document(intent.getStringExtra("UserID"))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.contains("Profile_Pic"))
                        {
                            Glide.with(EmployeeSignupActivity.this)
                                    .load(documentSnapshot.get("Profile_Pic").toString())
                                    .into(binding.employeeemployeesignupPic);
                        }
                        else
                        {

                            Glide.with(EmployeeSignupActivity.this)
                                    .load("android.resource://"+getPackageName()+"/drawable/profile")
                                    .into(binding.employeeemployeesignupPic);
                        }
                        binding.employeesignupAddresstextview.setText(documentSnapshot.get("Address").toString());
                        binding.employeesignupEmailTextview.setText(documentSnapshot.get("Email").toString());
                        binding.employeesignupUsernameTextview.setText(documentSnapshot.get("Username").toString());
                        binding.employeesignupNameEdittext.setText(documentSnapshot.get("Name").toString());
                        binding.employeesignupPhonenumberTextview.setText(documentSnapshot.get("Phone_Number").toString());
                        binding.employeesignupDOBTextview.setText(documentSnapshot.get("Date_of_Birth").toString());
                        binding.employeesignupserviceTextview.setText(intent.getStringExtra("Service").toString());


                    }
                });
        
        binding.employeeAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dBresources.database.collection("User").document(intent.getStringExtra("UserID"))
                        .update("Service",intent.getStringExtra("Service")
                        , "Role","Employee").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dBresources.database.collection("Job").document(intent.getStringExtra("UserID"))
                                                .delete();
                                startActivity(new Intent(EmployeeSignupActivity.this,AdminHomeActivity.class));
                            }
                        });
            }
        });
        binding.employeeRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmlogout = new AlertDialog.Builder(EmployeeSignupActivity.this);
                confirmlogout.setMessage("Are you sure?");
                confirmlogout.setTitle("Accepted");
                confirmlogout.setCancelable(false);
                confirmlogout.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) ->
                {

                    dBresources.database.collection("Job").document(intent.getStringExtra("UserID"))
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                    startActivity(new Intent(EmployeeSignupActivity.this, AdminHomeActivity.class));
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
        
    }
}