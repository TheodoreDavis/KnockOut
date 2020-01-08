package com.example.knockout007;


import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

public class sendPlayerInfo {
private LocationManager locationManager;
private Location location;



/*
void getLocation() {
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

   // locationManager.requestLocationUpdates(provider, 200, 1, mylistener);
    String a = "" + location.getLatitude();
}

class MyLocationListener implements LocationListener {

    @Override
    public void onProviderDisabled(String provider){};
    @Override
    public void onProviderEnabled(String provider){};

    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){};


}

*/

//TODO, add permission
@SuppressLint("MissingPermission")
double[] getLocation(){
   location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


   double[] latLong = {0,0};
   latLong[0] =  location.getLatitude();
   latLong[1] =  location.getLongitude();
   return latLong;
}



}