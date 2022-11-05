package com.example.amenite.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.text.format.DateUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

        //String remaining1 = DateUtils.formatElapsedTime ((then.getTime() - now.getTime())/1000); // Remaining time to seconds

        binding.employeehomecurrentappointmentcompletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaskCompleteActivity.class);
                DBresources dBresources = new DBresources();
                dBresources.database.collection("Appointment").document(appointmentLists.get(0).getAppointment_Id())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                intent.putExtra("Email", documentSnapshot.get("Email").toString());
                                dBresources.database.collection("Appointment")
                                        .document(appointmentLists.get(0).getAppointment_Id())
                                        .update("Status", "Completed");
                                startActivity(intent);
                            }
                        });
            }
        });
        TextView toolbartextview = getActivity().findViewById(R.id.EmployeeActivityToolbarTextview);
        return view;
    }

    void homeset() {
        DBresources dBresources = new DBresources();
        appointmentLists = new ArrayList<>();
        Task t1 = dBresources.database.collection("Appointment").whereEqualTo("Employee", User.Emailid)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            if (queryDocumentSnapshot.get("Status").equals("Accepted")) {
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
                if (appointmentLists.size() == 1) {

                    String sDate1 = new String(appointmentLists.get(0).getAppointment_Date()+":"+appointmentLists.get(0).getAppointment_Time());
                    String hr,m,d,mo,y;
                    String[] parts = sDate1.split("-");
                    if(parts[0].length()==1)sDate1="0"+sDate1;
                    if(parts[1].length()==1)sDate1=sDate1.substring(0,3)+"0"+sDate1.substring(3,sDate1.length());
                    SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy:hh:mm aa");
                    try {
                        Date then= formatter1.parse(sDate1);
                        Date now = new Date();
                        long diffInMillies1 = (now.getTime() - then.getTime());
                        Log.d(TAG.TAG, "onSuccess: "+diffInMillies1);
                        if(diffInMillies1<0)
                        {
                            sDate1 = new String(appointmentLists.get(1).getAppointment_Date()+":"+appointmentLists.get(1).getAppointment_Time());
                            String[] parts1 = sDate1.split("-");
                            if(parts1[0].length()==1)sDate1="0"+sDate1;
                            if(parts1[1].length()==1)sDate1=sDate1.substring(0,3)+"0"+sDate1.substring(3,sDate1.length());
                            then = formatter1.parse(sDate1);
                            binding.curenttext.setText("Next Appointment");
                            binding.employeehomenextappointmentcard.setVisibility(View.GONE);
                        }
                        else
                        {

                            binding.curenttext.setText("Current Appointment");
                            binding.employeehomenextappointmentcard.setVisibility(View.VISIBLE);
                        }
                        long diffInMillies = Math.abs(now.getTime() - then.getTime());
                        binding.countdownView.start(diffInMillies);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    binding.employeehomenextappointmentcard.setVisibility(View.GONE);
                    binding.employeehomecurrentappointment.EmployeehomeCardviewTimeTextview.setText(appointmentLists.get(0).getRequest_Date() + ", " + appointmentLists.get(0).getAppointment_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewIdTextview.setText(appointmentLists.get(0).getAppointment_Id());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewAppointmentTimeTextview.setText(appointmentLists.get(0).getAppointment_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewAppointmentDateTextview.setText(appointmentLists.get(0).getAppointment_Date());
                } else if (appointmentLists.size() == 0) {
                    binding.employeehomenextappointmentcard.setVisibility(View.GONE);
                    binding.employeehomecurrentlayout.setVisibility(View.GONE);
                    binding.employeehomenotcurrent.setVisibility(View.VISIBLE);
                } else {
                    String sDate1 = new String(appointmentLists.get(0).getAppointment_Date()+":"+appointmentLists.get(0).getAppointment_Time());
                    String hr,m,d,mo,y;
                    String[] parts = sDate1.split("-");
                    if(parts[0].length()==1)sDate1="0"+sDate1;
                    if(parts[1].length()==1)sDate1=sDate1.substring(0,3)+"0"+sDate1.substring(3,sDate1.length());
                    SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy:hh:mm aa");
                    try {
                        Date then= formatter1.parse(sDate1);
                        Date now = new Date();
                        long diffInMillies1 = (now.getTime() - then.getTime());
                        Log.d(TAG.TAG, "onSuccess: "+diffInMillies1);
                        if(diffInMillies1<0)
                        {
                            sDate1 = new String(appointmentLists.get(1).getAppointment_Date()+":"+appointmentLists.get(1).getAppointment_Time());
                            String[] parts1 = sDate1.split("-");
                            if(parts1[0].length()==1)sDate1="0"+sDate1;
                            if(parts1[1].length()==1)sDate1=sDate1.substring(0,3)+"0"+sDate1.substring(3,sDate1.length());
                            then = formatter1.parse(sDate1);
                            binding.curenttext.setText("Next Appointment");
                            binding.employeehomenextappointmentcard.setVisibility(View.GONE);
                        }
                        else
                        {

                            binding.curenttext.setText("Current Appointment");
                            binding.employeehomenextappointmentcard.setVisibility(View.VISIBLE);
                        }
                        long diffInMillies = Math.abs(now.getTime() - then.getTime());
                        binding.countdownView.start(diffInMillies);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    binding.employeehomecurrentappointment.EmployeehomeCardviewTimeTextview.setText(appointmentLists.get(0).getAppointment_Date() + ", " + appointmentLists.get(0).getAppointment_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewIdTextview.setText(appointmentLists.get(0).getAppointment_Id());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewAppointmentTimeTextview.setText(appointmentLists.get(0).getAppointment_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewAppointmentDateTextview.setText(appointmentLists.get(0).getAppointment_Date());
                    binding.employeehomenextappointment.EmployeehomeCardviewTimeTextview.setText(appointmentLists.get(1).getRequest_Date() + ", " + appointmentLists.get(1).getAppointment_Time());
                    binding.employeehomenextappointment.EmployeehomeCardviewIdTextview.setText(appointmentLists.get(1).getAppointment_Id());
                    binding.employeehomenextappointment.EmployeehomeCardviewAppointmentTimeTextview.setText(appointmentLists.get(1).getAppointment_Time());
                    binding.employeehomenextappointment.EmployeehomeCardviewAppointmentDateTextview.setText(appointmentLists.get(1).getAppointment_Date());

                }
            }
        });
    }

}