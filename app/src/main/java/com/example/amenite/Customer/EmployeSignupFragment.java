package com.example.amenite.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.amenite.Customer.Services.Carrental.CustomerServiceCarrentalAreaActivity;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.FragmentEmployeSignupBinding;

import java.util.HashMap;

public class EmployeSignupFragment extends Fragment {
    private FragmentEmployeSignupBinding binding;
    public EmployeSignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeSignupBinding.inflate(inflater,container,false);
        Glide.with(getContext())
                .load(User.Profile_Pic)
                .into(binding.cussignpic);
        binding.cussignNameEdittext.setText(User.getFullname());
        String[] job = {"Beauty and Salon","Electronic and Appliances","Car Rental", "Home Service"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, job);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        binding.cussignspiner.setAdapter(adapter);
        binding.ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> hashMap = new HashMap<>();
                DBresources dBresources = new DBresources();
                hashMap.put("Name",User.Fullname);
                hashMap.put("Email",User.Emailid);
                hashMap.put("UserID",User.UserID);
                hashMap.put("Service",binding.cussignspiner.getSelectedItem().toString());
                dBresources.database.collection("Job").document(User.UserID)
                        .set(hashMap);
                startActivity(new Intent(getActivity(),CustomerActivity.class));
            }
        });

        return binding.getRoot();
    }
}