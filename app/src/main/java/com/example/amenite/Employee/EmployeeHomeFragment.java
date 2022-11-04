package com.example.amenite.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.amenite.Customer.Appointmentlist.AppointmentList;
import com.example.amenite.DBRes.DBresources;
import com.example.amenite.PROFILE.User;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.example.amenite.databinding.FragmentEmployeeHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeHomeFragment extends Fragment {

    private FragmentEmployeeHomeBinding binding;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<AppointmentList> appointmentLists;
    public EmployeeHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEmployeeHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        appointmentLists = new ArrayList<>();
         mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        homeset();

        binding.employeehomecurrentappointmentcompletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TaskCompleteActivity.class);
                DBresources dBresources = new DBresources();
                dBresources.database.collection("Appointment").document(appointmentLists.get(1).getAppointment_Id())
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                intent.putExtra("Email",documentSnapshot.get("Email").toString());
                                dBresources.database.collection("Appointment")
                                                .document(appointmentLists.get(0).getAppointment_Id())
                                                        .update("Status","Completed");
                                startActivity(intent);
                            }
                        });
            }
        });
        TextView toolbartextview = getActivity().findViewById(R.id.EmployeeActivityToolbarTextview);
        return view;
    }
    void homeset()
    {
        DBresources dBresources = new DBresources();
        appointmentLists = new ArrayList<>();
        Task t1 = dBresources.database.collection("Appointment").whereEqualTo("Employee",User.Emailid)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots)
                        {
                            if(queryDocumentSnapshot.get("Status").equals("Accepted"))
                            {
                                appointmentLists.add(queryDocumentSnapshot.toObject(AppointmentList.class));
                            }
                        }
                        appointmentLists.stream()
                                .sorted(Comparator.comparing(AppointmentList::getAppointment_Date))
                                .collect(Collectors.toList());
                    }
                });
        Tasks.whenAllSuccess(t1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
                mSwipeRefreshLayout.setRefreshing(false);
                binding.employeehomecurrentappointment.EmployeeAppointmentlistCardviewTimeTextview.setText(appointmentLists.get(1).getRequest_Date()+", "+appointmentLists.get(1).getAppointment_Time());
                binding.employeehomecurrentappointment.EmployeeAppointmentlistCardviewIdTextview.setText(appointmentLists.get(1).getAppointment_Id());
                binding.employeehomecurrentappointment.EmployeeAppointmentlistCardviewServiceTextview.setText(appointmentLists.get(1).getService());
                binding.employeehomecurrentappointment.EmployeeAppointmentlistRating.setVisibility(View.GONE);
                binding.employeehomecurrentappointment.EmployeeAppointmentlistCardviewAppointmentTimeTextview.setText(appointmentLists.get(1).getAppointment_Time());
                binding.employeehomecurrentappointment.EmployeeAppointmentlistCardviewAppointmentDateTextview.setText(appointmentLists.get(1).getAppointment_Date());
                binding.employeehomenextappointment.EmployeeAppointmentlistCardviewTimeTextview.setText(appointmentLists.get(1).getRequest_Date()+", "+appointmentLists.get(1).getAppointment_Time());
                binding.employeehomenextappointment.EmployeeAppointmentlistCardviewIdTextview.setText(appointmentLists.get(2).getAppointment_Id());
                binding.employeehomenextappointment.EmployeeAppointmentlistCardviewServiceTextview.setText(appointmentLists.get(2).getService());
                binding.employeehomenextappointment.EmployeeAppointmentlistRating.setVisibility(View.GONE);
                binding.employeehomenextappointment.EmployeeAppointmentlistCardviewAppointmentTimeTextview.setText(appointmentLists.get(2).getAppointment_Time());
                binding.employeehomenextappointment.EmployeeAppointmentlistCardviewAppointmentDateTextview.setText(appointmentLists.get(2).getAppointment_Date());

            }
        });
    }

}