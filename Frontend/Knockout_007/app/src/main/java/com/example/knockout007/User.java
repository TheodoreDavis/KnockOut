package com.example.knockout007;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class User {
    int mId;
    String mUsername;
    int mAuthLevel;
    int mKills;
    int mDeaths;
    int mServerId;
    LatLng mLoc;
    int mAccuracy;
    Marker mMarker;
    Boolean is_target;

    public User(){
        mId = 0;
        mUsername = null;
        mAuthLevel = 0;
        mKills = 0;
        mDeaths = 0;
        mServerId = 0;
        mLoc = null;
        mAccuracy = 0;
        mMarker = null;
    }

    public User(int id, String username,
                int authLevel,
                int kills, int deaths,
                int serverId,
                LatLng loc, int accuracy){
        mId = id;
        mUsername = username;
        mAuthLevel = authLevel;
        mKills = kills;
        mDeaths = deaths;
        mServerId = serverId;
        mLoc = loc;
        mAccuracy = accuracy;
        mMarker = null;
    }

    public User(String username,
                LatLng loc){
        mId = 0;
        mUsername = username;
        mAuthLevel = 0;
        mKills = 0;
        mDeaths = 0;
        mServerId = 0;
        mLoc = loc;
        mAccuracy = 0;
        mMarker = null;
    }

    public Marker getMarker() {
        return mMarker;
    }

    public void setMarker(Marker marker){
        mMarker = marker;
    }

    public String getUsername(){
        return mUsername;
    }

    public LatLng getLoc(){
        return mLoc;
    }

    public MarkerOptions setLoc(LatLng loc){
        mLoc = loc;

        MarkerOptions options = new MarkerOptions();
        options.position(mLoc);
        options.title(mUsername);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        return options;
    }
}
