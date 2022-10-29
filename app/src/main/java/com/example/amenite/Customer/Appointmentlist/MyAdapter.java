package com.example.amenite.Customer.Appointmentlist;

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
        holder.customerAppointmentlistCardviewStatusTextview.setText(appoinmentList.Appointment_Status);
        holder.customerAppointmentlistCardviewTimeTextview.setText(appoinmentList.Request_Date+", "+appoinmentList.Request_Time);
        holder.customerAppointmentlistCardviewServiceTextview.setText(appoinmentList.Service);
        holder.customerAppointmentlistCardviewAppointmentTimeTextview.setText(appoinmentList.Appointment_Time);
        holder.customerAppointmentlistCardviewAppointmentDateTextview.setText(appoinmentList.Appointment_Date);
        holder.customerAppointmentlistCardviewIdTextview.setText(appoinmentList.Appointment_Id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CustomerAppointmentDetailsActivity.class);
                intent.putExtra("Appointment_Id",holder.customerAppointmentlistCardviewIdTextview.getText().toString());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return appoinmentArrayLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView customerAppointmentlistCardviewStatusTextview;
        TextView customerAppointmentlistCardviewTimeTextview;
        TextView customerAppointmentlistCardviewServiceTextview;
        TextView customerAppointmentlistCardviewAppointmentDateTextview;
        TextView customerAppointmentlistCardviewAppointmentTimeTextview;
        TextView customerAppointmentlistCardviewIdTextview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerAppointmentlistCardviewTimeTextview = itemView.findViewById(R.id.CustomerAppointmentlistCardviewTimeTextview);
            customerAppointmentlistCardviewServiceTextview = itemView.findViewById(R.id.CustomerAppointmentlistCardviewServiceTextview);
            customerAppointmentlistCardviewAppointmentDateTextview = itemView.findViewById(R.id.CustomerAppointmentlistCardviewAppointmentDateTextview);
            customerAppointmentlistCardviewAppointmentTimeTextview = itemView.findViewById(R.id.CustomerAppointmentlistCardviewAppointmentTimeTextview);
            customerAppointmentlistCardviewStatusTextview = itemView.findViewById(R.id.CustomerAppointmentlistCardviewStatusTextview);
            customerAppointmentlistCardviewIdTextview = itemView.findViewById(R.id.CustomerAppointmentlistCardviewIdTextview);
        }
    }
}
