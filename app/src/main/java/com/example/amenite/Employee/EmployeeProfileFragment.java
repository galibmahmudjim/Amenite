package com.example.amenite.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.amenite.PROFILE.User;
import com.example.amenite.databinding.FragmentEmployeeProfileBinding;
import com.example.amenite.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;;

public class EmployeeProfileFragment extends Fragment {


    public EmployeeProfileFragment() {
        // Required empty public constructor
    }

    private TextView Name;
    private TextView PhoneNumber;
    private TextView Email;
    private FragmentEmployeeProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEmployeeProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.employeeprofileload.startShimmer();
        User.RetriveData().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                if(User.Profile_Pic!=" ") {
                    Glide.with(getContext())
                            .load(User.Profile_Pic)
                            .into(binding.EmployeeProfileImageview);
                    binding.employeeprofileload.stopShimmer();
                    binding.employeeprofileload.setVisibility(View.GONE);
                    binding.emloyeeprofile.setVisibility(View.VISIBLE);
                }
                else
                {
                    Glide.with(getContext())
                            .load("android.resource://"+getContext().getPackageName()+"/drawable/profile")
                            .into(binding.EmployeeProfileImageview);
                    binding.employeeprofileload.stopShimmer();
                    binding.employeeprofileload.setVisibility(View.GONE);
                    binding.emloyeeprofile.setVisibility(View.VISIBLE);
                }

            }
        });
        binding.EmployeeProfileAddressTextview.setText(User.Address);
        binding.EmployeeProfileDOBTextview.setText(User.Date_of_Birth);
        binding.EmployeeProfileEmailTextview.setText(User.Emailid);
        binding.EmployeeProfileGenderTextview.setText(User.Gender);
        binding.EmployeeProfileNameTextview.setText(User.Fullname);
        binding.EmployeeProfilePhonenumberTextview.setText(User.Phonenumber);
        binding.EmployeeProfileEditprofileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditEmployeeProfileActivity.class));

            }
        });

        return view;
    }
}