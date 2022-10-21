package com.example.amenite.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.amenite.Customer.Services.CustomerBeautyServiceActivity;
import com.example.amenite.R;
import com.example.amenite.databinding.ActivityCustomerAddressMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CustomerAddressMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "Amenite_check";
    private static final int LOCATION_PERMISSION_CODE = 1;
    private double land;
    private double latd;
    private LatLng latLng;
    private String location;

    private GoogleMap mMap;
    private ActivityCustomerAddressMapsBinding binding;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerAddressMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Initalize();

        binding.CustomerAddressMapSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                Intent intent = new Intent(CustomerAddressMapsActivity.this, (Class<?>) intent1.getSerializableExtra("Activity"));
                intent.putExtra("Address",binding.CustomerAddressMapAddressTextview.getText().toString());
                intent.putExtra("Latitude",latd);
                intent.putExtra("Longitude",land);
                Bundle extra = getIntent().getExtras();
                intent.putExtras(extra);
                startActivity(intent);
                finish();
            }
        });

        binding.CustomerAddressMapAddressSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = binding.CustomerAddressMapAddressSearch.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || location != "") {
                    Geocoder geocoder = new Geocoder(CustomerAddressMapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void Initalize() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.CustomerAddressMap);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                if (ActivityCompat.checkSelfPermission(CustomerAddressMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CustomerAddressMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationPermission();
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                View zoomButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("2")).
                        getParent()).findViewById(Integer.parseInt("1"));
                RelativeLayout.LayoutParams rlp1 = (RelativeLayout.LayoutParams) zoomButton.getLayoutParams();
                rlp1.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                rlp1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                rlp1.setMargins(0, 1500, 180, 0);

                googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).
                        getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                rlp.setMargins(0, 1400, 180, 0);


                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        latLng = mMap.getCameraPosition().target;
                        googleMap.clear();
                        try {
                            String lat = latLng.latitude + "";
                            String lng = latLng.longitude + "";
                            land = latLng.longitude;
                            latd = latLng.latitude;
                            location = getAddress(latLng.latitude, latLng.longitude);
                            binding.CustomerAddressMapAddressTextview.setText(location);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


    }


    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                System.out.println("size====" + addresses.size());
                Address address = addresses.get(0);

                for (int i = 0; i <= addresses.get(0).getMaxAddressLineIndex(); i++) {
                    if (i == addresses.get(0).getMaxAddressLineIndex()) {
                        result.append(addresses.get(0).getAddressLine(i));
                    } else {
                        result.append(addresses.get(0).getAddressLine(i) + ",");
                    }
                }
                System.out.println("ad==" + address);
                System.out.println("result---" + result.toString());
            }
        } catch (IOException e) {
        }

        return result.toString();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationPermission();
    }


    private void locationPermission() {
        if (ActivityCompat.checkSelfPermission(CustomerAddressMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(CustomerAddressMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
        LocationManager lm = (LocationManager) CustomerAddressMapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(CustomerAddressMapsActivity.this)
                    .setMessage("GPS Service Not Available")
                    .setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        Initalize();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle extras = getIntent().getExtras();

        Intent intent = new Intent(CustomerAddressMapsActivity.this, (Class<?>) getIntent().getSerializableExtra("Activity"));  intent.putExtras(extras);
        startActivity(intent);
    }

    /*private void PlaceAutoComplete()
    {
        String apiKey = getString(R.string.ApiKey);
        if(!Places.isInitialized())
        {
            Places.initialize(getApplicationContext(),apiKey);
        }
        Log.d(TAG, "onCreate: "+Place.Field.NAME);
        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // mapFragment.getMapAsync(CustomerAddressMapsActivity.this);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
    */


}