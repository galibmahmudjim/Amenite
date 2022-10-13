package com.example.amenite.Customer.Services;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amenite.Customer.CustomerAddressMapsActivity;
import com.example.amenite.R;
import com.example.amenite.databinding.FragmentCustomerBeautyServiceBinding;

public class CustomerBeautyServiceFragment extends Fragment {

    FragmentCustomerBeautyServiceBinding fragmentCustomerBeautyServiceBinding;

    public CustomerBeautyServiceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        fragmentCustomerBeautyServiceBinding = FragmentCustomerBeautyServiceBinding.inflate(inflater,container,false);
        View view = fragmentCustomerBeautyServiceBinding.getRoot();
        fragmentCustomerBeautyServiceBinding.CustomerBeautyServiceAddressMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CustomerAddressMapsActivity.class));
            }
        });

        return view;
    }
}