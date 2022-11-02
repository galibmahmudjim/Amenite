package com.example.amenite.Employee;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import com.example.amenite.Employee.AppointmentReq.EmployeeAppointmentReqListFragment;
import com.example.amenite.Employee.Appointmentlist.EmployeeAppointmentListFragment;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.Welcome;
import com.example.amenite.databinding.ActivityEmployeeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityEmployeeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.EmployeeNavigationDrawerDrawerLayout.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.EmployeeNavigationDrawerDrawerLayout, binding.EmployeeNavigationDrawerToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.EmployeeNavigationDrawerDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        int intentFragment = 0;
        if (getIntent().getStringExtra("currentFragment") != null)
            intentFragment = Integer.parseInt(getIntent().getStringExtra("currentFragment"));
        if (savedInstanceState == null && getIntent().getStringExtra("currentFragment")==null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.EmployeeActivityFragmentContainer, new EmployeeHomeFragment())
                    .commit();
            binding.EmployeeActivityToolbarTextview.setText("Home");
            binding.EmployeeNavigationDrawerNavigationview.setCheckedItem(R.id.employeeMenuHome);
        } else if (intentFragment == 25) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.EmployeeActivityFragmentContainer, new EmployeeProfileFragment())
                    .commit();
            binding.EmployeeActivityToolbarTextview.setText("Profile");
            binding.EmployeeNavigationDrawerNavigationview.setCheckedItem(R.id.employeeMenuProfile);
        }else if (intentFragment == 26) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.EmployeeActivityFragmentContainer, new EmployeeAppointmentListFragment())
                    .commit();
            binding.EmployeeActivityToolbarTextview.setText("Appointment");
            binding.EmployeeNavigationDrawerNavigationview.setCheckedItem(R.id.employeeMenuAppointment);
        }else if (intentFragment == 27) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.EmployeeActivityFragmentContainer, new EmployeeAppointmentReqListFragment())
                    .commit();
            binding.EmployeeActivityToolbarTextview.setText("Appointment Request");
            binding.EmployeeNavigationDrawerNavigationview.setCheckedItem(R.id.employeeMenuAppointmentRequest);
        }

        binding.EmployeeNavigationDrawerNavigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.employeeMenuAppointment:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.EmployeeActivityFragmentContainer, new EmployeeAppointmentListFragment())
                                .commit();
                        binding.EmployeeActivityToolbarTextview.setText("Appointment");

                        break;
                    case R.id.employeeMenuProfile:
                        Task t1 = User.RetriveData();
                        Tasks.whenAllSuccess(t1).
                                addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                                    @Override
                                    public void onSuccess(List<Object> objects) {
                                        getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.EmployeeActivityFragmentContainer, new EmployeeProfileFragment())
                                                .commit();
                                        binding.EmployeeActivityToolbarTextview.setText("Profile");
                                    }
                                });
                        break;

                    case R.id.employeeMenuHome:

                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.EmployeeActivityFragmentContainer, new EmployeeHomeFragment())
                                        .
                                commit();
                        binding.EmployeeActivityToolbarTextview.setText("Home");
                        break;
                    case R.id.employeeMenuAppointmentRequest:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.EmployeeActivityFragmentContainer, new EmployeeAppointmentReqListFragment())
                                .commit();
                        binding.EmployeeActivityToolbarTextview.setText("Appointment Request");
                        break;
                    case R.id.employeeMenuContactus:

                        getSupportFragmentManager().
                                beginTransaction()
                                        .
                                replace(R.id.EmployeeActivityFragmentContainer, new ContactUsFragment())
                                        .
                                commit();
                        binding.EmployeeActivityToolbarTextview.setText("Contact Us");
                        break;
                    case R.id.ExitApp:
                        AlertDialog.Builder exitapp = new AlertDialog.Builder(EmployeeActivity.this);
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

                    case R.id.employeeMenuLogout:
                        AlertDialog.Builder confirmlogout = new AlertDialog.Builder(EmployeeActivity.this);
                        confirmlogout.setMessage("Do you want to Logout?");
                        confirmlogout.setTitle("Logout");
                        confirmlogout.setCancelable(false);
                        confirmlogout.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) ->

                        {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(EmployeeActivity.this, Welcome.class));
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
                binding.EmployeeNavigationDrawerDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {
        if (binding.EmployeeNavigationDrawerDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.EmployeeNavigationDrawerDrawerLayout.closeDrawer(GravityCompat.START);
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
