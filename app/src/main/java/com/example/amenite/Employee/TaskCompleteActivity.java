package com.example.amenite.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.databinding.ActivityTaskCompleteBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TaskCompleteActivity extends AppCompatActivity {
    private ActivityTaskCompleteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        final double[] rating = {0.0};
        binding.taskcompleterating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating[0] = v;
            }
        });
        DBresources dBresources = new DBresources();
        User.RetriveData().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                dBresources.database.collection("User").whereEqualTo("Email", getIntent().getStringExtra("Email"))
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                    binding.taskcompletename.setText(queryDocumentSnapshot.get("Name").toString());
                                    if (queryDocumentSnapshot.contains("Profile_Pic")) {
                                        Glide.with(TaskCompleteActivity.this)
                                                .load(queryDocumentSnapshot.get("Profile_Pic"))
                                                .into(binding.taskcompleteprofilepc);
                                    }
                                }
                            }
                        });
            }
        });
        binding.taskcompletebuttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                               startActivity(new Intent(TaskCompleteActivity.this,EmployeeActivity.class));

                finish();
            }
        });


    }
}