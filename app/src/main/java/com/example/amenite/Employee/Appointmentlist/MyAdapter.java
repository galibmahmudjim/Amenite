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
import com.example.amenite.Employee.AppointmentDetailsActivity;
import com.example.amenite.Employee.RequestedEmployeeAppointmentDetailsActivity;
import com.example.amenite.R;

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
        holder.employeeAppointmentlistCardviewStatusTextview.setText(appointmentList.Status);
        holder.employeeAppointmentlistCardviewTimeTextview.setText(appointmentList.Request_Date+", "+appointmentList.Request_Time);
        holder.employeeAppointmentlistCardviewServiceTextview.setText(appointmentList.Service);
        holder.employeeAppointmentlistCardviewAppointmentTimeTextview.setText(appointmentList.Appointment_Time);
        holder.employeeAppointmentlistCardviewAppointmentDateTextview.setText(appointmentList.Appointment_Date);
        holder.employeeAppointmentlistCardviewIdTextview.setText(appointmentList.Appointment_Id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AppointmentDetailsActivity.class);
                intent.putExtra("Appointment_Id",holder.employeeAppointmentlistCardviewIdTextview.getText().toString());
                view.getContext().startActivity(intent);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeAppointmentlistCardviewTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewTimeTextview);
            employeeAppointmentlistCardviewServiceTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewServiceTextview);
            employeeAppointmentlistCardviewAppointmentDateTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewAppointmentDateTextview);
            employeeAppointmentlistCardviewAppointmentTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewAppointmentTimeTextview);
            employeeAppointmentlistCardviewStatusTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewStatusTextview);
            employeeAppointmentlistCardviewIdTextview = itemView.findViewById(R.id.EmployeeAppointmentlistCardviewIdTextview);
        }
    }
}
