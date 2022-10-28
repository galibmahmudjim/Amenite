package com.example.amenite.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.amenite.Admin.CustomerAdapter;
import com.example.amenite.Admin.CustomerList;
import com.example.amenite.Customer.Appointmentlist.AppoinmentList;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



import java.util.ArrayList;

public class CustomerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static ArrayList<CustomerList> customerList=new ArrayList<CustomerList>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        setUpList();

    }

    private void setUpList ()
    {
        recyclerView=(RecyclerView)findViewById(R.id.AdminCustomerListRecyclerView);
        CustomerAdapter adapter=new CustomerAdapter(this,customerList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DBresources dBresources = new DBresources();

        dBresources.database.collection("User").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges())
                {
                    if(documentChange.getType()==DocumentChange.Type.ADDED)
                    {
                       
                    }
                }

                adapter.notifyDataSetChanged();

            }
        });
    }






}