package com.example.amenite.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.LoginActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeEditProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    DBresources dBresources = new DBresources();
    private Map<String, String> userDetails;
    private FirebaseAuth mAuth;

    String UserId;
// ...


 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_employee__edit_profile_);
     TextView Username=findViewById(R.id.EmployeeEditProfileNameEdittext);
     TextView Email=findViewById(R.id.EmployeeEditGmailEdittext);
     TextView Phonenumber=findViewById(R.id.EmployeeEditProfilePhoneNumberEdittext);


     Query emailquery = dBresources.database.collection("User").whereEqualTo("Email",Email.getText().toString());
     Query phonequery = dBresources.database.collection("User").whereEqualTo("Phone_Number",Phonenumber.getText().toString());
     Query usernamequery = dBresources.database.collection("User").whereEqualTo("Username",Username.getText().toString());



     Task t1 = emailquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
         @Override
         public void onComplete(@NonNull Task<QuerySnapshot> task) {
             QuerySnapshot querySnapshot = task.getResult();
             if(task.isSuccessful())
             {
                 if(!querySnapshot.isEmpty())
                 {
                     Email.setError("This Email is already in use.");
                 }
             }
             else
             {
             }
         }

     });

     Task t2 = phonequery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
         @Override
         public void onComplete(@NonNull Task<QuerySnapshot> task) {
             QuerySnapshot querySnapshot = task.getResult();
             if(task.isSuccessful())
             {
                 if(!querySnapshot.isEmpty())
                 {
                     Phonenumber.setError("This Phone Number is already in use.");
                 }
             }
             else
             {
             }
         }
     });
     Task t3 = usernamequery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
         @Override
         public void onComplete(@NonNull Task<QuerySnapshot> task) {
             QuerySnapshot querySnapshot = task.getResult();
             if (task.isSuccessful()) {
                 if (!querySnapshot.isEmpty()) {
                     Username.setError("Username Taken");
                 }
             }
             else
             {
             }
         }
     });

     Tasks.whenAllSuccess(t1,t2,t3).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
         @Override
         public void onSuccess(List<Object> objects) {
             if( Username.getError()==null && Email.getError()==null&& Phonenumber.getError()==null)
             {
                 userDetails = new HashMap<>();
                 userDetails.put("Email",Email.getText().toString());
                 userDetails.put("Username",Username.getText().toString());
                 userDetails.put("Phone_Number",Phonenumber.getText().toString());
                 dBresources.database.collection("User").document(User.UserID).set(userDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
             }

         }
     });




 }
}