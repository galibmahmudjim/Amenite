package com.example.amenite.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.amenite.Customer.Services.CustomerBeautyServiceActivity;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.FragmentEmployeeHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.List;

public class EmployeeHomeFragment extends Fragment {

    private FragmentEmployeeHomeBinding binding;

    public EmployeeHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEmployeeHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        TextView toolbartextview = getActivity().findViewById(R.id.EmployeeActivityToolbarTextview);
        return view;
    }
}