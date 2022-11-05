package com.example.amenite.Admin.JobApplication;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amenite.Admin.EmployeeSignupActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Employee.AppointmentDetailsActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<JobApplicationList> jobApplicationLists;

    public MyAdapter(Context context, ArrayList<JobApplicationList> jobApplicationLists) {
        this.context = context;
        this.jobApplicationLists = jobApplicationLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.job_application,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        JobApplicationList jobApplicationList = jobApplicationLists.get(position);
        holder.Jobappname.setText(jobApplicationList.Name);
        holder.Jobemail.setText(jobApplicationList.Email);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EmployeeSignupActivity.class);
                intent.putExtra("Service",holder.Jobappservice.getText());
                intent.putExtra("UserID",jobApplicationList.UserID);
                view.getContext().startActivity(intent);
            }
        });
        DBresources dBresources =   new DBresources();
        dBresources.database.collection("Job").document(jobApplicationList.UserID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                holder.Jobappservice.setText(documentSnapshot.get("Service").toString());
            }
        });
        dBresources.database.collection("User")
                .document(jobApplicationList.UserID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.contains("Profile_Pic"))
                        {
                            Glide.with(context)
                                    .load(documentSnapshot.get("Profile_Pic"))
                                    .into(holder.Jobapplicationpropic);
                        }
                        else
                        {
                            Glide.with(context)
                                    .load(Uri.parse("android.resource://" + "com.example.amenite"+ "/" + R.drawable.profile));
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {

        return jobApplicationLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Jobappservice;
        TextView Jobappname;
        TextView Jobemail;
        ImageView Jobapplicationpropic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           Jobappservice = itemView.findViewById(R.id.jobappservice);
            Jobappname = itemView.findViewById(R.id.jobappname);
            Jobapplicationpropic = itemView.findViewById(R.id.jobapplicationpropic);
            Jobemail = itemView.findViewById(R.id.jobemail);
        }
    }
}
