package com.example.amenite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    private final String TAG = "Amenite_check";
    private String username;
    private String email;
    private String password;
    private String passwordconfirm;
    public void signup( EditText Username, EditText Email, EditText Phonenumber, EditText Password, EditText PasswordConfirm, ProgressBar progressBar, View view) throws InterruptedException {
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
        //dBresources.Signup(Email.getText().toString(),Password.getText().toString(),Phonenumber.getText().toString(),Username.getText().toString());
        Query emailquery = dBresources.database.collection("User").whereEqualTo("Email",Email.getText().toString());
        Query phonequery = dBresources.database.collection("User").whereEqualTo("Phone_Number",Phonenumber.getText().toString());
        Query usernamequery = dBresources.database.collection("User").whereEqualTo("Username",Username.getText().toString());
        Task t1 = emailquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "onComplete: in");
                QuerySnapshot querySnapshot = task.getResult();
                if(task.isSuccessful())
                {
                    if(!querySnapshot.isEmpty())
                    {
                        Log.d(TAG, "onComplete: "+querySnapshot.getQuery());
                        Email.setError("This Email is already in use.");
                    }
                    else
                    {
                        //   Email.setError(null);
                    }
                }
                else
                {
                    Log.d(TAG, "Failed: "+task.getException());
                }
            }

        });
       Task t2 = phonequery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "onComplete: in");
                QuerySnapshot querySnapshot = task.getResult();
                if(task.isSuccessful())
                {
                    if(!querySnapshot.isEmpty())
                    {
                        //Log.d(TAG, "onComplete: ");
                        Phonenumber.setError("This Phone Number is already in use.");
                    }
                    else
                    {
                        // Phonenumber.setError(null);
                    }
                }
                else
                {
                    Log.d(TAG, "Failed: "+task.getException());
                }
            }
        });
        Task t3 = usernamequery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "onComplete: in");
                QuerySnapshot querySnapshot = task.getResult();
                if(task.isSuccessful())
                {
                    if(!querySnapshot.isEmpty())
                    {
                        //Log.d(TAG, "onComplete: ");
                        Username.setError("Username Taken");
                    }
                    else
                    {
                        // Username.setError(null);
                    }
                }
                else
                {
                    Log.d(TAG, "Failed: "+task.getException());
                }
            }
        });

        Tasks.whenAllSuccess(t1,t2,t3).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
                progressBar.setVisibility(View.GONE);
                if(Username.getError()==null&&Email.getError()==null&&Phonenumber.getError()==null&&Password.getError()==null)
                {
                    Map<String,String> users = dBresources.Signup(Email.getText().toString(),Password.getText().toString(),Phonenumber.getText().toString(),Username.getText().toString());
                    dBresources.database.collection("User").document(Username.getText().toString()).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: Successfull");
                            Toast.makeText(SignupActivity.this,"Registration complete",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                        }
                    });

                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText signupActivityUsernameEdittext = (EditText) findViewById(R.id.SignupActivityUsernameEdittext);
        EditText signupActivityEmailEdittext = (EditText) findViewById(R.id.SignupActivityEmailEdittext);
        EditText signupActivityPasswordEdittext = (EditText) findViewById(R.id.SignupActivityPasswordEdittext);
        EditText signupActivityConfirmPasswordEdittext = (EditText) findViewById(R.id.SignupActivityConfirmPasswordEdittext);
        EditText signupActivityPhonenumberEdittext = (EditText)findViewById(R.id.SignupActivityPhonenumberEdittext);
        ProgressBar signupActivityLoadingProgressBar = (ProgressBar) findViewById(R.id.SignupActivityLoadingProgressBar);
        signupActivityLoadingProgressBar.setVisibility(View.GONE);
        findViewById(R.id.SignupActivitySubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                   signup(signupActivityUsernameEdittext,signupActivityEmailEdittext,signupActivityPhonenumberEdittext,signupActivityPasswordEdittext,signupActivityConfirmPasswordEdittext,signupActivityLoadingProgressBar, view);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}