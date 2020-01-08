package com.example.knockout007;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONHandlerInterface {

    boolean JSONHandler(JSONObject fullJson);

    void updateInventory(JSONObject json);

    boolean rSpotted(JSONObject json);

    boolean  rNearbyPlayers(JSONObject json);

    boolean sLocation(JSONObject json);
}
