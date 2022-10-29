package com.example.amenite.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amenite.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    Context context;
    ArrayList<CustomerList> CustomerArrayLists;

    public CustomerAdapter(Context context, ArrayList<CustomerList> customerArrayLists)
    {
        this.context = context;

        this.CustomerArrayLists = customerArrayLists;

    }

    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_admin_customerlistsample,parent,false);
        return new CustomerAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, int position) {

        CustomerList customerList = CustomerArrayLists.get(position);

        holder.customerNameTextview.setText(customerList.getName());
        holder.customerPhoneNumberTextview.setText(customerList.getPhone_Number());
        holder.customerAddressTextview.setText(customerList.getAddress());

    }

    @Override
    public int getItemCount() {

        return CustomerArrayLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView customerNameTextview;
        TextView customerPhoneNumberTextview;
        TextView customerAddressTextview;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            customerNameTextview = itemView.findViewById(R.id.AdminName);
            customerPhoneNumberTextview = itemView.findViewById(R.id.AdminPhoneNumber);
            customerAddressTextview = itemView.findViewById(R.id.AdminAddress);

        }

    }
}
