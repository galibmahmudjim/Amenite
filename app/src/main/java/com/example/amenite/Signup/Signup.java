package com.example.amenite.Signup;

import android.widget.EditText;

import com.example.amenite.DBRes.DBresources;
import com.google.firebase.database.DatabaseReference;

import java.nio.file.attribute.UserDefinedFileAttributeView;

public class Signup {
    private String username;
    private String email;
    private String password;
    private String passwordconfirm;

    public static void signup(EditText Username, EditText Email, EditText Password, EditText PasswordConfirm ) throws InterruptedException {
        DBresources dBresources = new DBresources();
        if(Username.getText().toString().isEmpty())
        {
            Username.setError("Invalid Username");
        }
        if(Email.getText().toString().isEmpty())
        {
            Email.setError("Invalid Email Address");
        }
        if(Password.getText().toString().isEmpty())
        {
            Password.setError("Password can not be empty!");
        }
        if(PasswordConfirm.getText().toString().isEmpty())
        {
            PasswordConfirm.setError("Please enter the same password again");
        }
       // dBresources.listDays();

    }

}
