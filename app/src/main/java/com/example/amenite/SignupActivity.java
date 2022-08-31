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
import android.widget.Toast;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneMultiFactorAssertion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class SignupActivity extends AppCompatActivity {
    private EditText signupActivityUsernameEdittext;
    private EditText signupActivityEmailEdittext;
    private EditText signupActivityPasswordEdittext;
    private EditText signupActivityConfirmPasswordEdittext;
    private EditText signupActivityPhonenumberEdittext;
    private ProgressBar signupActivityLoadingProgressBar;
    private EditText signupActivityOtpEdittext;
    private Button signupActivitySubmitButton;

    private final String TAG = "Amenite_check";
    private String username;
    private String email;
    private String phonenumber;
    private String password;
    private String passwordconfirm;
    private FirebaseAuth mAuth;
    private Boolean verificationInProcess = false;
    String VerificationID;
    PhoneAuthProvider.ForceResendingToken token;

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
        if(Phonenumber.getText().toString().length()==14)
        {
            phonenumber = Phonenumber.getText().toString();
        }
        else if(Phonenumber.getText().toString().length()==11)
        {
            phonenumber ="+88"+ Phonenumber.getText().toString();
        }
        else
        {
            Phonenumber.setError("Invalid Number");
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
                if(Username.getError()==null&&Email.getError()==null&&Phonenumber.getError()==null&&Password.getError()==null)
                {
                     Map<String,String> users = dBresources.Signup(Email.getText().toString(),Password.getText().toString(),Phonenumber.getText().toString(),Username.getText().toString());
                     requestOTP(phonenumber);
                }

            }
        });
    }

    private void requestOTP(String phonenumber) {
        mAuth=FirebaseAuth.getInstance();
        PhoneAuthProvider.OnVerificationStateChangedCallbacks myCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                VerificationID = s;
                token = forceResendingToken;
                signupActivityOtpEdittext.setVisibility(View.VISIBLE);
                signupActivityLoadingProgressBar.setVisibility(View.GONE);
                signupActivitySubmitButton.setText("Verify");
                verificationInProcess = true;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
               // Toast.makeText(SignupActivity.this,"Cannot Create Acxount "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phonenumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(myCallBack)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    /*dBresources.database.collection("User").document(Username.getText().toString()).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
      //      @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: Successfull");
                Toast.makeText(SignupActivity.this,"Registration complete",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         signupActivityUsernameEdittext = (EditText) findViewById(R.id.SignupActivityUsernameEdittext);
         signupActivityEmailEdittext = (EditText) findViewById(R.id.SignupActivityEmailEdittext);
         signupActivityPasswordEdittext = (EditText) findViewById(R.id.SignupActivityPasswordEdittext);
         signupActivityConfirmPasswordEdittext = (EditText) findViewById(R.id.SignupActivityConfirmPasswordEdittext);
         signupActivityPhonenumberEdittext = (EditText)findViewById(R.id.SignupActivityPhonenumberEdittext);
         signupActivityLoadingProgressBar = (ProgressBar) findViewById(R.id.SignupActivityLoadingProgressBar);
         signupActivityOtpEdittext = (EditText) findViewById(R.id.SignupActivityOtpEdittext);
         signupActivityLoadingProgressBar.setVisibility(View.GONE);
         signupActivitySubmitButton = (Button)  findViewById(R.id.SignupActivitySubmitButton);
        findViewById(R.id.SignupActivitySubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                   if(verificationInProcess==false) {
                       signup(signupActivityUsernameEdittext, signupActivityEmailEdittext, signupActivityPhonenumberEdittext, signupActivityPasswordEdittext, signupActivityConfirmPasswordEdittext, signupActivityLoadingProgressBar, view);
                   }
                   else
                   {
                       String otpcode = signupActivityOtpEdittext.getText().toString();
                       if(!otpcode.isEmpty() && otpcode.length()==6)
                       {
                           PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationID,otpcode);
                           verifyAuth(credential);
                       }
                       else
                       {
                           signupActivityOtpEdittext.setError("Not valid otp");
                       }
                   }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void verifyAuth(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignupActivity.this,"Authentication is successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignupActivity.this,"Authentication failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}