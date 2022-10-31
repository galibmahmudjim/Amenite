package com.example.amenite.Customer.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.Customer.CustomerAddressMapsActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.databinding.ActivityCustomerElectricServiceBinding;

public class CustomerElectricServiceActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerElectricServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerElectricServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.CutomerElectricAppointmentNameTextview.setText(User.Fullname);
        binding.CutomerElectricAppointmentMapAddressTextview.setText(User.getAddress().toString());
        Log.d(TAG, "onCreate:fd " + binding.CutomerElectricAppointmentMapAddressTextview.getText());
        binding.CutomerElectricAppointmentPhonenumberTextview.setText(User.Phonenumber);
        binding.CutomerElectricAppointmentEmailTextview.setText(User.Emailid);
        binding.CutomerElectricAppointmentPhonenumber1Edittext.setText(getIntent().getStringExtra("PhoneNumber2"));
        binding.CutomerElectricAppointmentAddressEdittext.setText(getIntent().getStringExtra("AddressDetails"));
        binding.CutomerElectricAppointmentMapAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerElectricServiceActivity.this, CustomerAddressMapsActivity.class);
                intent.putExtra("PhoneNumber2", binding.CutomerElectricAppointmentPhonenumber1Edittext.getText().toString());
                intent.putExtra("AddressDetails", binding.CutomerElectricAppointmentAddressEdittext.getText().toString());
                intent.putExtra("Activity", CustomerElectricServiceActivity.class);
                startActivity(intent);

            }
        });
        Intent intent = getIntent();
        double Longitude;
        double Latitude;

        Latitude = Double.parseDouble(User.Latitude);
        Longitude = Double.parseDouble(User.Longitude);
        if ( intent.getStringExtra("Address") != null)
        {
            binding.CutomerElectricAppointmentMapAddressTextview.setText(intent.getStringExtra("Address"));
            Latitude = getIntent().getDoubleExtra("Latitude",0.0);
            Longitude = getIntent().getDoubleExtra("Longitude",0.0);
        }
        else
        {
            Latitude = Double.parseDouble(User.Latitude);
            Longitude = Double.parseDouble(User.Longitude);
        }
        double finalLongitude = Longitude;
        double finalLatitude = Latitude;
        binding.CutomerElectricAppointmentNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onCreate: "+finalLatitude);
                Intent intent1 = new Intent(CustomerElectricServiceActivity.this, CustomerElectricAppointmentConfirmActivity.class);
                intent1.putExtra("Name", binding.CutomerElectricAppointmentNameTextview.getText().toString());
                intent1.putExtra("PhoneNumber", binding.CutomerElectricAppointmentPhonenumberTextview.getText().toString());
                intent1.putExtra("PhoneNumber2", binding.CutomerElectricAppointmentPhonenumber1Edittext.getText().toString());
                intent1.putExtra("Email", binding.CutomerElectricAppointmentEmailTextview.getText().toString());
                intent1.putExtra("AddressMap", binding.CutomerElectricAppointmentMapAddressTextview.getText().toString());
                intent1.putExtra("AddressDetails", binding.CutomerElectricAppointmentAddressEdittext.getText().toString());
                intent1.putExtra("Service", "Electric");
                intent1.putExtra("Latitude", String.valueOf(finalLatitude));
                intent1.putExtra("Longitude",String.valueOf(finalLongitude));
                intent1.putExtra("PhoneNumber2", binding.CutomerElectricAppointmentPhonenumber1Edittext.getText().toString());
                if (binding.CutomerElectricAppointmentMapAddressTextview.getText().toString().isEmpty()) {
                    binding.CutomerElectricAppointmentMapAddressButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                    binding.CutomerElectricAppointmentMapAddressTextview.setError("Pick Address from map");
                } else {
                    startActivity(intent1);
                }

            }
        });
    }
}