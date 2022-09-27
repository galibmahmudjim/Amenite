package com.example.amenite.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.amenite.R;
import com.example.amenite.Welcome;
import com.example.amenite.databinding.ActivityCustomerBinding;
import com.example.amenite.databinding.CustomerMenuHeaderBinding;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityCustomerBinding activityCustomerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCustomerBinding = activityCustomerBinding.inflate(getLayoutInflater());
        View view = activityCustomerBinding.getRoot();
        setContentView(view);
        activityCustomerBinding.CustomerNavigationDrawerDrawerLayout.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, activityCustomerBinding.CustomerNavigationDrawerDrawerLayout, activityCustomerBinding.CustomerNavigationDrawerToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        activityCustomerBinding.CustomerNavigationDrawerDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.CustomerActivityFragmentContainer, new CustomerHomeFragment())
                    .commit();
            activityCustomerBinding.CustomerActivityToolbarTextview.setText("Home");
            activityCustomerBinding.CustomerNavigationDrawerNavigationview.setCheckedItem(R.id.customerMenuHome);

        }

        activityCustomerBinding.CustomerNavigationDrawerNavigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.customerMenuHome:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.CustomerActivityFragmentContainer, new CustomerHomeFragment())
                                .commit();
                        activityCustomerBinding.CustomerActivityToolbarTextview.setText("Home");
                        activityCustomerBinding.CustomerNavigationDrawerNavigationview.setCheckedItem(R.id.customerMenuHome);

                        break;
                    case R.id.customerMenuLogout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(CustomerActivity.this, Welcome.class));
                        break;
                }
                activityCustomerBinding.CustomerNavigationDrawerDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (activityCustomerBinding.CustomerNavigationDrawerDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityCustomerBinding.CustomerNavigationDrawerDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
