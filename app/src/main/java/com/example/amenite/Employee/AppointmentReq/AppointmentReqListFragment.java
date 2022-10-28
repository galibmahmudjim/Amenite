package com.example.amenite.Employee.AppointmentReq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AppointmentReqListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AppointmentReqList> appoinmentLists;
    MyAdapter myAdapter;
    public AppointmentReqListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_appointment_list, container, false);
        DBresources dBresources = new DBresources();
        recyclerView = view.findViewById(R.id.EmployeeApoointmentlistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appoinmentLists = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(),appoinmentLists);
        recyclerView.setAdapter(myAdapter);
        dBresources.database.collection("Appointment").whereEqualTo("Email",User.Emailid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange documentChange : value.getDocumentChanges())
                        {
                            if(documentChange.getType()==DocumentChange.Type.ADDED)
                            {
                                appoinmentLists.add(documentChange.getDocument().toObject(AppointmentReqList.class));
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
        return view;
    }
}