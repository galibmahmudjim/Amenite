package com.example.amenite.Customer.Services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.amenite.Customer.CustomerAddressMapsActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerHomeServiceBinding;

public class CustomerHomeServiceActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerHomeServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerHomeServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.hstoolbar.appbartitle.setText("Home Service");
        binding.hstoolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.hsNameTextview.setText(User.Fullname);
        binding.hsMapAddressTextview.setText(User.getAddress().toString());
        Log.d(TAG, "onCreate:fd " + binding.hsMapAddressTextview.getText());
        binding.hsPhonenumberTextview.setText(User.Phonenumber);
        binding.hsEmailTextview.setText(User.Emailid);
        binding.hsPhonenumber1Edittext.setText(getIntent().getStringExtra("PhoneNumber2"));
        binding.hsAddressEdittext.setText(getIntent().getStringExtra("AddressDetails"));
        binding.hsMapAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHomeServiceActivity.this, CustomerAddressMapsActivity.class);
                intent.putExtra("PhoneNumber2", binding.hsPhonenumber1Edittext.getText().toString());
                intent.putExtra("AddressDetails", binding.hsAddressEdittext.getText().toString());
                intent.putExtra("Activity", CustomerBeautyServiceActivity.class);
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
            binding.hsMapAddressTextview.setText(intent.getStringExtra("Address"));
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
        binding.hsNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onCreate: "+finalLatitude);
                Intent intent1 = new Intent(CustomerHomeServiceActivity.this, CustomerHomeserviceTimeActivity.class);
                intent1.putExtra("Name", binding.hsNameTextview.getText().toString());
                intent1.putExtra("PhoneNumber", binding.hsPhonenumberTextview.getText().toString());
                intent1.putExtra("PhoneNumber2", binding.hsPhonenumber1Edittext.getText().toString());
                intent1.putExtra("Email", binding.hsEmailTextview.getText().toString());
                intent1.putExtra("AddressMap", binding.hsMapAddressTextview.getText().toString());
                intent1.putExtra("AddressDetails", binding.hsAddressEdittext.getText().toString());
                intent1.putExtra("Service", "Home Service");
                intent1.putExtra("Latitude", String.valueOf(finalLatitude));
                intent1.putExtra("Longitude",String.valueOf(finalLongitude));
                intent1.putExtra("PhoneNumber2", binding.hsPhonenumber1Edittext.getText().toString());
                if (binding.hsMapAddressTextview.getText().toString().isEmpty()) {
                    binding.hsMapAddressButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                    binding.hsMapAddressTextview.setError("Pick Address from map");
                } else {
                    startActivity(intent1);
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}