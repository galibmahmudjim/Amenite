package com.example.amenite.Customer;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amenite.Customer.Services.CustomerBeautyServiceFragment;
import com.example.amenite.R;
import com.example.amenite.databinding.FragmentCustomerHomeBinding;

public class CustomerHomeFragment extends Fragment {

    private FragmentCustomerHomeBinding fragmentCustomerHomeBinding;
    public CustomerHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCustomerHomeBinding =  FragmentCustomerHomeBinding.inflate(inflater,container,false);
        View view = fragmentCustomerHomeBinding.getRoot();
        TextView toolbartextview = getActivity().findViewById(R.id.CustomerActivityToolbarTextview);
        fragmentCustomerHomeBinding.CustomerHomeFragmentBeautyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.CustomerActivityFragmentContainer,CustomerBeautyServiceFragment.class,null)
                        .commit();


            }
        });
        return view;
    }
}