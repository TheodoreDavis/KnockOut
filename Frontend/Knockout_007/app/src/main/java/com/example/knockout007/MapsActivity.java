package com.example.knockout007;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Semaphore;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import static android.provider.SettingsSlicesContract.KEY_LOCATION;


/**
 * The main screen of the application. Contains an interactive map and the ability to move to other screens.
 */
public class MapsActivity extends AppCompatActivity
        implements  ServiceConnection,
                    OnMapReadyCallback,
                    WebSocketsService.CallBack,
                    GoogleMap.OnMarkerClickListener {

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private Location mLastKnownLocation;
    private Map<String, User> users;
    private boolean permissionGranted = false;
    private MapsActivity thisActivity = this;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    //User Variables
    private Location lastLocation;
    private Marker currentLocation;

    //WebSocket Variables
    private String serverUrl = "http://cs309-ad-8.misc.iastate.edu:8080/websocket/";
    private JSONObject user_token = new JSONObject();
    private JSONObject session_token = new JSONObject();

    //Buttons
    private Button btnGameInfo;
    private Button btnInventory;
    private int sem;
    private String mess;
    private JSONObject userObj;
    private WebSocketsService wsService;
    private Boolean sent_200 = false;
    private RequestQueue mQueue;


    //Initializes the map
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mQueue = Volley.newRequestQueue(this);


        //Use fusedLocationClient to get last known location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        users = new HashMap<>();

        //Init Buttons
        btnGameInfo = findViewById(R.id.btnGameInfo);
        btnInventory = findViewById(R.id.btnInventory);
        Intent intent = getIntent();
//        authenticator = intent.getStringExtra("authenticator");
//        expiration = intent.getStringExtra("expiration");
        try {
            user_token.put("authenticator", intent.getStringExtra("user_authenticator"));
            user_token.put("expiration", intent.getStringExtra("user_expiration"));
            session_token.put("authenticator", intent.getStringExtra("session_authenticator"));
            session_token.put("expiration", intent.getStringExtra("session_expiration"));
    } catch (JSONException e) {
        e.printStackTrace();
    }
        sem = 0;


    }

    @Override
    protected void onResume() {

//            if (!isMyServiceRunning(wsService.getClass())) {
//                wsService = new WebSocketsService(user_token);
//            }
        super.onResume();
        Intent service = new Intent(this, WebSocketsService.class);
        bindService(service, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wsService != null) {
            wsService.setCallBack(null);
            unbindService(this);
        }
    }


    @Override


    public void onMapReady(GoogleMap map) {

        mMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            //return;
        }
        else{
            permissionGranted = true;
        }

        if(permissionGranted == true) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(5000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            //fusedLocationClient.removeLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(true);

//            lastLocation = fusedLocationClient.getLastLocation().getResult();
//
//            LatLng lastLatLng;
//            lastLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
//
//
//            mMap.addMarker(new MarkerOptions().position(lastLatLng));
            btnGameInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { toGameInfoScreen();}
            });
            btnInventory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { toInventory();}
            });
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

                @Override
                public void onMapClick(LatLng latLng) {

                    if( !sent_200 ){
                        wsService.sendToServer(200, null);
                        sent_200 = true;
                    }
                    wsService.sendToServer(202, null);
                }
            });

            mMap.setOnMarkerClickListener(this);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
//                //The last location in the list is the newest
//                Location location = locationList.get(locationList.size() - 1);
//                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
//                lastLocation = location;
//                if (currentLocation != null) {
//                    currentLocation.remove();
//                }
//
//                //Place current location marker
//                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title("Current Position");
//                //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//                //Bitmap bm = "res.drawable.Super_Mario_Pixel.bmp";
//                //markerOptions.icon(BitmapDescriptorFactory.fromResource(res.drawable.Super_Mario_Pixel));
//                markerOptions.icon(BitmapDescriptorFactory.fromFile("super_mario_pixel.bmp"));
//                currentLocation = mMap.addMarker(markerOptions);
//
//                //move map camera
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                //getUserLocations();
                lastLocation = locationList.get(locationList.size() - 1);
            }


            if(sem > 0){
                sem--;
//                Toast.makeText(thisActivity, mess, Toast.LENGTH_SHORT).show();
//                if(mess.equals("add_players")){
//                    //getUserLocations(obj);
//                }
                if(userObj != null) {
                    load_markers();
                    userObj = null;
                }
            }



        }
    };

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

