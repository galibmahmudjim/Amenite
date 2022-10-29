package com.example.amenite.Employee.AppointmentReq;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amenite.Customer.CustomerAppointmentDetailsActivity;
import com.example.amenite.Employee.RequestedEmployeeAppointmentDetailsActivity;
import com.example.amenite.R;

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
        holder.employeeAppointmentReqlistCardviewStatusTextview.setText(appointmentReqList.Appointment_Status);
        holder.employeeAppointmentReqlistCardviewTimeTextview.setText(appointmentReqList.Request_Date+", "+appointmentReqList.Request_Time);
        holder.employeeAppointmentReqlistCardviewServiceTextview.setText(appointmentReqList.Service);
        holder.employeeAppointmentReqlistCardviewAppointmentTimeTextview.setText(appointmentReqList.Appointment_Time);
        holder.employeeAppointmentReqlistCardviewAppointmentDateTextview.setText(appointmentReqList.Appointment_Date);
        holder.employeeAppointmentReqlistCardviewIdTextview.setText(appointmentReqList.Appointment_Id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RequestedEmployeeAppointmentDetailsActivity.class);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeAppointmentReqlistCardviewTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewTimeTextview);
            employeeAppointmentReqlistCardviewServiceTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewServiceTextview);
            employeeAppointmentReqlistCardviewAppointmentDateTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewAppointmentDateTextview);
            employeeAppointmentReqlistCardviewAppointmentTimeTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewAppointmentTimeTextview);
            employeeAppointmentReqlistCardviewStatusTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewStatusTextview);
            employeeAppointmentReqlistCardviewIdTextview = itemView.findViewById(R.id.EmployeeAppointmentReqlistCardviewIdTextview);
        }
    }
}
