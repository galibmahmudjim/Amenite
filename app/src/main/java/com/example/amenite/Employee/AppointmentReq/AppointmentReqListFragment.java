package com.example.amenite.Employee.AppointmentReq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.R;
import com.example.amenite.databinding.FragmentEmployeeAppointmentReqListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AppointmentReqListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AppointmentReqList> appoinmentLists;
    MyAdapter myAdapter;
    private FragmentEmployeeAppointmentReqListBinding binding;

    public void AppointmentReqList() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeAppointmentReqListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        DBresources dBresources = new DBresources();
        recyclerView = view.findViewById(R.id.EmployeeApoointmentReqlistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appoinmentLists = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), appoinmentLists);
        recyclerView.setAdapter(myAdapter);
        dBresources.database.collection("Appointment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot querySnapshot : task.getResult())
                {
                    appoinmentLists.add(querySnapshot.toObject(AppointmentReqList.class));
                }
            }
        });

        return view;
}
}