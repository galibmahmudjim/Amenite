package com.example.amenite.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amenite.LoginActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.Welcome;
import com.example.amenite.databinding.FragmentCustomerHomeBinding;
import com.example.amenite.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }


    private TextView Name;
    private TextView PhoneNumber;
    private TextView Email;
    private FragmentProfileBinding fragmentEditCustomerProfileBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentEditCustomerProfileBinding =  FragmentProfileBinding.inflate(inflater,container,false);
        View view = fragmentEditCustomerProfileBinding.getRoot();

        Name = (TextView) view.findViewById(R.id.CustomerProfileNameImageview);
        Name.setText(User.Username);

        PhoneNumber = (TextView) view.findViewById(R.id.CustomerProfilePhonenumberTextview);
        PhoneNumber.setText(User.Phonenumber);

        Email = (TextView) view.findViewById(R.id.CustomerProfileEmailImageview);
        Email.setText(User.Emailid);

        fragmentEditCustomerProfileBinding.CustomerProfileEditprofileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.CustomerActivityFragmentContainer, EditProfileFragment.class,null)
                        .commit();

            }
        });

        return view;
    }
}