package com.example.amenite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.amenite.Admin.AdminHomeActivity;
import com.example.amenite.Customer.CustomerHomeActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "Amenite_check";
    private EditText loginActivityEmailEditText;
    private EditText loginActivityPasswordEditText;
    private Button loginActivityLoginButton;
    private ProgressBar loginActivityloadingProgressbar;
    DBresources dBresources = new DBresources();
    User user = new User();


    private void Login() {
        loginActivityloadingProgressbar.setVisibility(View.VISIBLE);
            Task t1 = dBresources.database.collection("User").whereEqualTo("Email",loginActivityEmailEditText.getText().toString()).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if(task.isSuccessful())
                            {
                                if(!querySnapshot.isEmpty())
                                {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        user.Emailid= (String) document.get("Email");
                                        user.password= (String) document.get("Password");
                                        user.Phonenumber= (String) document.get("Phone_Number");
                                        user.UserID=document.getId();
                                        user.Role= (String) document.get("Role");
                                        user.Username = (String) document.get("Username");
                                        if(!user.password.equals(loginActivityPasswordEditText.getText().toString()))
                                        {
                                            loginActivityPasswordEditText.setError("Incorrect Password");
                                            loginActivityloadingProgressbar.setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            if(user.Role=="Customer") {
                                                loginActivityPasswordEditText.setError("Incorrect Password");
                                                startActivity(new Intent(LoginActivity.this, CustomerHomeActivity.class));
                                            }
                                            else {
                                                startActivity((new Intent(LoginActivity.this, AdminHomeActivity.class)));
                                            }
                                        }
                                        Log.d(TAG, document.getId() + " => " +user.password+" "+loginActivityPasswordEditText.getText().toString());
                                    }
                                }
                            }
                        }
                    });
        Tasks.whenAllSuccess(t1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
                if(loginActivityEmailEditText.getError()==null && loginActivityPasswordEditText.getError()==null)
                {
                    authverify(user.Phonenumber);
                }
            }
        });
    }

    private void authverify(String phonenumber) {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivityEmailEditText = findViewById(R.id.LoginActivityEmailEdittext);
        loginActivityPasswordEditText = findViewById(R.id.LoginActivityPasswordEdittext);
        loginActivityLoginButton = findViewById(R.id.LoginActivityLoginButton);
        loginActivityloadingProgressbar = findViewById(R.id.LoginActivityLoadingProgressBar);
        loginActivityloadingProgressbar.setVisibility(View.GONE);
        loginActivityLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!loginActivityEmailEditText.getText().toString().isEmpty())
                {
                    Login();
                }
                else
                {
                    if(loginActivityEmailEditText.getText().toString().isEmpty())
                    {
                        loginActivityEmailEditText.setError("Incorrent Email");
                    }
                    else
                    {
                        loginActivityPasswordEditText.setError("Incorrect Password");
                    }
                }
            }
        });
    }


}