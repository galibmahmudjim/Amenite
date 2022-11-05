package com.example.amenite.Employee.AppointmentReq;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.example.amenite.databinding.FragmentAppointmentListBinding;
import com.example.amenite.databinding.FragmentEmployeeAppointmentReqListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class EmployeeAppointmentReqListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AppointmentReqList> appointmentReqLists;
    MyAdapter myAdapter;
    private FragmentEmployeeAppointmentReqListBinding binding;

    public EmployeeAppointmentReqListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeAppointmentReqListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        DBresources dBresources = new DBresources();
        binding.EmployeeAppointmentReqListShmimmer.startShimmer();
        recyclerView = view.findViewById(R.id.EmployeeApoointmentReqlistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appointmentReqLists = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), appointmentReqLists);
        recyclerView.setAdapter(myAdapter);
        Task t1 = User.RetriveData();
        Task t2 = t1.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                dBresources.database.collection("Appointment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            dBresources.database.collection("Appointment").document(queryDocumentSnapshot.getId())
                                    .collection("Requested_Employee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot queryDocumentSnapshot1 : task.getResult()) {
                                                if (queryDocumentSnapshot1.contains("Email") && queryDocumentSnapshot.contains("Appointment_Status")) {
                                                    if (queryDocumentSnapshot1.get("Email").toString().equals(User.Emailid) && queryDocumentSnapshot.get("Appointment_Status").equals("Pending")) {
                                                        appointmentReqLists.add(queryDocumentSnapshot.toObject(AppointmentReqList.class));
                                                    }
                                                }
                                            }
                                            binding.EmployeeAppointmentReqListShmimmer.stopShimmer();
                                            binding.EmployeeAppointmentReqListShmimmer.setVisibility(View.GONE);
                                            binding.EmployeeApoointmentReqlistRecyclerview.setVisibility(View.VISIBLE);
                                            myAdapter.notifyDataSetChanged();
                                        }
                                    });

                        }

                    }
                });

            }
        });
        t2.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                binding.EmployeeAppointmentReqListShmimmer.stopShimmer();
                binding.EmployeeAppointmentReqListShmimmer.setVisibility(View.GONE);
                binding.EmployeeApoointmentReqlistRecyclerview.setVisibility(View.VISIBLE);
            }
        });
        return view;

    }
}