package com.example.amenite.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.FragmentEditProfileBinding;
import com.example.amenite.databinding.FragmentProfileBinding;

public class EditProfileFragment extends Fragment {

    public EditProfileFragment() {
        // Required empty public constructor
    }

    private FragmentEditProfileBinding binding;
    private TextView Name;
    private TextView PhoneNumber;
    private TextView Email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.EditProfileEmailTextview.setText(User.Emailid);
        binding.EditProfilePhonenumberTextview.setText(User.Phonenumber);
        binding.EditProfileNameEdittext.setText(User.Fullname);
        binding.EditprofileMylocationiconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;


    }
}