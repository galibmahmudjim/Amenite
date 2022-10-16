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
        activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentNameTextview.setText(User.Fullname);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentPhonenumberTextview.setText(User.Phonenumber);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentEmailTextview.setText(User.Emailid);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentMapAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: cloc");
                startActivity(new Intent(CustomerBeautyServiceActivity.this, CustomerAddressMapsActivity.class));
            }
        });
        Intent intent = getIntent();
        activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentMapAddressTextview.setText(intent.getStringExtra("Address"));
        activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerBeautyServiceActivity.this,CustomerChooseBeautyServiceActivity.class);
                intent1.putExtra("Name", activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentNameTextview.getText().toString());
                intent1.putExtra("PhoneNumber", activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentPhonenumberTextview.getText().toString());
                intent1.putExtra("PhoneNumber2", activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentPhonenumber1Edittext.getText().toString());
                intent1.putExtra("Email", activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentEmailTextview.getText().toString());
                intent1.putExtra("AddressMap", activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentMapAddressTextview.getText().toString());
                intent1.putExtra("AddressDetails", activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentAddressEdittext.getText().toString());
                intent1.putExtra("Latitue",intent.getDoubleExtra("Latitude",0.000));
                intent1.putExtra("Longitude",intent.getDoubleExtra("Longitude",0.00));

                if(activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentMapAddressTextview.getText().toString().isEmpty())
                {
                    activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentMapAddressButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                    activityCustomerBeautyServiceBinding.CutomerBeautyAppoinmentMapAddressTextview.setError("Pick Address from map");
                }
                else
                {
                    startActivity(intent1);
                }

            }
        });
    }
}