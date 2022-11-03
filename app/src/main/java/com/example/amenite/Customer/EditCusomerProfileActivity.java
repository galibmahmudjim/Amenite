package com.example.amenite.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Employee.EditEmployeeProfileActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityEditCusomerProfileBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.time.Month;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EditCusomerProfileActivity extends AppCompatActivity {
    private static final String TAG = "Amenite_check";
    private ActivityEditCusomerProfileBinding binding;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCusomerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.appbartitle.setText("Edit Profile");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //image
        //Image pick
        binding.customereeditprofilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(EditCusomerProfileActivity.this)
                        .crop()
                        .maxResultSize(1024, 1024)
                        .start();
            }
        });
        //end
        if(User.Profile_Pic==" ")
        {
            Glide.with(EditCusomerProfileActivity.this)
                    .load("android.resource://"+getPackageName()+"/drawable/profile")
                    .into(binding.customereeditprofilePic);

            binding.EditProfileEmailTextview.setText(User.Emailid);
            binding.EditProfileUsernameTextview.setText(User.getUsername());
            binding.EditProfilePhonenumberTextview.setText(User.Phonenumber);
            binding.EditProfileNameEdittext.setText(User.Fullname);
        }
        else
        {
            Glide.with(EditCusomerProfileActivity.this)
                    .load(User.Profile_Pic)
                    .into(binding.customereeditprofilePic);
            binding.EditProfileEmailTextview.setText(User.Emailid);
            binding.EditProfileUsernameTextview.setText(User.getUsername());
            binding.EditProfilePhonenumberTextview.setText(User.Phonenumber);
            binding.EditProfileNameEdittext.setText(User.Fullname);
        }
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
                double Latitude = 0;
                double Longitude = 0;
                Intent intent = new Intent(EditCusomerProfileActivity.this, CustomerActivity.class);
                intent.putExtra("currentFragment", "25");
                int radio = binding.EditCustomerRadioGroup.getCheckedRadioButtonId();
                RadioButton red = findViewById(radio);
                String Gender = " ";
                if (getIntent().getStringExtra("Address") != null) {
                    Address = getIntent().getStringExtra("Address");
                    Latitude = getIntent().getDoubleExtra("Latitude", 0.0);
                    Longitude = getIntent().getDoubleExtra("Longitude", 0.0);
                } else {
                    if (User.Address != null) {
                        Address = User.Address;
                        Latitude = Double.parseDouble(User.Latitude);
                        Longitude = Double.parseDouble(User.Longitude);

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

                dBresources.database.collection("User").document(dBresources.firebaseAuth.getUid())
                        .update(
                                "Name", name,
                                "Address", Address,
                                "Gender", Gender,
                                "Latitude", Latitude,
                                "Longitude", Longitude,
                                "Date_of_Birth", binding.EditProfileDOBTextview.getText().toString()
                        );
                //Uploading image
                Log.d(TAG, "onClick: " + imageUri);
                if (imageUri != null) {
                    ProgressDialog progressDialog = new ProgressDialog(EditCusomerProfileActivity.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    dBresources.firebaseStorage.getReference().child(User.Emailid + ".jpg")
                            .putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    binding.customereeditprofilePic.setImageURI(null);
                                    dBresources.firebaseStorage.getReference().child(User.Emailid + ".jpg")
                                            .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    dBresources.database.collection("User")
                                                            .document(User.UserID).update("Profile_Pic", uri)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    progressDialog.dismiss();
                                                                }
                                                            });
                                                }
                                            });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    // Error, Image not uploaded
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(EditCusomerProfileActivity.this,
                                                    "Failed " + e.getMessage(),
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
                }
                //end
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {

            binding.customereeditprofilePic.setImageURI(data.getData());
            imageUri = data.getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}