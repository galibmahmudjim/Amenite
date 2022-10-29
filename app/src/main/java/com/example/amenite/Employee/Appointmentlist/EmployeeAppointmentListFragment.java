package com.example.amenite.Employee.Appointmentlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.databinding.FragmentEmployeeAppointmentListBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class EmployeeAppointmentListFragment extends Fragment {

   // RecyclerView recyclerView;
    ArrayList<AppointmentList> appoinmentLists;
    MyAdapter myAdapter;
    private FragmentEmployeeAppointmentListBinding binding;
    public EmployeeAppointmentListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeAppointmentListBinding.inflate(inflater, container, false);
        View view =binding.getRoot();
        DBresources dBresources = new DBresources();
        binding.EmployeeApoointmentlistRecyclerview.setHasFixedSize(true);
        binding.EmployeeApoointmentlistRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        appoinmentLists = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(),appoinmentLists);
        binding.EmployeeApoointmentlistRecyclerview.setAdapter(myAdapter);
        dBresources.database.collection("Appointment").whereEqualTo("Employee_Email",User.Emailid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange documentChange : value.getDocumentChanges())
                        {
                            if(documentChange.getType()==DocumentChange.Type.ADDED)
                            {
                                appoinmentLists.add(documentChange.getDocument().toObject(AppointmentList.class));
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
        return view;
    }
}