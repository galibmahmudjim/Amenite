package com.example.amenite.Employee.Appointmentlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amenite.Customer.CustomerAppointmentDetailsActivity;
import com.example.amenite.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<AppointmentList> appoinmentArrayLists;

    public MyAdapter(Context context, ArrayList<AppointmentList> appoinmentArrayLists) {
        this.context = context;
        this.appoinmentArrayLists = appoinmentArrayLists;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custome_appoinment_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        AppointmentList appoinmentList = appoinmentArrayLists.get(position);
        holder.employeeAppointmentlistCardviewAppointmentIdTextview.setText(appoinmentList.Appointment_Id);
        holder.employeeAppointmentlistCardviewTimeTextview.setText(appoinmentList.Request_Date+", "+appoinmentList.Request_Time);
        holder.employeeAppointmentlistCardviewServiceTextview.setText(appoinmentList.Service);
        holder.employeeAppointmentlistCardviewAppointmentTimeTextview.setText(appoinmentList.Appointment_Time);
        holder.employeeAppointmentlistCardviewAppointmentDateTextview.setText(appoinmentList.Appointment_Date);
        holder.employeeAppointmentlistCardviewStatusTextview.setText(appoinmentList.Appointment_Status);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CustomerAppointmentDetailsActivity.class);
                intent.putExtra("Appointment_Id",holder.employeeAppointmentlistCardviewAppointmentIdTextview.getText().toString());
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return appoinmentArrayLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView employeeAppointmentlistCardviewStatusTextview;
        TextView employeeAppointmentlistCardviewTimeTextview;
        TextView employeeAppointmentlistCardviewServiceTextview;
        TextView employeeAppointmentlistCardviewAppointmentDateTextview;
        TextView employeeAppointmentlistCardviewAppointmentTimeTextview;
        TextView employeeAppointmentlistCardviewAppointmentIdTextview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeAppointmentlistCardviewTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewTimeTextview);
            employeeAppointmentlistCardviewServiceTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewServiceTextview);
            employeeAppointmentlistCardviewAppointmentDateTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewAppointmentDateTextview);
            employeeAppointmentlistCardviewAppointmentTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewAppointmentTimeTextview);
            employeeAppointmentlistCardviewStatusTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewStatusTextview);
            employeeAppointmentlistCardviewAppointmentIdTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewIdTextview);

        }
    }
}
