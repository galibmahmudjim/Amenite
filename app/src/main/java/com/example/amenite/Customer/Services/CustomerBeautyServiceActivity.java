package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.Customer.CustomerAddressMapsActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerBeautyServiceBinding;

public class CustomerBeautyServiceActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerBeautyServiceBinding activityCustomerBeautyServiceBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCustomerBeautyServiceBinding = ActivityCustomerBeautyServiceBinding.inflate(getLayoutInflater());
        setContentView(activityCustomerBeautyServiceBinding.getRoot());
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentNameTextview.setText(User.Fullname);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumberTextview.setText(User.Phonenumber);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentEmailTextview.setText(User.Emailid);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumber1Edittext.setText(getIntent().getStringExtra("PhoneNumber2"));
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentAddressEdittext.setText(getIntent().getStringExtra("AddressDetails"));
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerBeautyServiceActivity.this, CustomerAddressMapsActivity.class);
                intent.putExtra("PhoneNumber2",activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumber1Edittext.getText().toString());
                intent.putExtra("AddressDetails",activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentAddressEdittext.getText().toString());
                startActivity(intent);

            }
        });
        Intent intent = getIntent();
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.setText(intent.getStringExtra("Address"));
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerBeautyServiceActivity.this,CustomerChooseBeautyServiceActivity.class);
                intent1.putExtra("Name", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentNameTextview.getText().toString());
                intent1.putExtra("PhoneNumber", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumberTextview.getText().toString());
                intent1.putExtra("PhoneNumber2", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumber1Edittext.getText().toString());
                intent1.putExtra("Email", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentEmailTextview.getText().toString());
                intent1.putExtra("AddressMap", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.getText().toString());
                intent1.putExtra("AddressDetails", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentAddressEdittext.getText().toString());
                intent1.putExtra("Service","Beauty");
                intent1.putExtra("Latitude",intent.getDoubleExtra("Latitude",0.000));
                intent1.putExtra("Longitude",intent.getDoubleExtra("Longitude",0.00));
                intent1.putExtra("PhoneNumber2",activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumber1Edittext.getText().toString());
                if(activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.getText().toString().isEmpty())
                {
                    activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                    activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.setError("Pick Address from map");
                }
                else
                {
                    startActivity(intent1);
                }

            }
        });
    }
}