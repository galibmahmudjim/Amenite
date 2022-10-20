package com.example.amenite.Customer.Appointmentlist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AppointmentListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<AppoinmentList> appoinmentLists;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        DBresources dBresources = new DBresources();
        recyclerView = findViewById(R.id.CustomerApoointmentlistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appoinmentLists = new ArrayList<>();
        myAdapter = new MyAdapter(AppointmentListActivity.this,appoinmentLists);
        recyclerView.setAdapter(myAdapter);
        dBresources.database.collection("Appointment").whereEqualTo("Email",User.Emailid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange documentChange : value.getDocumentChanges())
                        {
                            if(documentChange.getType()==DocumentChange.Type.ADDED)
                            {
                                appoinmentLists.add(documentChange.getDocument().toObject(AppoinmentList.class));
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
    }
}