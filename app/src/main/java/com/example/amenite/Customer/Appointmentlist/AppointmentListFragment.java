package com.example.amenite.Customer.Appointmentlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.databinding.FragmentAppointmentListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;


public class AppointmentListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AppointmentList> appoinmentLists;
    MyAdapter myAdapter;
    private FragmentAppointmentListBinding binding;
    public AppointmentListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        DBresources dBresources = new DBresources();
        recyclerView = view.findViewById(R.id.CustomerApoointmentlistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appoinmentLists = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(),appoinmentLists);
        recyclerView.setAdapter(myAdapter);
        binding.AppointmentListShimmer.startShimmer();
        dBresources.database.collection("Appointment").whereEqualTo("Email",User.Emailid)
              .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange documentChange : value.getDocumentChanges())
                        {
                            appoinmentLists.add(documentChange.getDocument().toObject(AppointmentList.class));
                        }

                        binding.AppointmentListShimmer.stopShimmer();
                        binding.AppointmentListShimmer.setVisibility(View.GONE);
                        binding.CustomerApoointmentlistRecyclerview.setVisibility(View.VISIBLE);
                        myAdapter.notifyDataSetChanged();
                    }
                });

        return view;
    }
}