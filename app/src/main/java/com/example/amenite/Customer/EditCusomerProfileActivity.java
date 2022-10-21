package com.example.amenite.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.amenite.Customer.Services.CustomerBeautyAppointmentConfirmActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityEditCusomerProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.SetOptions;

import java.time.Month;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EditCusomerProfileActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityEditCusomerProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCusomerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.EditProfileEmailTextview.setText(User.Emailid);
        binding.EditProfileUsernameTextview.setText(User.getUsername());
        binding.EditProfilePhonenumberTextview.setText(User.Phonenumber);
        binding.EditProfileNameEdittext.setText(User.Fullname);
        binding.EditProfileAddresstextview.setText(getIntent().getStringExtra("Address"));
        if (getIntent().getStringExtra("Name") != null) {
            binding.EditProfileNameEdittext.setText(getIntent().getStringExtra("Name"));
        } else {
            binding.EditProfileNameEdittext.setText(User.Fullname);
        }
        if (getIntent().getStringExtra("Address") != null) {
            binding.EditProfileAddresstextview.setText(getIntent().getStringExtra("Address"));
        } else {
            binding.EditProfileAddresstextview.setText(User.Address);
        }
        if (getIntent().getStringExtra("Date_of_Birth") != null) {
            binding.EditProfileDOBTextview.setText(getIntent().getStringExtra("Date_of_Birth"));
        } else {
            binding.EditProfileDOBTextview.setText(User.Date_of_Birth);
        }
        binding.EditProfileDOBTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePick();
            }
        });

        binding.EditprofileMylocationiconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:f ");
                Intent intent = new Intent(EditCusomerProfileActivity.this, CustomerAddressMapsActivity.class);
                intent.putExtra("Activity", EditCusomerProfileActivity.class);
                intent.putExtra("Name", binding.EditProfileNameEdittext.getText().toString());
                intent.putExtra("Date_of_Birth", binding.EditProfileDOBTextview.getText().toString());
                startActivity(intent);
            }
        });
        binding.EditProfilUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Address = " ";
                Intent intent = new Intent(EditCusomerProfileActivity.this, CustomerActivity.class);
                intent.putExtra("currentFragment", "25");
                int radio = binding.EditCustomerRadioGroup.getCheckedRadioButtonId();
                RadioButton red = findViewById(radio);
                String Gender = " ";
                if (getIntent().getStringExtra("Address") != null) {
                    Address = getIntent().getStringExtra("Address");
                }
                else
                {
                    if(User.Address!=null)
                    {
                        Address=User.Address;
                    }
                }
                if (red != null) {
                    Gender = red.getText().toString();
                }
                String name = " ";
                if (binding.EditProfileNameEdittext.getText() != null) {
                    name = binding.EditProfileNameEdittext.getText().toString();
                } else {
                    if (User.Fullname != null) {
                        name = User.Fullname;
                    }
                }
                String DOB = " ";
                if (binding.EditProfileDOBTextview.getText() != null) {
                    DOB = binding.EditProfileDOBTextview.getText().toString();
                } else {
                    if (User.Date_of_Birth != null) {
                        name = User.Date_of_Birth;
                    }

                }
                DBresources dBresources = new DBresources();
                HashMap<String, String> userdata = new HashMap<>();
                userdata.put("Address", Address);
                userdata.put("Gender", Gender);
                userdata.put("Date_of_Birth", DOB);

                dBresources.database.collection("User").document(dBresources.firebaseAuth.getUid())
                        .update(
                                "Name", name,
                                "Address", Address,
                                "Gender", Gender,
                                "Date_of_Birth", binding.EditProfileDOBTextview.getText().toString()
                        );
                Task t1 = User.RetriveData();
                Tasks.whenAllSuccess(t1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> objects) {
                        startActivity(intent);
                    }
                });

            }
        });
    }

    private void DatePick() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.EditProfileDOBTextview.setText(i2 + "-" + Month.of(i1 + 1) + "-" + i);
            }
        }, year, month, day);
        datePickerDialog.show();

    }

}