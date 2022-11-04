package com.example.amenite.Customer.Services.Carrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.amenite.Customer.CustomerAddressMapsActivity;
import com.example.amenite.Customer.Services.CustomerBeautyAppointmentConfirmActivity;
import com.example.amenite.R;
import com.example.amenite.TAG;
import com.example.amenite.databinding.ActivityCustomerServiceCarrentalAreaBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.time.Month;
import java.util.Calendar;

public class CustomerServiceCarrentalAreaActivity extends AppCompatActivity {
    private ActivityCustomerServiceCarrentalAreaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerServiceCarrentalAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbararea.appbartitle.setText("Car Rental");
        binding.toolbararea.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //spinner
        String[] arraySpinner = new String[] {
                "Inside City(Dhaka)","Outside City"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomerServiceCarrentalAreaActivity.this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        binding.carrenterspiner.setAdapter(adapter);
        //end
        Intent intent = new Intent(CustomerServiceCarrentalAreaActivity.this,CarRentFeeActivity.class);
        Intent intent1 = getIntent();
        Bundle bundle = getIntent().getExtras();
        intent.putExtras(bundle);
        String pickaddress = null, destaddres=null;
        if(intent.getStringExtra("Pickup")!=null) {

            pickaddress = intent.getStringExtra("Pickup");
            intent.putExtra("Pickup_Latitude",getIntent().getStringExtra("Pickup_Latitude"));
            intent.putExtra("Pickup_Longitude",getIntent().getStringExtra("Pickup_Longitude"));
        }
        else{

            pickaddress = intent.getStringExtra("AddressMap");
            intent.putExtra("Pickup_Latitude",getIntent().getStringExtra("Latitude"));
            intent.putExtra("Pickup_Longitude",intent.getStringExtra("Longitude"));

        }
        binding.picuplocation.setText(pickaddress);
        intent.putExtra("Pickup",pickaddress);

        if(intent.getStringExtra("Destination")!=null)
        {
            destaddres = intent.getStringExtra("Destination");
        }
        Log.d(TAG.TAG, "onCreate: "+intent.getStringExtra("Destination_Latitude"));
        binding.destinationlocation.setText(destaddres);
        intent.putExtra("destination",destaddres);

        binding.carrenterspiner.setSelection(adapter.getPosition(intent.getStringExtra("Service_Area")));
        binding.carrenterspiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(arraySpinner[i]=="Outside City")
                {
                    binding.destinationlayout.setVisibility(View.VISIBLE);
                    intent.removeExtra("Service_Area");
                    intent.putExtra("Service_Area","Outside City");
                    binding.timelimitlayout.setVisibility(View.GONE);
                    binding.carrentalareatextview.setText(intent.getStringExtra("Service_Area")+" car rental service");
                }
                else
                {
                    intent.removeExtra("Service_Area");
                    intent.putExtra("Service_Area","Inside City(Dhaka)");

                    binding.timelimitlayout.setVisibility(View.VISIBLE);
                    binding.carrentalareatextview.setText(intent.getStringExtra("Service_Area")+" car rental service");
                    binding.destinationlayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.areaTooglegroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked)
                {
                    if(checkedId==R.id.hourbutton)
                    {
                        binding.servicehourcardview.setVisibility(View.VISIBLE);
                        intent.removeExtra("Time_Limit");
                        intent.putExtra("Time_Limit","Hour");
                        binding.carrentalareatextview.setText(intent.getStringExtra("Service_Area")+" car rental service");


                    }
                    else if(checkedId==R.id.fulldaybutton)
                    {
                        binding.servicehourcardview.setVisibility(View.GONE);
                        intent.removeExtra("Time_Limit");
                        intent.putExtra("Time_Limit","Fullday");
                        binding.carrentalareatextview.setText("Full day "+intent.getStringExtra("Service_Area").toLowerCase()+" car rental service");
                    }
                    else if(checkedId==R.id.halfdaybutton)
                    {
                        binding.servicehourcardview.setVisibility(View.GONE);
                        intent.removeExtra("Time_Limit");
                        intent.putExtra("Time_Limit","Halfday");
                        binding.carrentalareatextview.setText("Half day "+intent.getStringExtra("Service_Area").toLowerCase()+" car rental service");
                    }
                }
            }
        });
        binding.pickupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(CustomerServiceCarrentalAreaActivity.this, CustomerAddressMapsActivity.class);
                intent2.putExtra("Activity",CustomerServiceCarrentalAreaActivity.class);
                if(intent.getStringExtra("target")!=null)
                {
                    intent.removeExtra("target");
                }
                intent.removeExtra("Pickup_Latitude");
                intent.removeExtra("Pickup_Longitude");
                intent.removeExtra("Pickup");
                intent.removeExtra("target");
                intent2.putExtra("target","Pickup");
                intent2.putExtras(intent.getExtras());
                startActivity(intent2);
            }
        });
        binding.destinationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(CustomerServiceCarrentalAreaActivity.this, CustomerAddressMapsActivity.class);
                intent2.putExtra("Activity",CustomerServiceCarrentalAreaActivity.class);
                if(intent.getStringExtra("target")!=null)
                {
                    intent.removeExtra("target");
                }

                intent.removeExtra("Destination_Latitude");
                intent.removeExtra("Destination_Longitude");
                intent.removeExtra("Destination");
                intent.removeExtra("target");
                intent2.putExtra("target","Destination");
                intent2.putExtras(intent.getExtras());
                startActivity(intent2);
            }
        });

        binding.picupdatetimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePick();
                Timepick();
            }
        });
        binding.procedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG.TAG, "onClick: "+intent.getStringExtra("Pickup_Latitude"));

                intent.putExtra("Pickup_Date",binding.picuplocationdate.getText());
                intent.putExtra("Pickup_Time",binding.picuplocationtime.getText());
                if(intent.getStringExtra("Service_Area").equals("Inside City(Dhaka)"))
                {
                    boolean inc =true;
                    if(binding.picuplocation.getText().toString().isEmpty()||binding.picuplocation.getText().toString()==" ")
                    {
                        inc = false;
                    }
                    else
                    {
                        intent.putExtra("Pickup_Location",binding.picuplocation.getText());
                    }

                    if(binding.picuplocationdate.getText().toString().isEmpty()||binding.picuplocationdate.getText().toString()==" ")
                    {
                        inc = false;
                    }
                    else
                    {
                        intent.putExtra("Pickup_Date",binding.picuplocationdate.getText());
                    }
                    if(binding.picuplocationtime.getText().toString().isEmpty()||binding.picuplocationtime.getText().toString()==" ")
                    {
                        inc = false;
                    }
                    else
                    {
                        intent.putExtra("Pickup_Time",binding.picuplocationtime.getText());
                    }

                    if(inc == false)
                    {
                        Toast.makeText(CustomerServiceCarrentalAreaActivity.this,"Invalid data",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startActivity(intent);
                    }
                }
                if(intent.getStringExtra("Service_Area").equals("Outside City"))
                {
                    boolean inc =true;
                    if(binding.picuplocation.getText().toString().isEmpty()||binding.picuplocation.getText().toString()==" ")
                    {
                        inc = false;
                    }
                    else
                    {
                        intent.putExtra("Pickup_Location",binding.picuplocation.getText());
                    }
                    if(binding.destinationlocation.getText().toString().isEmpty()||binding.destinationlocation.getText().toString()==" ")
                    {
                        inc = false;
                    }
                    else
                    {
                        intent.putExtra("Destination_Location",binding.destinationlocation.getText());
                    }
                    if(binding.picuplocationdate.getText().toString().isEmpty()||binding.picuplocationdate.getText().toString()==" ")
                    {
                        inc = false;
                    }
                    else
                    {
                        intent.putExtra("Pickup_Date",binding.picuplocationdate.getText());
                    }
                    if(binding.picuplocationtime.getText().toString().isEmpty()||binding.picuplocationtime.getText().toString()==" ")
                    {
                        inc = false;
                    }
                    else
                    {
                        intent.putExtra("Pickup_Time",binding.picuplocationtime.getText());
                    }

                    if(inc == false)
                    {
                        Toast.makeText(CustomerServiceCarrentalAreaActivity.this,"Invalid data",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startActivity(intent);
                    }
                }
            }
        });


    }
    private void Timepick() {
        final Calendar calendar = Calendar.getInstance();
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                CustomerServiceCarrentalAreaActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(0, 0, 0, i, i1);
                        binding.picuplocationtime.setText(DateFormat.format("hh:mm aa", calendar));
                    }
                }, 12, 0, false
        );
        timePickerDialog.show();
    }

    private void DatePick() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CustomerServiceCarrentalAreaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.picuplocationdate.setText(i2 + "-" + Month.of(i1+1) + "-" + i);
            }
        }, year, month, day);
        datePickerDialog.show();

    }
}