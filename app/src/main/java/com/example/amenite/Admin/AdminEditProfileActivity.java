package com.example.amenite.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.List;

public class AdminEditProfileActivity extends AppCompatActivity {

    String newName="";

    String newEmail="";

    String newPhone="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_profile);
        TextView Name=findViewById(R.id.AdminEditProfileNameEdittext);
        TextView Email=findViewById(R.id.AdminEditEmailEdittext);
        TextView Phone=findViewById(R.id.AdminEditProfilePhoneNumberEdittext);

        Name.setText(User.getUsername());
        Email.setText(User.getEmailid());
        Phone.setText(User.getPhonenumber());


        newName=User.getUsername();
        newEmail=User.getEmailid();
        newPhone=User.getPhonenumber();



        findViewById(R.id.AdminEditProfileUpdateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Name.getText().toString()!=null)
                {
                    newName=Name.getText().toString();

                }
                if(Email.getText().toString()!=null)
                {
                    newEmail=Email.getText().toString();

                }
                if(Phone.getText().toString()!=null)
                {
                    newPhone=Phone.getText().toString();

                }

                DBresources dBresources = new DBresources();

                dBresources.database.collection("User").document(dBresources.firebaseAuth.getUid())
                        .update(
                                "Username", newName,
                                "Email", newEmail,
                                "Phone_Number", newPhone

                        );
                Task t1 = User.RetriveData();
                Tasks.whenAllSuccess(t1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> objects) {

                        startActivity(new Intent(AdminEditProfileActivity.this,AdminProfileActivity.class ) );
                    }
                });



            }
        });

    }

}