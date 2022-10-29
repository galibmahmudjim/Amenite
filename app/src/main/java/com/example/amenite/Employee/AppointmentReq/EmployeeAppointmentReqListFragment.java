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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class EmployeeAppointmentReqListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AppointmentReqList> appointmentReqLists;
    MyAdapter myAdapter;
    private FragmentAppointmentListBinding binding;

    public EmployeeAppointmentReqListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        DBresources dBresources = new DBresources();
        recyclerView = view.findViewById(R.id.CustomerApoointmentlistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appointmentReqLists = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), appointmentReqLists);
        recyclerView.setAdapter(myAdapter);
        dBresources.database.collection("Appointment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {

                    dBresources.database.collection("Appointment").document(queryDocumentSnapshot.getId())
                            .collection("Requested_Employee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot queryDocumentSnapshot1 : task.getResult()) {
                                        if (queryDocumentSnapshot1.contains("Email")) {
                                            if (queryDocumentSnapshot1.get("Email").toString().equals(User.Emailid)) {
                                                Log.d(TAG.TAG, "onComplete: " + queryDocumentSnapshot.getData());
                                                appointmentReqLists.add(queryDocumentSnapshot.toObject(AppointmentReqList.class));
                                                myAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }
}