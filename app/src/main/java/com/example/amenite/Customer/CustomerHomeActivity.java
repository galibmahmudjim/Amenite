package com.example.amenite.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.Customer.Services.BeautyServiceActivity;
import com.example.amenite.Customer.Services.ElecrtronicandAppliancesServiceActivity;
import com.example.amenite.Customer.Services.HomeServiceActivity;
import com.example.amenite.Customer.Services.TravelServiceActivites;
import com.google.android.material.navigation.NavigationView;

public class CustomerHomeActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private Button customerHomeActivityTravelButton;
    private Button customerHomeActivityHomeserviceButton;
    private Button customerHomeActivityContactusButton;
    private Button customerHomeActivityDonationButton;
    private Button customerHomeActivityBeautyButton;
    private Button customerHomeActivityElectronicandappliancesButton;
    private DrawerLayout customerHomeActivityDrawerlayout;
    private NavigationView customerHomeActivityNavigationview;
    private Toolbar customerHomeActivityToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User user = new User();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        customerHomeActivityTravelButton = findViewById(R.id.CustomerHomeActivityTravelButton);
        customerHomeActivityHomeserviceButton = findViewById(R.id.CustomerHomeActivityHomeserviceButton);
        customerHomeActivityContactusButton = findViewById(R.id.CustomerHomeActivityContactusButton);
        customerHomeActivityDonationButton = findViewById(R.id.CustomerHomeActivityDonationButton);
        customerHomeActivityBeautyButton = findViewById(R.id.CustomerHomeActivityBeautyButton);
        Button customerHomeActivityElectronicandappliancesButton = findViewById(R.id.CustomerHomeActivityElectronicandappliancesButton);
        customerHomeActivityDrawerlayout = findViewById(R.id.customer_drawer_layout);
        customerHomeActivityNavigationview = findViewById(R.id.customerHomeActivityNavigationview);
        customerHomeActivityToolbar = findViewById(R.id.customerHomeActivityToolbar);
        customerHomeActivityNavigationview.bringToFront();
        setSupportActionBar(customerHomeActivityToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, customerHomeActivityDrawerlayout, customerHomeActivityToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        customerHomeActivityDrawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        customerHomeActivityHomeserviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHomeActivity.this, HomeServiceActivity.class));
            }
        });
        customerHomeActivityTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHomeActivity.this, TravelServiceActivites.class));
            }
        });
        customerHomeActivityElectronicandappliancesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHomeActivity.this, ElecrtronicandAppliancesServiceActivity.class));

            }
        });
        customerHomeActivityBeautyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHomeActivity.this, BeautyServiceActivity.class));

            }
        });
        customerHomeActivityDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHomeActivity.this, CustomerDonationActivity.class));
            }
        });
        customerHomeActivityContactusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHomeActivity.this, ContactUsActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (customerHomeActivityDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            customerHomeActivityDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    dr

}