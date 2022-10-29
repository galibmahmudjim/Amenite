package com.example.amenite.Admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EmployeeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static ArrayList<EmployeeList> employeeList=new ArrayList<EmployeeList>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employeelist);
        setUpList();

    }

    private void setUpList ()
    {
        recyclerView=(RecyclerView)findViewById(R.id.AdminEmployeeListRecyclerView);
        EmployeeAdapter adapter=new EmployeeAdapter(this,  employeeList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DBresources dBresources = new DBresources();

        recyclerView.setAdapter(adapter);

        dBresources.database.collection("User").whereEqualTo("Role","Employee").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges())
                {
                    if(documentChange.getType()==DocumentChange.Type.ADDED)
                    {
                        employeeList.add( documentChange.getDocument().toObject(EmployeeList.class) );
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });



    }





}
