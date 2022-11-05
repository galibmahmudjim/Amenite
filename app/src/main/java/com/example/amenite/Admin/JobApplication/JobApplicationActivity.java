package com.example.amenite.Admin.JobApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.example.amenite.databinding.ActivityJobApplicationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class JobApplicationActivity extends AppCompatActivity {
    private ActivityJobApplicationBinding binding;
    RecyclerView recyclerView;
    ArrayList<JobApplicationList> jobApplicationLists;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityJobApplicationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        DBresources dBresources = new DBresources();
        recyclerView = view.findViewById(R.id.jobappRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        jobApplicationLists = new ArrayList<>();
        myAdapter = new MyAdapter(JobApplicationActivity.this, jobApplicationLists);
        recyclerView.setAdapter(myAdapter);
      //  binding.EmployeesignupListShmimmer.startShimmer();
        dBresources.database.collection("Job").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                binding.EmployeesignupListShmimmer.stopShimmer();
                binding.EmployeesignupListShmimmer.setVisibility(View.GONE);
                binding.jobappRecyclerview.setVisibility(View.VISIBLE);
                for (DocumentChange queryDocumentSnapshot : queryDocumentSnapshots.getDocumentChanges()) {
                    jobApplicationLists.add(queryDocumentSnapshot.getDocument().toObject(JobApplicationList.class));

                }myAdapter.notifyDataSetChanged();
            }
        });


    }
}