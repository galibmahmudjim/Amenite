package com.example.amenite.Employee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.amenite.databinding.FragmentContactUsBinding;

public class ContactUsFragment extends Fragment {

    private FragmentContactUsBinding fragmentContactUsBinding;
    public ContactUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentContactUsBinding = FragmentContactUsBinding.inflate(inflater,container,false);
        fragmentContactUsBinding.ContactusFragmentSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = fragmentContactUsBinding.ContactusFragmentSubjectEdittext.getText().toString();
                String messege = fragmentContactUsBinding.ContactusFragmentMessegeEdittext.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amenite@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT,messege);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
                else
                    Toast.makeText(getActivity(),"No apps installed", Toast.LENGTH_SHORT);
            }
        });
        return fragmentContactUsBinding.getRoot();
    }
}