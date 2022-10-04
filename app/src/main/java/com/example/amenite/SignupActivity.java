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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
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
    private EditText signupActivityFullnameEdittext;
    private final String TAG = "Amenite_check";
    private String username;
    private String email;
    private String phonenumber;
    private String password;
    private String passwordconfirm;
    private FirebaseAuth mAuth;
    private Map<String, String> userDetails;
    private Boolean verificationInProcess = false;
    String UserId;
    String VerificationID;
    PhoneAuthProvider.ForceResendingToken token;
    DBresources dBresources = new DBresources();

    public void signup( EditText Fullname, EditText Username, EditText Email, EditText Phonenumber, EditText Password, EditText PasswordConfirm, ProgressBar progressBar, View view) throws InterruptedException {
        progressBar.setVisibility(View.VISIBLE);
        System.out.println();
        if(Fullname.getText().toString().isEmpty())
        {
            Fullname.setError("Please Insert Your Name");
        }
        if(Username.getText().toString().isEmpty())
        {
            Username.setError("Invalid Username");
        }
        if(Email.getText().toString().isEmpty())
        {
            Email.setError("Invalid Email");
        }
        if(Phonenumber.getText().toString().length()!=11)
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
            phonenumber="";
        }
        Log.d(TAG, "signup: Phone"+phonenumber);

        Query emailquery = dBresources.database.collection("User").whereEqualTo("Email",Email.getText().toString());
        Query phonequery = dBresources.database.collection("User").whereEqualTo("Phone_Number",phonenumber);
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
                        Username.setError("Username Taken");
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

                    userDetails = new HashMap<>();
                    userDetails.put("Name",Fullname.getText().toString());
                    userDetails.put("Email",Email.getText().toString());
                    userDetails.put("Username",Username.getText().toString());
                    userDetails.put("Role","Customer");
                    userDetails.put("Phone_Number",phonenumber);
                    userDetails.put("Password",Password.getText().toString());
                     requestOTP(phonenumber);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
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
                .setPhoneNumber(phonenumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(myCallBack)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyAuth(AuthCredential credential) {
         Task t1 = mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
         Tasks.whenAllSuccess(t1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
             @Override
             public void onSuccess(List<Object> objects) {
                 UserId = dBresources.firebaseAuth.getCurrentUser().getUid();
                 Task t2 = dBresources.database.collection("User").document(UserId).set(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful())
                         {
                             AuthCredential credential1 = EmailAuthProvider.getCredential(signupActivityEmailEdittext.getText().toString(),signupActivityPasswordEdittext.getText().toString());
                             dBresources.firebaseAuth.getCurrentUser().linkWithCredential(credential1)
                                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                         @Override
                                         public void onComplete(@NonNull Task<AuthResult> task) {
                                             if(task.isSuccessful())
                                             {
                                                 Log.d(TAG, "onComplete: Success");
                                                 startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                                             }
                                             else
                                             {
                                                 Toast.makeText(SignupActivity.this,"Auth is failed",Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     });
                             signupActivityLoadingProgressBar.setVisibility(View.GONE);
                             dBresources.firebaseAuth.signOut();
                         }
                     }
                 });
             }
         });

    }

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
         signupActivityFullnameEdittext = findViewById(R.id.SignupActivityFullnameEditText);
         signupActivityOtpEdittext = (EditText) findViewById(R.id.SignupActivityOtpEdittext);
         signupActivityLoadingProgressBar.setVisibility(View.GONE);
         signupActivitySubmitButton = (Button)  findViewById(R.id.SignupActivitySubmitButton);
        findViewById(R.id.SignupActivitySubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(verificationInProcess==false) {
                     try {
                         signup(signupActivityFullnameEdittext, signupActivityUsernameEdittext, signupActivityEmailEdittext, signupActivityPhonenumberEdittext, signupActivityPasswordEdittext, signupActivityConfirmPasswordEdittext, signupActivityLoadingProgressBar, view);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
                 else
                 {
                     String otpcode = signupActivityOtpEdittext.getText().toString();
                     if(!otpcode.isEmpty() && otpcode.length()==6)
                     {
                         signupActivityLoadingProgressBar.setVisibility(View.VISIBLE);
                         AuthCredential credential = PhoneAuthProvider.getCredential(VerificationID,otpcode);
                         verifyAuth(credential);


                     }
                 }
            }
        });
    }
}