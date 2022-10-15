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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        Name.setText( User.Fullname );

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