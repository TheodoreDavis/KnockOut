package com.example.knockout007;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
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
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JoinMenu extends AppCompatActivity implements ServiceConnection, WebSocketsService.CallBack {

    private WebSocketsService wsService;
    protected EditText sessionName;
    protected EditText passcode;
    protected SeekBar playSpaceRadius;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mLoc = null;
    private RequestQueue mQueue;
    private JSONObject user_token;
    private JSONObject session_token;
    private LocationRequest mLocationRequest;
    private Boolean permissionGranted = false;
    private Button createNew;
    //    private String authenticator;
//    private String expiration;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_menu);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            try {
                user_token = new JSONObject();
                session_token = new JSONObject();
                String auth = (String) extras.get("user_authenticator");
                String exp = (String) extras.get("user_expiration");
                user_token.put("authenticator", auth);
                user_token.put("expiration", exp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        mQueue = Volley.newRequestQueue(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        sessionName = findViewById(R.id.editSessionName);
        passcode = findViewById(R.id.editPasscode);
        //passcode.setText("123456");
        playSpaceRadius = findViewById(R.id.seekRadius);

        Button join = findViewById((R.id.buttonJoinSession));
        createNew = findViewById(R.id.buttonNewSession);
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSession();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinSession(Integer.parseInt(passcode.getText().toString()));
            }
        });
        createNew.setEnabled(false);


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

        if (permissionGranted == true) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(15000);
            mLocationRequest.setFastestInterval(15000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            //fusedLocationClient.removeLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }

    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                //The last location in the list is the newest

                createNew.setEnabled(true);
                mLoc = locationList.get(locationList.size() - 1);
            }
        }
    };

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


    protected void newSession() {

        String url = getResources().getString(R.string.server_URL) + "session/add";
        //String url = "http://cs309-ad-8.misc.iastate.edu:8080/session/add";

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            //return;
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_LOCATION);
//        }
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() {
//            @Override
//            public void onSuccess(android.location.Location location) {
//                if(location != null){
//                    mLoc = location;
//                }
//                else{
//                    mLoc = null;
//                }
//            }
//        });
        //JSONObject session = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject jLoc = new JSONObject();

        try {
            request.put("name", sessionName.getText().toString());
            request.put("radius", playSpaceRadius.getProgress());

//            jLoc.put("latitude", 42.026995); //mLoc.getLatitude());
//            jLoc.put("longitude", -93.650789); //mLoc.getLongitude());
            while(mLoc == null);
            jLoc.put("latitude", mLoc.getLatitude());
            jLoc.put("longitude", mLoc.getLongitude());

            request.put("center", jLoc);
            //request.put("session",session);
            request.put("token",user_token);

            JsonObjectRequest postRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    request,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("Response", Integer.toString(response.getInt("object")));
                            //  mTextViewResult.setText("Response: " +response + "\n");
                                Toast toast = Toast.makeText(getApplicationContext(), "Session created with join code :" + response.getInt("object"), Toast.LENGTH_LONG);
                                toast.show();
                                //String str = passcode.getText().toString();
                                //int test = Integer.parseInt(str);
                                //EditText code = findViewById(R.id.editPasscode);
                                //code.setText(response.getInt("object"));
                                joinSession(response.getInt("object"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error", error.toString());

                            Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                            toast.show();
                            //login(username.getText().toString(), password.getText().toString());
                        }
                    }
            );

            mQueue.add(postRequest);
        }
        catch(JSONException e){
            Log.d("Put Error", e.getMessage());
        }
    }

    protected void joinSession(int code) {
        String url = getResources().getString(R.string.server_URL) + "session/add/user";



        JSONObject request = new JSONObject();
        //JSONObject addArray[] = new JSONObject[1];

        try {
            request.put("passcode", code);
            //addArray[0] = user_token;
            request.put("toAdd", user_token);

            JsonObjectRequest postRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    request,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            try {
                                if(response.getInt("exception") == 0)
                                    session_token = response.getJSONObject("object");
                                    wsService.sendToServer(200, null);
                                    goToLobby();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error", error.toString());

                            Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                            toast.show();
                            //login(username.getText().toString(), password.getText().toString());
                        }
                    }
            );

            mQueue.add(postRequest);
        }
        catch(JSONException e){
            Log.d("Put Error", e.getMessage());
        }
    }

    protected void goToLobby() {
        Intent intent = new Intent(JoinMenu.this, GameLobby.class);
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

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        wsService = ((WebSocketsService.MyBinder)service).getService();
        wsService.setCallBack(this);
        wsService.setToken(user_token);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void rcvStatus(JSONObject msg) {

    }

    @Override
    public void sendLocation(JSONObject msg) {

    }

    @Override
    public void rcvNearbyPlayers(JSONObject msg) {

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
}
