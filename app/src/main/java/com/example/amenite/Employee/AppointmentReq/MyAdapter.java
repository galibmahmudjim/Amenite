package com.example.amenite.Employee.AppointmentReq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amenite.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<AppointmentReqList> appoinmentArrayLists;

    public MyAdapter(Context context, ArrayList<AppointmentReqList> appoinmentArrayLists) {
        this.context = context;
        this.appoinmentArrayLists = appoinmentArrayLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.employee_appoinment_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AppointmentReqList appoinmentList = appoinmentArrayLists.get(position);
        holder.employeeAppointmentReqlistCardviewStatusTextview.setText(appoinmentList.Appointment_Status);
        holder.employeeAppointmentReqlistCardviewTimeTextview.setText(appoinmentList.Request_Date + ", " + appoinmentList.Request_Time);
        holder.employeeAppointmentReqlistCardviewServiceTextview.setText(appoinmentList.Service);
        holder.employeeAppointmentReqlistCardviewAppointmentTimeTextview.setText(appoinmentList.Appointment_Time);
        holder.employeeAppointmentReqlistCardviewAppointmentDateTextview.setText(appoinmentList.Appointment_Date);
        holder.employeeAppointmentReqlistCardviewAppointmentIdTextview.setText(appoinmentList.Appointment_Id);

    }

    @Override
    public int getItemCount() {

        return appoinmentArrayLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView employeeAppointmentReqlistCardviewStatusTextview;
        TextView employeeAppointmentReqlistCardviewTimeTextview;
        TextView employeeAppointmentReqlistCardviewServiceTextview;
        TextView employeeAppointmentReqlistCardviewAppointmentDateTextview;
        TextView employeeAppointmentReqlistCardviewAppointmentTimeTextview;
        TextView employeeAppointmentReqlistCardviewAppointmentIdTextview;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeAppointmentReqlistCardviewTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewTimeTextview);
            employeeAppointmentReqlistCardviewServiceTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewServiceTextview);
            employeeAppointmentReqlistCardviewAppointmentDateTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewAppointmentDateTextview);
            employeeAppointmentReqlistCardviewAppointmentTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewAppointmentTimeTextview);
            employeeAppointmentReqlistCardviewStatusTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewStatusTextview);
            employeeAppointmentReqlistCardviewAppointmentIdTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewIdTextview);

        }
    }
}
