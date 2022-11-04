package com.example.amenite.Customer.Services.Carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.Customer.CustomerAddressMapsActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.databinding.ActivityCustomerCarrentalServiceBinding;

public class CustomerCarrentalServiceActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerCarrentalServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.appbartitle.setText("Car Rental");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.CutomerCarrentalAppointmentNameTextview.setText(User.Fullname);
        binding.CutomerCarrentalAppointmentMapAddressTextview.setText(User.getAddress().toString());
        Log.d(TAG, "onCreate:fd " + binding.CutomerCarrentalAppointmentMapAddressTextview.getText());
        binding.CutomerCarrentalAppointmentPhonenumberTextview.setText(User.Phonenumber);
        binding.CutomerCarrentalAppointmentEmailTextview.setText(User.Emailid);
        binding.CutomerCarrentalAppointmentPhonenumber1Edittext.setText(getIntent().getStringExtra("PhoneNumber2"));
        binding.CutomerCarrentalAppointmentAddressEdittext.setText(getIntent().getStringExtra("AddressDetails"));
        binding.CutomerCarrentalAppointmentMapAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerCarrentalServiceActivity.this, CustomerAddressMapsActivity.class);
                intent.putExtra("PhoneNumber2", binding.CutomerCarrentalAppointmentPhonenumber1Edittext.getText().toString());
                intent.putExtra("AddressDetails", binding.CutomerCarrentalAppointmentAddressEdittext.getText().toString());
                intent.putExtra("Activity", CustomerCarrentalServiceActivity.class);
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
            binding.CutomerCarrentalAppointmentMapAddressTextview.setText(intent.getStringExtra("Address"));
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
        binding.CutomerCarrentalAppointmentNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onCreate: "+finalLatitude);
                Intent intent1 = new Intent(CustomerCarrentalServiceActivity.this, CustomerServicesCarrentalInoutCityActivity.class);
                intent1.putExtra("Name", binding.CutomerCarrentalAppointmentNameTextview.getText().toString());
                intent1.putExtra("PhoneNumber", binding.CutomerCarrentalAppointmentPhonenumberTextview.getText().toString());
                intent1.putExtra("PhoneNumber2", binding.CutomerCarrentalAppointmentPhonenumber1Edittext.getText().toString());
                intent1.putExtra("Email", binding.CutomerCarrentalAppointmentEmailTextview.getText().toString());
                intent1.putExtra("AddressMap", binding.CutomerCarrentalAppointmentMapAddressTextview.getText().toString());
                intent1.putExtra("AddressDetails", binding.CutomerCarrentalAppointmentAddressEdittext.getText().toString());
                intent1.putExtra("Service", "Carrental");
                intent1.putExtra("Latitude", String.valueOf(finalLatitude));
                intent1.putExtra("Longitude",String.valueOf(finalLongitude));
                intent1.putExtra("PhoneNumber2", binding.CutomerCarrentalAppointmentPhonenumber1Edittext.getText().toString());
                if (binding.CutomerCarrentalAppointmentMapAddressTextview.getText().toString().isEmpty()) {
                    binding.CutomerCarrentalAppointmentMapAddressButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                    binding.CutomerCarrentalAppointmentMapAddressTextview.setError("Pick Address from map");
                } else {
                    startActivity(intent1);
                }

            }
        });

    }
}