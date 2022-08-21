package com.example.amenite.Signup;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Welcome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Signup extends SignupActivity {
    private static final String TAG = "Error";
    private String username;
    private String email;
    private String password;
    private String passwordconfirm;
    public static void signup( EditText Username, EditText Email, EditText Phonenumber, EditText Password, EditText PasswordConfirm, ProgressBar progressBar) throws InterruptedException {
        DBresources dBresources = new DBresources();
        progressBar.setVisibility(View.VISIBLE);
        if(Username.getText().toString().isEmpty())
        {
            Username.setError("Invalid Username");
        }
        if(Email.getText().toString().isEmpty())
        {
            Email.setError("Invalid Email");
        }
        if(Phonenumber.getText().toString().isEmpty())
        {
            Phonenumber.setError("Invalid Phone Number");
        }
        if(Password.getText().toString().isEmpty())
        {
            Password.setError("Invalid Password");
        }
        else if(Password.getText().toString().length()<8)
        {
            Password.setError("Password should be at least 8 characters");
        }
        if(PasswordConfirm.getText().toString().isEmpty()|| !PasswordConfirm.getText().toString().equals(Password.getText().toString()))
        {
            PasswordConfirm.setError("Please Enter the same password");
        }
        String mainnumber;
        if(Phonenumber.getText().toString().length()>11)
        {
            mainnumber = Phonenumber.getText().toString().substring(3);
        }
        else
        {
            mainnumber = Phonenumber.getText().toString();
        }
        dBresources.databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int username = 0;
                int email = 0;
                int phonenumber=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    if(Username.getText().toString().equals(dataSnapshot.child("Username").getValue(String.class).toString()) && !Username.getText().toString().isEmpty())
                    {
                        Log.e(TAG, "onDataChange: "+dataSnapshot.child("Username").getValue(String.class).toString() );
                        username=1;
                    }
                    if(Email.getText().toString().equals(dataSnapshot.child("Email").getValue(String.class).toString()) && !Email.getText().toString().isEmpty())
                    {
                        Log.e(TAG, "onDataChange:Email "+dataSnapshot.child("Email").getValue(String.class).toString() );
                        email = 1;
                    }

                    if(mainnumber.equals(dataSnapshot.child("Phone_Number").getValue(String.class).toString()) && !Phonenumber.getText().toString().isEmpty())
                    {
                        Log.e(TAG, "onDataChange:pn "+dataSnapshot.child("Phone_Number").getValue(String.class).toString() );
                        phonenumber = 1;
                    }
                }
                if(phonenumber==1)
                {
                    Phonenumber.setError("Already Registered");
                }
                if(email==1)
                {
                    Email.setError("Already have and account with this Email");
                }
                if(username==1)
                {
                    Username.setError("Username is already taken");
                }
                if(Username.getError()==null && Phonenumber.getError()==null && Password.getError()==null && PasswordConfirm.getError()==null&& Email.getError()==null) {
                    dBresources.Signup(Email.getText().toString(), Password.getText().toString(), Phonenumber.getText().toString(), Username.getText().toString());

                    Toast toast = Toast.makeText(progressBar.getContext(), "Signup Successfull", Toast.LENGTH_SHORT);
                    toast.show();

                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(progressBar.getContext(), "Signup Unsuccesful", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}
