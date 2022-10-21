package com.example.amenite.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.List;

public class ProfileFragment extends Fragment {



    public ProfileFragment() {
        // Required empty public constructor
    }

    private TextView Name;
    private TextView PhoneNumber;
    private TextView Email;
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding =  FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.CustomerProfileAddressTextview.setText(User.Address);
        binding.CustomerProfileDOBTextview.setText(User.Date_of_Birth);
        binding.CustomerProfileEmailTextview.setText(User.Emailid);
        binding.CustomerProfileGenderTextview.setText(User.Gender);
        binding.CustomerProfileNameTextview.setText(User.Fullname);
        binding.CustomerProfilePhonenumberTextview.setText(User.Phonenumber);
        binding.CustomerProfileEditprofileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditCusomerProfileActivity.class));

            }
        });

        return view;
    }
}