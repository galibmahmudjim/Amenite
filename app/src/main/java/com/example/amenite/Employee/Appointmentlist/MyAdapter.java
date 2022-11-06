package com.example.amenite.Employee.Appointmentlist;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amenite.Customer.CustomerAppointmentDetailsActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Employee.AppointmentDetailsActivity;
import com.example.amenite.Employee.CarrentalDetailsActivity;
import com.example.amenite.Employee.RequestedEmployeeAppointmentDetailsActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<AppointmentList> appointmentLists;

    public MyAdapter(Context context, ArrayList<AppointmentList> appointmentLists) {
        this.context = context;
        this.appointmentLists = appointmentLists;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.employee_appoinment_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        AppointmentList appointmentList = appointmentLists.get(position);
        holder.employeeAppointmentlistCardviewStatusTextview.setText(appointmentList.Appointment_Status);
        holder.employeeAppointmentlistCardviewTimeTextview.setText(appointmentList.Request_Date+", "+appointmentList.Request_Time);
        holder.employeeAppointmentlistCardviewServiceTextview.setText(appointmentList.Service);
        holder.employeeAppointmentlistCardviewAppointmentTimeTextview.setText(appointmentList.Appointment_Time);
        holder.employeeAppointmentlistCardviewAppointmentDateTextview.setText(appointmentList.Appointment_Date);
        holder.employeeAppointmentlistCardviewIdTextview.setText(appointmentList.Appointment_Id);
        if(User.Service=="Car Rental")
        {
            holder.apptext.setText("Pickup Time");
        }
        DBresources dBresources = new DBresources();
        User.RetriveData().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                dBresources.database.collection("User").whereEqualTo("Email",appointmentList.Email)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult())
                                {
                                    if(queryDocumentSnapshot.contains("Profile_Pic"))
                                    {
                                        Glide.with(context)
                                                .load(queryDocumentSnapshot.get("Profile_Pic"))
                                                .into(holder.employeeAppointmentlistImage);
                                    }
                                    if(queryDocumentSnapshot.contains("Rating"))
                                        holder.employeeAppointmentlistRating.setRating(Float.parseFloat(queryDocumentSnapshot.get("Rating").toString()));

                                }
                            }
                        });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.employeeAppointmentlistCardviewServiceTextview.getText().toString().equals("Car Rental"))
                {
                Intent intent = new Intent(view.getContext(), CarrentalDetailsActivity.class);
                intent.putExtra("Appointment_Id",holder.employeeAppointmentlistCardviewIdTextview.getText().toString());
                view.getContext().startActivity(intent);}
                else{
                    Intent intent = new Intent(view.getContext(), AppointmentDetailsActivity.class);
                    intent.putExtra("Appointment_Id",holder.employeeAppointmentlistCardviewIdTextview.getText().toString());
                    view.getContext().startActivity(intent);}

            }
        });

    }

    @Override
    public int getItemCount() {

        return appointmentLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView employeeAppointmentlistCardviewStatusTextview;
        TextView employeeAppointmentlistCardviewTimeTextview;
        TextView employeeAppointmentlistCardviewServiceTextview;
        TextView employeeAppointmentlistCardviewAppointmentDateTextview;
        TextView employeeAppointmentlistCardviewAppointmentTimeTextview;
        TextView employeeAppointmentlistCardviewIdTextview;
        ImageView employeeAppointmentlistImage;
        TextView apptext;
        RatingBar employeeAppointmentlistRating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeAppointmentlistCardviewTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewTimeTextview);
            employeeAppointmentlistCardviewServiceTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewServiceTextview);
            employeeAppointmentlistCardviewAppointmentDateTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewAppointmentDateTextview);
            employeeAppointmentlistCardviewAppointmentTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewAppointmentTimeTextview);
            employeeAppointmentlistCardviewStatusTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewStatusTextview);
            employeeAppointmentlistCardviewIdTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewIdTextview);
            employeeAppointmentlistImage = itemView.findViewById(R.id.EmployeeAppointmentlistImage);
            apptext = itemView.findViewById(R.id.apptext);
            employeeAppointmentlistRating = itemView.findViewById(R.id.EmployeeAppointmentlistRating);
        }
    }
}
