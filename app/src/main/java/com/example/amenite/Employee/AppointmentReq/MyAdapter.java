package com.example.amenite.Employee.AppointmentReq;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amenite.Customer.CustomerAppointmentDetailsActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.Employee.CarrentalDetailsActivity;
import com.example.amenite.Employee.RequestedEmployeeAppointmentDetailsActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<AppointmentReqList> appointmentReqLists;

    public MyAdapter(Context context, ArrayList<AppointmentReqList> appointmentReqLists) {
        this.context = context;
        this.appointmentReqLists = appointmentReqLists;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.employee_appoinment_req_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        AppointmentReqList appointmentReqList = appointmentReqLists.get(position);
        holder.employeeAppointmentReqlistCardviewStatusTextview.setText(appointmentReqList.Status);
        holder.employeeAppointmentReqlistCardviewTimeTextview.setText(appointmentReqList.Request_Date+", "+appointmentReqList.Request_Time);
        holder.employeeAppointmentReqlistCardviewServiceTextview.setText(appointmentReqList.Service);
        holder.employeeAppointmentReqlistCardviewAppointmentTimeTextview.setText(appointmentReqList.Appointment_Time);
        holder.employeeAppointmentReqlistCardviewAppointmentDateTextview.setText(appointmentReqList.Appointment_Date);
        holder.employeeAppointmentReqlistCardviewIdTextview.setText(appointmentReqList.Appointment_Id);
        DBresources dBresources = new DBresources();
        if(holder.employeeAppointmentReqlistCardviewServiceTextview.getText().toString().equals(User.getService()))
        {
            holder.appreqtext.setText("Pickup Time");
            holder.employeeAppointmentReqlistCardviewServiceTextview.setText("Car Rental");
        }
        else
        {
            holder.appreqtext.setText("Appointment Time");
        }
        User.RetriveData().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                dBresources.database.collection("User").whereEqualTo("Email",appointmentReqList.Email)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult())
                                {
                                    if(queryDocumentSnapshot.contains("Profile_Pic"))
                                    {
                                        Glide.with(context)
                                                .load(queryDocumentSnapshot.get("Profile_Pic"))
                                                .into(holder.employeeAppointmentReqlistImage);
                                    }
                                }
                            }
                        });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Log.d(TAG.TAG, "onClick: "+holder.employeeAppointmentReqlistCardviewServiceTextview.getText());
                if(holder.employeeAppointmentReqlistCardviewServiceTextview.getText()=="Car Rental"){
                     intent = new Intent(view.getContext(), CarrentalDetailsActivity.class);}
                    else {
                     intent = new Intent(view.getContext(), RequestedEmployeeAppointmentDetailsActivity.class);
                }
                intent.putExtra("Appointment_Id",holder.employeeAppointmentReqlistCardviewIdTextview.getText().toString());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return appointmentReqLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView employeeAppointmentReqlistCardviewStatusTextview;
        TextView employeeAppointmentReqlistCardviewTimeTextview;
        TextView employeeAppointmentReqlistCardviewServiceTextview;
        TextView employeeAppointmentReqlistCardviewAppointmentDateTextview;
        TextView employeeAppointmentReqlistCardviewAppointmentTimeTextview;
        TextView employeeAppointmentReqlistCardviewIdTextview;
        TextView appreqtext;
        ImageView employeeAppointmentReqlistImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeAppointmentReqlistCardviewTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewTimeTextview);
            employeeAppointmentReqlistCardviewServiceTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewServiceTextview);
            employeeAppointmentReqlistCardviewAppointmentDateTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewAppointmentDateTextview);
            employeeAppointmentReqlistCardviewAppointmentTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewAppointmentTimeTextview);
            employeeAppointmentReqlistCardviewStatusTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewStatusTextview);
            employeeAppointmentReqlistCardviewIdTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewIdTextview);
            employeeAppointmentReqlistImage = itemView.findViewById(R.id.EmployeeAppointmentReqlistImage);
            appreqtext = itemView.findViewById(R.id.appreqtext);
        }
    }
}
