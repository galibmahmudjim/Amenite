package com.example.amenite.Customer.Services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.amenite.Customer.CustomerAddressMapsActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerBeautyServiceBinding;
import com.example.amenite.databinding.ToolbarBinding;

public class CustomerBeautyServiceActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerBeautyServiceBinding activityCustomerBeautyServiceBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCustomerBeautyServiceBinding = ActivityCustomerBeautyServiceBinding.inflate(getLayoutInflater());
        setContentView(activityCustomerBeautyServiceBinding.getRoot());
        activityCustomerBeautyServiceBinding.toolbar.appbartitle.setText("Beauty and Salon");
        activityCustomerBeautyServiceBinding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentNameTextview.setText(User.Fullname);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.setText(User.getAddress().toString());
        Log.d(TAG, "onCreate:fd " + activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.getText());
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumberTextview.setText(User.Phonenumber);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentEmailTextview.setText(User.Emailid);
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumber1Edittext.setText(getIntent().getStringExtra("PhoneNumber2"));
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentAddressEdittext.setText(getIntent().getStringExtra("AddressDetails"));
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerBeautyServiceActivity.this, CustomerAddressMapsActivity.class);
                intent.putExtra("PhoneNumber2", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumber1Edittext.getText().toString());
                intent.putExtra("AddressDetails", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentAddressEdittext.getText().toString());
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
            activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.setText(intent.getStringExtra("Address"));
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
        activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onCreate: "+finalLatitude);
                Intent intent1 = new Intent(CustomerBeautyServiceActivity.this, CustomerChooseBeautyServiceActivity.class);
                intent1.putExtra("Name", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentNameTextview.getText().toString());
                intent1.putExtra("PhoneNumber", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumberTextview.getText().toString());
                intent1.putExtra("PhoneNumber2", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumber1Edittext.getText().toString());
                intent1.putExtra("Email", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentEmailTextview.getText().toString());
                intent1.putExtra("AddressMap", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.getText().toString());
                intent1.putExtra("AddressDetails", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentAddressEdittext.getText().toString());
                intent1.putExtra("Service", "Beauty");
                intent1.putExtra("Latitude", String.valueOf(finalLatitude));
                intent1.putExtra("Longitude",String.valueOf(finalLongitude));
                intent1.putExtra("PhoneNumber2", activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentPhonenumber1Edittext.getText().toString());
                if (activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.getText().toString().isEmpty()) {
                    activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressButton.setBackgroundColor(Color.parseColor("#A41E2C29"));
                    activityCustomerBeautyServiceBinding.CutomerBeautyAppointmentMapAddressTextview.setError("Pick Address from map");
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