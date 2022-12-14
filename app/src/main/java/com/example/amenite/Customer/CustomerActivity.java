package com.example.amenite.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.amenite.Customer.Appointmentlist.AppointmentListFragment;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.Welcome;
import com.example.amenite.databinding.ActivityCustomerBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

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
        int intentFragment = 0;
        if (getIntent().getStringExtra("currentFragment") != null)
            intentFragment = Integer.parseInt(getIntent().getStringExtra("currentFragment"));
        if (savedInstanceState == null && intentFragment != 25) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.CustomerActivityFragmentContainer, new CustomerHomeFragment())
                    .commit();
            activityCustomerBinding.CustomerActivityToolbarTextview.setText("Home");
            activityCustomerBinding.CustomerNavigationDrawerNavigationview.setCheckedItem(R.id.customerMenuHome);
        } else if (intentFragment == 25) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.CustomerActivityFragmentContainer, new ProfileFragment())
                    .commit();
            activityCustomerBinding.CustomerActivityToolbarTextview.setText("Profile");
            activityCustomerBinding.CustomerNavigationDrawerNavigationview.setCheckedItem(R.id.customerMenuProfile);

        }

        activityCustomerBinding.CustomerNavigationDrawerNavigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.customerMenuAppointment:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.CustomerActivityFragmentContainer, new AppointmentListFragment())
                                .commit();
                        activityCustomerBinding.CustomerActivityToolbarTextview.setText("Appointment");

                        break;
                    case R.id.Joinus:
                        User.RetriveData();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.CustomerActivityFragmentContainer, new EmployeSignupFragment())
                                .commit();
                        activityCustomerBinding.CustomerActivityToolbarTextview.setText("Employee Registration");

                        break;
                    case R.id.customerMenuProfile:
                        Task t1 = User.RetriveData();
                        Tasks.whenAllSuccess(t1).
                                addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                                    @Override
                                    public void onSuccess(List<Object> objects) {
                                        getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.CustomerActivityFragmentContainer, new ProfileFragment())
                                                .commit();
                                        activityCustomerBinding.CustomerActivityToolbarTextview.setText("Profile");
                                    }
                                });
                        break;

                    case R.id.customerMenuHome:

                        getSupportFragmentManager().

                                beginTransaction()
                                        .

                                replace(R.id.CustomerActivityFragmentContainer, new CustomerHomeFragment())
                                        .

                                commit();
                        activityCustomerBinding.CustomerActivityToolbarTextview.setText("Home");
                        break;
                    case R.id.customerMenuContactus:

                        getSupportFragmentManager().

                                beginTransaction()
                                        .

                                replace(R.id.CustomerActivityFragmentContainer, new ContactUsFragment())
                                        .

                                commit();
                        activityCustomerBinding.CustomerActivityToolbarTextview.setText("Contact Us");
                        break;
                    case R.id.ExitApp:
                        AlertDialog.Builder exitapp = new AlertDialog.Builder(CustomerActivity.this);
                        exitapp.setMessage("Do you want to Exit?");
                        exitapp.setTitle("Exit");
                        exitapp.setCancelable(false);
                        exitapp.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) ->

                        {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        });
                        exitapp.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->

                        {
                            dialog.cancel();
                        });
                        AlertDialog exitalert = exitapp.create();
                        exitalert.show();
                        break;

                    case R.id.customerMenuLogout:
                        AlertDialog.Builder confirmlogout = new AlertDialog.Builder(CustomerActivity.this);
                        confirmlogout.setMessage("Do you want to Logout?");
                        confirmlogout.setTitle("Logout");
                        confirmlogout.setCancelable(false);
                        confirmlogout.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) ->

                        {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(CustomerActivity.this, Welcome.class));
                            finish();
                        });
                        confirmlogout.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->

                        {
                            dialog.cancel();
                        });
                        AlertDialog alertDialog = confirmlogout.create();
                        alertDialog.show();
                        break;
                }
                activityCustomerBinding.CustomerNavigationDrawerDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {
        if (activityCustomerBinding.CustomerNavigationDrawerDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityCustomerBinding.CustomerNavigationDrawerDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }
}
