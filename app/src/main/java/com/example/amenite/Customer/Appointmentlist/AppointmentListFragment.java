package com.example.amenite.Customer.Appointmentlist;

import android.os.Bundle;

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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AppointmentListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AppoinmentList> appoinmentLists;
    MyAdapter myAdapter;
    public AppointmentListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_appointment_list, container, false);
        DBresources dBresources = new DBresources();
        recyclerView = recyclerView.findViewById(R.id.CustomerApoointmentlistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appoinmentLists = new ArrayList<>();
        myAdapter = new MyAdapter(getContext(),appoinmentLists);
        recyclerView.setAdapter(myAdapter);
        dBresources.database.collection("Appointment").whereEqualTo("Email", User.Emailid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange documentChange : value.getDocumentChanges())
                        {
                            if(documentChange.getType()==DocumentChange.Type.ADDED)
                            {
                                appoinmentLists.add(documentChange.getDocument().toObject(AppoinmentList.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
        return view;
    }
}