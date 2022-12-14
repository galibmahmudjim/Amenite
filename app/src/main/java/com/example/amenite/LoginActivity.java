package com.example.amenite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.amenite.Admin.AdminHomeActivity;
import com.example.amenite.Customer.CustomerActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Employee.EmployeeActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.SendNotificationPack.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "Amenite_check";
    private EditText loginActivityEmailEditText;
    private TextInputEditText loginActivityPasswordEditText;
    private Button loginActivityLoginButton;
    private TextInputLayout loginPasswordLayout;
    private ProgressBar loginActivityloadingProgressbar;
    DBresources dBresources = new DBresources();


    private void Login() {
        loginActivityloadingProgressbar.setVisibility(View.VISIBLE);
        Task t1 = dBresources.database.collection("User").whereEqualTo("Email", loginActivityEmailEditText.getText().toString()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (task.isSuccessful()) {
                            if (!querySnapshot.isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User.Fullname = document.get("Name").toString();
                                    User.Emailid = document.get("Email").toString();
                                    User.password = document.get("Password").toString();
                                    User.Phonenumber = document.get("Phone_Number").toString();
                                    User.UserID = document.getId().toString();
                                    User.Fullname = document.get("Name").toString();
                                    User.Role = document.get("Role").toString();
                                    User.Username = (String) document.get("Username");
                                    if (!User.password.equals(loginActivityPasswordEditText.getText().toString())) {
                                        loginPasswordLayout.setError("Incorrect Password");
                                        loginActivityloadingProgressbar.setVisibility(View.GONE);
                                    } else {
                                        dBresources.firebaseAuth.signInWithEmailAndPassword(User.Emailid, User.password)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            Token token = new Token();
                                                            token.saveToken();
                                                            dBresources.firebaseUser = dBresources.firebaseAuth.getCurrentUser();
                                                            User.RetriveData().addOnSuccessListener(new OnSuccessListener() {
                                                                @Override
                                                                public void onSuccess(Object o) {

                                                                    if (User.Role.equals("Customer")) {
                                                                        startActivity(new Intent(LoginActivity.this, CustomerActivity.class));
                                                                        finish();
                                                                    } else if (User.Role.equals("Admin")) {
                                                                        startActivity((new Intent(LoginActivity.this, AdminHomeActivity.class)));
                                                                        finish();
                                                                    } else if (User.Role.equals("Employee")) {
                                                                        startActivity((new Intent(LoginActivity.this, EmployeeActivity.class)));
                                                                        finish();
                                                                    }
                                                                }
                                                            });


                                                        } else {
                                                            User.Emailid = null;
                                                            User.Role = null;
                                                            User.Username = null;
                                                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    }
                                }
                            } else {
                                loginActivityEmailEditText.setError("Email not found");
                                loginActivityloadingProgressbar.setVisibility(View.GONE);
                            }
                        }
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPasswordLayout = findViewById(R.id.LoginPasswordLayout);
        loginActivityEmailEditText = findViewById(R.id.LoginActivityEmailEdittext);
        loginActivityPasswordEditText = findViewById(R.id.LoginActivityPasswordEdittext);
        loginActivityLoginButton = findViewById(R.id.LoginActivityLoginButton);
        loginActivityloadingProgressbar = findViewById(R.id.LoginActivityLoadingProgressBar);
        loginActivityloadingProgressbar.setVisibility(View.GONE);
        loginPasswordLayout.setErrorIconDrawable(null);
        loginActivityLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!loginActivityEmailEditText.getText().toString().isEmpty()) {
                    Login();
                } else {
                    if (loginActivityEmailEditText.getText().toString().isEmpty()) {
                        loginActivityEmailEditText.setError("Incorrent Email");
                    } else {
                        loginPasswordLayout.setError("Incorrect Password");
                    }
                }
            }
        });
    }
}