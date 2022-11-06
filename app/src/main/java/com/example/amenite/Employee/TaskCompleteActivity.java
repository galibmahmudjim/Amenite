package com.example.amenite.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.TAG;
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
        final String[] id = {""};
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
                                    id[0] = queryDocumentSnapshot.getId();
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
                dBresources.database.collection("User").document(id[0]).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        double rate = 0;
                        int ih = 0;
                        if (documentSnapshot.contains("Rating")) {
                            Double i = Double.parseDouble(documentSnapshot.get("Rating").toString());
                            ih = Integer.parseInt(documentSnapshot.get("RateHead").toString());
                            rate = (i * ih + rating[0]) / (ih + 1);

                        }
                        Log.d(TAG.TAG, "onSuccess: "+rating[0]);
                        dBresources.database.collection("User").document(id[0])
                                .update("Rating", String.valueOf(rate),
                                        "RateHead", String.valueOf(ih + 1));

                        startActivity(new Intent(TaskCompleteActivity.this, EmployeeActivity.class));

                        finish();
                    }
                });
            }
        });


    }
}