package com.example.amenite.Employee;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.amenite.Customer.Appointmentlist.AppointmentList;
import com.example.amenite.Customer.CustomerActivity;
import com.example.amenite.Customer.CustomerAppointmentDetailsActivity;
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
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

        DBresources dBresources = new DBresources();
        binding.employeehomecurrentappointmentcompletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.employeehomecurrentappointmentcompletebutton.getText().equals("Start"))
                {
                    String sDate1 = new String(appointmentLists.get(0).getAppointment_Date() + ":" + appointmentLists.get(0).getAppointment_Time());
                    sDate1 = formatdate(sDate1);
                    SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy:hh:mm aa");
                    try {
                        Date then = formatter1.parse(sDate1);
                        Date now = new Date();
                        long diffInMillies1 = (now.getTime() - then.getTime());
                        Log.d(TAG.TAG, "onClick: ");
                        if(diffInMillies1<0)
                        {
                            Toast.makeText(getContext(),"You can not start this Task now",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dBresources.database.collection("Appointment").document(binding.employeehomecurrentappointment.employeehomeappid.getText().toString())
                                    .update("Appointment_Status","Started",
                                            "Start_Time",new Date());
                            binding.employeehomecurrentappointmentcompletebutton.setText("Complete");
                            homeset();
                        }
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    dBresources.database.collection("Appointment").document(binding.employeehomecurrentappointment.employeehomeappid.getText().toString())
                            .update("Appointment_Status","Completed",
                                    "Complete_Time",new Date());
                    startActivity(new Intent(getActivity(),EmployeeActivity.class));
                }
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

                             if (queryDocumentSnapshot.get("Appointment_Status").toString().equals("Accepted")||queryDocumentSnapshot.get("Appointment_Status").toString().equals("Started")) {
                                appointmentLists.add(queryDocumentSnapshot.toObject(AppointmentList.class));
                            }
                        }
                        appointmentLists.stream()
                                .sorted(Comparator.comparing(AppointmentList::getAppointment_Time))
                                .collect(Collectors.toList());
                    }
                });
        Tasks.whenAllSuccess(t1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {

                Log.d(TAG.TAG, "onSuccess: "+appointmentLists.size());
                mSwipeRefreshLayout.setRefreshing(false);
                if (appointmentLists.size() == 1) {

                    String sDate1 = new String(appointmentLists.get(0).getAppointment_Date() + ":" + appointmentLists.get(0).getAppointment_Time());
                    sDate1 = formatdate(sDate1);
                    SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy:hh:mm aa");
                    try {
                        Date then = formatter1.parse(sDate1);
                        Date now = new Date();
                        long diffInMillies1 = (then.getTime() - now.getTime());
                        if (diffInMillies1 > 0) {
                            sDate1 = new String(appointmentLists.get(0).getAppointment_Date() + ":" + appointmentLists.get(0).getAppointment_Time());
                            sDate1 = formatdate(sDate1);
                            then = formatter1.parse(sDate1);
                            binding.curenttext.setText("Next Appointment");

                        } else {

                            binding.curenttext.setText("Current Appointment");
                        }
                        long diffInMillies = (then.getTime() - now.getTime());
                        binding.countdownView.start(diffInMillies);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    binding.employeehomenextappointmentcard.setVisibility(View.GONE);

                    binding.employeehomecurrentappointment.EmployeehomeCardviewTimeTextview.setText(appointmentLists.get(0).getRequest_Date() + ", " + appointmentLists.get(0).getRequest_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewIdTextview.setText(appointmentLists.get(0).getAppointment_Id());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewAppointmentTimeTextview.setText(appointmentLists.get(0).getAppointment_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewAppointmentDateTextview.setText(appointmentLists.get(0).getAppointment_Date());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewStatusTextview.setText(appointmentLists.get(0).getAppointment_Status());
                } else if (appointmentLists.size() == 0) {
                    binding.employeehomenextappointmentcard.setVisibility(View.GONE);
                    binding.employeehomecurrentlayout.setVisibility(View.GONE);
                    binding.employeehomenotcurrent.setVisibility(View.VISIBLE);
                } else {
                    String sDate1 = new String(appointmentLists.get(0).getAppointment_Date() + ":" + appointmentLists.get(0).getAppointment_Time());
                    sDate1 = formatdate(sDate1);
                    SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy:hh:mm aa");
                    try {
                        Date then = formatter1.parse(sDate1);
                        Date now = new Date();

                        long diffInMillies1 = (then.getTime() - now.getTime());
                        if (diffInMillies1 > 0) {
                            sDate1 = new String(appointmentLists.get(0).getAppointment_Date() + ":" + appointmentLists.get(0).getAppointment_Time());
                            sDate1 = formatdate(sDate1);
                            then = formatter1.parse(sDate1);
                            binding.curenttext.setText("Next Appointment");
                            binding.countdownView.start(then.getTime()-now.getTime());
                            binding.employeehomecurrentappointmentcompletebutton.setText("Start");
                        }else {
                            sDate1 = new String(appointmentLists.get(1).getAppointment_Date() + ":" + appointmentLists.get(1).getAppointment_Time());
                           sDate1 = formatdate(sDate1);
                            then = formatter1.parse(sDate1);
                            binding.countdownView.start(then.getTime()-now.getTime());
                            if(!binding.employeehomecurrentappointmentcompletebutton.getText().toString().equals("Start"))
                            binding.employeehomecurrentappointmentcompletebutton.setText("Start");
                            binding.curenttext.setText("Current Appointment");
                            binding.employeehomenextappointmentcard.setVisibility(View.VISIBLE);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG.TAG, "onSuccess: "+appointmentLists.get(0).getRequest_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewTimeTextview.setText(appointmentLists.get(0).getRequest_Date() + ", " + appointmentLists.get(0).getRequest_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewIdTextview.setText(appointmentLists.get(0).getAppointment_Id());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewAppointmentTimeTextview.setText(appointmentLists.get(0).getAppointment_Time());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewAppointmentDateTextview.setText(appointmentLists.get(0).getAppointment_Date());
                    binding.employeehomecurrentappointment.EmployeehomeCardviewStatusTextview.setText(appointmentLists.get(0).getAppointment_Status());
                    binding.employeehomenextappointment.EmployeehomeCardviewTimeTextview.setText(appointmentLists.get(1).getRequest_Date() + ", " + appointmentLists.get(1).getAppointment_Time());
                    binding.employeehomenextappointment.EmployeehomeCardviewIdTextview.setText(appointmentLists.get(1).getAppointment_Id());
                    binding.employeehomenextappointment.EmployeehomeCardviewAppointmentTimeTextview.setText(appointmentLists.get(1).getAppointment_Time());
                    binding.employeehomenextappointment.EmployeehomeCardviewAppointmentDateTextview.setText(appointmentLists.get(1).getAppointment_Date());
                    binding.employeehomenextappointment.EmployeehomeCardviewStatusTextview.setText("Accepted");

                }
            }
        });
        binding.employeehomenextappointmentCallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dBresources.database.collection("Appointment").document(appointmentLists.get(1).getAppointment_Id())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.d(TAG.TAG, "onSuccess: "+documentSnapshot);
                                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                                dialIntent.setData(Uri.parse("tel:" + documentSnapshot.get("Phone_Number")));
                                startActivity(dialIntent);
                            }
                        });
            }
        });
        binding.employeehomenextappointmentcancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder confirmlogout = new AlertDialog.Builder(getContext());
                confirmlogout.setMessage("Are you sure?");
                confirmlogout.setTitle("Cancel");
                confirmlogout.setCancelable(false);
                confirmlogout.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) ->
                {
                    dBresources.database.collection("Appointment").document(appointmentLists.get(0).getAppointment_Id())
                            .update("Appointment_Status","Cancelled");
                    startActivity(new Intent(getActivity(),EmployeeActivity.class));
                });
                confirmlogout.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->

                {
                    dialog.cancel();
                });
                AlertDialog alertDialog = confirmlogout.create();
                alertDialog.show();
            }
        });

    }
    public String formatdate(String s)
    {
        String date1 =appointmentLists.get(0).getAppointment_Date().toString();
        String[] part;
        part = date1.split("-");
        Log.d(TAG.TAG, "formatdate: "+s);
        String sDate1 = s;
        if(part[1].length()>2)
        {
        part[1]=part[1].substring(0,1)+part[1].substring(1,3).toLowerCase();
        DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH); // supports Jan, Feb etc
        MONTH_FORMAT.parse(part[1]).get(ChronoField.MONTH_OF_YEAR);
        Month month = Month.from(MONTH_FORMAT.parse(part[1]));
        String hr, m, d, mo, y;
        String[] parts = sDate1.split("-");
        Integer n =month.getValue();
        part[1]=n.toString();
        if (parts[0].length() == 1) parts[0] = "0" + parts[0];
        if (part[1].length() == 1)
            part[1]="0"+part[1];
        sDate1 = parts[0]+"-"+part[1]+"-"+parts[2];}
        else
        {
            String hr, m, d, mo, y;
            String[] parts = sDate1.split("-");
            if (parts[0].length() == 1) parts[0] = "0" + parts[0];
            if (parts[1].length() == 1)
                parts[1]="0"+parts[1];
            sDate1=parts[0]+"-"+parts[1]+"-"+parts[2];
        }
        return sDate1;
    }

}