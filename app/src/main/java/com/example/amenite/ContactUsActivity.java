package com.example.amenite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amenite.databinding.ActivityContactUsBinding;

import java.net.URI;


public class ContactUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_us);
        findViewById(R.id.ContactusactivitySendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText subjecttext = findViewById(R.id.ContactusactivitySubjectEdittext);
                EditText messegetext = findViewById(R.id.ContactusactivityMessegeEdittext);
                String subject = subjecttext.getText().toString();
                 String messege = messegetext.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amenite@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT,messege);
                System.out.println(intent.resolveActivity(getPackageManager()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                else
                    Toast.makeText(ContactUsActivity.this,"No apps installed", Toast.LENGTH_SHORT);
            }
        });

    }
}