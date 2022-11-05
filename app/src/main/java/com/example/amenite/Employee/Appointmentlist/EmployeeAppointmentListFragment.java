package com.example.amenite.Employee.Appointmentlist;

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
import com.example.amenite.databinding.FragmentEmployeeAppointmentListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class EmployeeAppointmentListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AppointmentList> appointmentLists;
    MyAdapter myAdapter;
    private FragmentEmployeeAppointmentListBinding binding;

    public EmployeeAppointmentListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeAppointmentListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        DBresources dBresources = new DBresources();
        recyclerView = view.findViewById(R.id.EmployeeApoointmentlistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appointmentLists = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), appointmentLists);
        recyclerView.setAdapter(myAdapter);
        binding.EmployeeAppointmentListShimmer.startShimmer();

        dBresources.database.collection("Appointment").whereEqualTo("Employee",User.Emailid)

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange documentChange : value.getDocumentChanges())
                        {
                            if(documentChange.getType()==DocumentChange.Type.ADDED)
                            {
                                appointmentLists.add(documentChange.getDocument().toObject(AppointmentList.class));
                            }
                        }
                        binding.EmployeeAppointmentListShimmer.stopShimmer();
                        binding.EmployeeAppointmentListShimmer.setVisibility(View.GONE);
                        binding.EmployeeApoointmentlistRecyclerview.setVisibility(View.VISIBLE);
                        myAdapter.notifyDataSetChanged();
                    }
                });

        return view;
    }
}