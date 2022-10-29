package com.example.amenite.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class CustomerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static ArrayList<CustomerList> customerList=new ArrayList<CustomerList>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customerlist);
        setUpList();

    }

    private void setUpList ()
    {
        recyclerView=(RecyclerView)findViewById(R.id.AdminEmployeeListRecyclerView);
        CustomerAdapter adapter=new CustomerAdapter(this,  customerList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DBresources dBresources = new DBresources();

        recyclerView.setAdapter(adapter);

        dBresources.database.collection("User").whereEqualTo("Role","Customer").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange documentChange : value.getDocumentChanges())
                        {
                            if(documentChange.getType()==DocumentChange.Type.ADDED)
                            {
                               customerList.add( documentChange.getDocument().toObject(CustomerList.class) );
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
        });

/*
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference.getChild("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    CustomerList user= dataSnapshot.getValue(CustomerList.class);
                    customerList.add(user);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }






}