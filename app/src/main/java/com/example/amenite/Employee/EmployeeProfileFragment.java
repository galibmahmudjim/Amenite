package com.example.amenite.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.example.amenite.PROFILE.User;
import com.example.amenite.databinding.FragmentEmployeeProfileBinding;
import com.example.amenite.databinding.FragmentProfileBinding;

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

        binding =  FragmentEmployeeProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

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