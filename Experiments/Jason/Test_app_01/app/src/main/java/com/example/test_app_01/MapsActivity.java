package com.example.test_app_01;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import java.lang.*;
import java.util.Locale;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.*;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.tasks.OnSuccessListener;


import android.content.pm.PackageManager;
import android.location.Location;

public class MapsActivity extends FragmentActivity
        implements
        OnMapReadyCallback,
        OnMapClickListener,
        OnMyLocationClickListener,
        OnMyLocationButtonClickListener{

    private GoogleMap mMap;
    private int LOCATION_PERMISSION_REQUEST_CODE = 1252;
    private FusedLocationProviderClient mFusedLocationClient;
    double wayLatitude = 0;
    double wayLongitude = 0;
    String txtLocation = "No Data";

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Construct a FusedLocationProviderClient.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        //txtLocation.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude);
                        txtLocation.format("%s -- %s", wayLatitude, wayLongitude);

                        Toast.makeText(MapsActivity.this, txtLocation, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };


        //mMap.setOnMapClickListener(this.onMapClick);
        // Setting a click event handler for the map
        /*mMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });*/
        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            //@Override
            public void onMapClick(LatLng point) {
                LatLng xy = point;
                //String str = xy.toString();
                //Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
                //Log.d("DEBUG","Map clicked [" + point.latitude + " / " + point.longitude + "]");
                //Do your stuff with LatLng here
                //Then pass LatLng to other activity
            }
        });*/
    }

   // public void onMapClick(LatLng point) {
    //    LatLng xy = point;
        //String str = xy.toString();
        //Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        //Log.d("DEBUG","Map clicked [" + point.latitude + " / " + point.longitude + "]");
        //Do your stuff with LatLng here
        //Then pass LatLng to other activity
    //}


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //Marker marker = new Marker({position:})
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(this);

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    private void enableMyLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        //    PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
        //            Manifest.permission.ACCESS_FINE_LOCATION, true);
            Toast toast = Toast.makeText(getApplicationContext(), "failed location", Toast.LENGTH_LONG);
            toast.show();
        } else if(mMap != null){
            mMap.setMyLocationEnabled(true);
            Toast toast = Toast.makeText(getApplicationContext(), "location enabled", Toast.LENGTH_LONG);
            toast.show();

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    public void onMapClick(LatLng point){
        Toast toast = Toast.makeText(getApplicationContext(), point.toString(), Toast.LENGTH_LONG);
        toast.show();

        mMap.getMyLocation();

    }

    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    public void onRequestPermissionsResults(int requestCode, @NonNull String[] permissions,
                                            @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1252:{
                try {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            mFusedLocationClient.getLastLocation().addOnSuccessListener(this,
                                    new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if (location != null) {
                                                wayLatitude = location.getLatitude();
                                                wayLongitude = location.getLongitude();
                                                txtLocation.format(Locale.US,
                                                        "%s -- %s", wayLatitude, wayLongitude);
                                                Toast.makeText(MapsActivity.this, txtLocation, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                catch(Exception exception){

                }
            }
        }
    }


}