//    public boolean  rNearbyPlayers(JSONObject json) {
//        obj = json;
//        mess = "add_players";
//        sem++;
//        return true;
//    }

    private void toGameInfoScreen(){
        Intent intent = new Intent(this, GameInfoActivity.class);
        startActivity(intent);
    }

    protected void toInventory() {
        Intent intent = new Intent(MapsActivity.this, LeaderBoardActivity.class);
        try {
            intent.putExtra("user_authenticator", user_token.getString("authenticator"));
            intent.putExtra("user_expiration", user_token.getString("expiration"));
            intent.putExtra("session_authenticator", session_token.getString("authenticator"));
            intent.putExtra("session_expiration", session_token.getString("expiration"));
            //        intent.putExtra("joincode", code);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private LatLng get_user_location(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            //return;
        } else {
            permissionGranted = true;
        }
        lastLocation = fusedLocationClient.getLastLocation().getResult();
//
        lastLocation.getAccuracy();
        LatLng lastLatLng;
        lastLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        return lastLatLng;
    }

    @Override
    public  void rcvStatus(JSONObject msg){
        try {
            mess = msg.getString("Message");
            sem++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendLocation(JSONObject msg) {
        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//                //return;
//            } else {
//                permissionGranted = true;
//            }





            // fusedLocationClient.requestLocationUpdates(request locationCallback looper)

           // lastLocation = fusedLocationClient.getLastLocation().getResult();
//
            lastLocation.getAccuracy();
            LatLng lastLatLng;
            lastLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            JSONObject location = new JSONObject();
            try {
                location.put("latitude", lastLocation.getLatitude());
                location.put("longitude", lastLocation.getLongitude());
                location.put("accuracy", lastLocation.getAccuracy());

            } catch (JSONException e) {
                Log.d("JSON Error", e.getMessage());
            }

            wsService.sendToServer(201, location);
        }
        catch(Exception e){
            JSONObject location = new JSONObject();
            try {


                location.put("latitude", 42.024099);
                location.put("longitude", -93.650064);
                location.put("accuracy", 1);
                //   myWebSocket.send("101" + location.toString());

            } catch (Exception f) {
                Log.d("JSON Error", f.getMessage());
            }

            wsService.sendToServer(201, location);
        }
    }

    @Override
    public void rcvNearbyPlayers(JSONObject msg) {
        userObj = msg;
        if(sem < 1) sem++;
    }

    private void load_markers(){
        try {
            JSONArray userlist = userObj.getJSONArray("users");
            for (int i = 0; i < userlist.length(); i++) {
                JSONObject entry = userlist.getJSONObject(i);
                JSONObject locJSON = entry.getJSONObject("Location");
                String username = entry.getString("username");

                LatLng loc = new LatLng(
                        locJSON.getDouble("latitude"),
                        locJSON.getDouble("longitude"));
                if (!users.containsKey(username)) {
                    users.put(username,
                            new User(
                                    username,
                                    loc));
                }
                User user = users.get(username);
                Marker marker = user.getMarker();
                if(marker == null) {
                    user.setMarker(mMap.addMarker(user.setLoc(loc)));
                }
                else{
                    marker.setPosition(loc);
                }



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rcvSpotted(JSONObject msg) {

    }

    @Override
    public void invUpdated(JSONObject msg) {

    }

    @Override
    public void invNotUpdated(JSONObject msg) {

    }

    @Override
    public void rcvInventory(JSONObject msg) {

    }

    @Override
    public void leaderBoardUpdate(JSONObject msg) {

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        wsService = ((WebSocketsService.MyBinder)service).getService();
        wsService.setToken(user_token);
        wsService.setCallBack(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        User user = users.get(marker.getTitle());
        JSONObject clickedUser = new JSONObject();
        //clickedUser.put("username", user.getUsername());
        kill_player(marker.getPosition());
        return false;
    }

    private void kill_player(LatLng pos){
        String url = getResources().getString(R.string.server_URL) + "session/update/onTap";

        JSONObject json = new JSONObject();

        try {
        json.put("session", session_token);
        json.put("tapper", user_token);

        JSONObject loc = new JSONObject();
        loc.put("latitude", pos.latitude);
        loc.put("longitude", pos.longitude);
        loc.put("accuracy", 0);
            json.put("tapped", loc);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        //  mTextViewResult.setText("Response: " +response + "\n");
                        Toast toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG);
                        toast.show();

                        try {
                            if(response.getInt("exception") == 0){
                                if(response.getInt("object") == 1){
                                    Toast.makeText(getApplicationContext(),"YOU WON!", Toast.LENGTH_LONG).show();
                                    end_game();
                                }
                                else{
                                    for(User u : users.values()){
                                        if( u.is_target){
                                            users.remove(u.mUsername);
                                        }
                                    }
                                    get_target();
                                    Toast.makeText(getApplicationContext(),"Getting new target!", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.toString());

                        //  Toast toast = Toast.makeText(thisActivity, "Error, failed to connect to cs309-ad-8.misc.iastate.edu:8080/user/add", Toast.LENGTH_SHORT);
                        // toast.show();
                    }
                }


        );

        mQueue.add(getRequest);
    }

    private void get_target(){
        String url = getResources().getString(R.string.server_URL) + "user/get/targets";

        JSONObject json = new JSONObject();

        try {
            json.put("tapper", user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        //  mTextViewResult.setText("Response: " +response + "\n");
                        Toast toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG);
                        toast.show();

                        try {
                            JSONArray array = response.getJSONArray("list");
                            if(array.length() > 0){
                                String target = array.getJSONObject(0).getString("username");
                                if (!users.containsKey(target)) {
                                    users.get(target).is_target = true;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.toString());

                        //  Toast toast = Toast.makeText(thisActivity, "Error, failed to connect to cs309-ad-8.misc.iastate.edu:8080/user/add", Toast.LENGTH_SHORT);
                        // toast.show();
                    }
                }


        );

        mQueue.add(getRequest);
    }

    private void end_game(){
        String url = getResources().getString(R.string.server_URL) + "session/delete";

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                session_token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Intent intent = new Intent(MapsActivity.this, JoinMenu.class);
                        try {
                            intent.putExtra("user_authenticator", user_token.getString("authenticator"));
                            intent.putExtra("user_expiration", user_token.getString("expiration"));

                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.toString());

                        //  Toast toast = Toast.makeText(thisActivity, "Error, failed to connect to cs309-ad-8.misc.iastate.edu:8080/user/add", Toast.LENGTH_SHORT);
                        // toast.show();
                    }
                }


        );

        mQueue.add(getRequest);
    }
}