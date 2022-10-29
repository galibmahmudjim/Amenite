package com.example.amenite.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amenite.PROFILE.Employee;
import com.example.amenite.R;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>{
    Context context;
    ArrayList<EmployeeList> employeeArrayLists;

    public EmployeeAdapter(Context context, ArrayList<EmployeeList> employeeArrayLists)
    {
        this.context = context;
        this.employeeArrayLists=employeeArrayLists;
    }

    @NonNull
    @Override
    public EmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_admin_employeelistsample,parent,false);
        return new EmployeeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.MyViewHolder holder, int position) {

        EmployeeList employeeList = employeeArrayLists.get(position);

        holder.employeeNameTextview.setText(employeeList.getName());
        holder.employeePhoneNumberTextview.setText(employeeList.getPhone_Number());
        holder.employeeAddressTextview.setText(employeeList.getAddress());

    }

    @Override
    public int getItemCount() {

        return employeeArrayLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView employeeNameTextview;
        TextView employeePhoneNumberTextview;
        TextView employeeAddressTextview;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            employeeNameTextview = itemView.findViewById(R.id.AdminName);
            employeePhoneNumberTextview = itemView.findViewById(R.id.AdminPhoneNumber);
            employeeAddressTextview = itemView.findViewById(R.id.AdminAddress);

        }

    }
}
