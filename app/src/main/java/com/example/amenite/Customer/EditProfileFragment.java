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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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

    private FragmentEditProfileBinding fragmentChangeCustomerProfileBinding;
    private TextView Name;
    private TextView PhoneNumber;
    private TextView Email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentChangeCustomerProfileBinding =  FragmentEditProfileBinding.inflate(inflater,container,false);
        View view = fragmentChangeCustomerProfileBinding.getRoot();

        Name = (TextView) view.findViewById(R.id.EditProfileNameEdittext);
        PhoneNumber = (TextView) view.findViewById(R.id.EditProfilePhonenumberEdittext);
        Email = (TextView) view.findViewById(R.id.EditProfileEmailEdittext);


        fragmentChangeCustomerProfileBinding.EditProfilUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(69);
                Log.d("Bismillah","69");

                User.Fullname=Name.getText().toString();
                Log.d("Bismillah","69");


                User.Phonenumber= PhoneNumber.getText().toString();
                Log.d("Bismillah","69");

                User.Emailid= Email.getText().toString();
                Log.d("Bismillah","69");

                Name.setText("");

                PhoneNumber.setText("");

                Email.setText("");



            }
        });

        return view;


    }
}