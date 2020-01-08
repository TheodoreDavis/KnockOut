package com.example.knockout007;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class WebSocketsService extends Service implements ClientWebSocketListener.callback {

    private static final String TAG = WebSocketsService.class.getSimpleName();
    public static final String ACTION_NETWORK_STATE_CHANGED = "networkStateChanged";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    private LocationRequest mLocationRequest;
    private android.location.Location mLastKnownLocation;
    private boolean permissionGranted = false;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    //User Variables

    //WebSocket Variables
    private WebSocket myWebSocket;
    private String serverUrl = "http://cs309-ad-8.misc.iastate.edu:8080/websocket/";
    private JSONObject mUser_token = null;
    private JSONObject session_token = null;
    private OkHttpClient client = new OkHttpClient();


    // Unique Notification ID
    private static final int NOTIFICATION_ID = 1001;
    // Custom Binder
    private MyBinder mLocalBinder = new MyBinder();
    // Custom interface Callback which is declared in this Service
    private CallBack mCallBack;


    public WebSocketsService() {
        super();
        Log.i("SERVICE", "Running");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
// Getting the instance of Binder
        return mLocalBinder;
    }
    public void setToken(JSONObject user_token){
        if(mUser_token == null || myWebSocket == null) {
            mUser_token = user_token;
            myWebSocket = startWS();
        }
    }
    // Callback Setter
    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
    // Method to perform Long running task for User
    public void sendToServer(Integer intent, JSONObject msg) {
        //new CustomAsyncTask(intent, msg).execute();
        if(msg == null) myWebSocket.send(String.valueOf(intent));
        else myWebSocket.send(String.valueOf(intent) + msg.toString());
    }
//    // Method to create notification
//    private Notification getNotificationBuilder() {
//        Notification notification;
//        //TODO Build your own custom Notification
//        return notification;
//    }

    @Override
    public boolean JSONHandler(JSONObject fullJson) {
        //Parse the fullJson into intent, reason, and object
        int methodId = -1;
        String reason = "Error, Reason not recorder";
        JSONObject json = null;


        try {
            methodId = fullJson.getInt("intent");
            reason = fullJson.getString("reason");
            json = fullJson.getJSONObject("object");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch(methodId) {
            case -1:
                //Error, no methodID
                break;

            case 100:
                //Server sends client a message
                //                Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_LONG).show();
                //Toast toast = Toast.makeText(thisActivity, reason, Toast.LENGTH_LONG);
                //toast.show();

                mCallBack.rcvStatus(json);
                break;

            case 101:
                //Client sends location data
                mCallBack.sendLocation(json);
                break;


            case 102:
                //Client asked for the location of nearby players, server responded
                mCallBack.rcvNearbyPlayers(json);
                break;


            case 103:
                //A player spotted you
                mCallBack.rcvSpotted(json);
                break;


            case 104 :
                //Inventory updated successfully
                Toast.makeText(getApplicationContext(), "Inventory updated successfully", Toast.LENGTH_LONG).show();
                mCallBack.invUpdated(json);
                break;


            case 105:
                Toast.makeText(getApplicationContext(), "Inventory did not update successfully", Toast.LENGTH_LONG).show();
                mCallBack.invNotUpdated(json);
                //Inventory did not update successfully
                break;

            case 106:
                //Client asked for their inventory
                mCallBack.rcvInventory(json);
                break;

            case 107:
                mCallBack.leaderBoardUpdate(json);
                break;



        }

        return false;}

    // callback Interface
    public interface CallBack {
        void rcvStatus(JSONObject msg);
        void sendLocation(JSONObject msg);
        void rcvNearbyPlayers(JSONObject msg);
        void rcvSpotted(JSONObject msg);
        void invUpdated(JSONObject msg);
        void invNotUpdated(JSONObject msg);
        void rcvInventory(JSONObject msg);
        void leaderBoardUpdate(JSONObject msg);
    }
    //Custom Binder class
    public class MyBinder extends Binder {
        public WebSocketsService getService() {
            return WebSocketsService.this;
        }
    }
//    // AsyncTask class to perform any task
//    private class CustomAsyncTask extends AsyncTask<Integer, Integer, JSONObject> {
//        public CustomAsyncTask(Integer intent, JSONObject msg) {
//            mIntent = intent;
//            mJSONObject = msg;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Notification notification = getNotificationBuilder();
//            startForeground(NOTIFICATION_ID, notification);
//        }
//        @Override
//        protected User doInBackground(Integer... objects) {
//            if(myWebSocket != null){
//
//                myWebSocket.send(String.valueOf(mIntent))
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(JSONObject msg) {
//            super.onPostExecute(msg);
//            if (mCallBack != null) {
//                mCallBack.onOperationCompleted(msg);
//            }
//        }
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            if (mCallBack != null && values.length > 0) {
//                for (Integer integer : values)
//                    mCallBack.onOperationProgress(integer);
//            }
//        }
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//    }

    private WebSocket startWS() {

        //login
        WebSocket ws = null;
        //Create the builder and set up appropriate fields
        okhttp3.Request.Builder myBuilder = new okhttp3.Request.Builder();
        try {
            myBuilder.url(serverUrl + mUser_token.getString("authenticator") + "," + mUser_token.getString("expiration") );//myBuilder.addHeader("Username","Caden");





            //Create the request
            okhttp3.Request request = myBuilder.build();
            //Create the listener
            ClientWebSocketListener listener = new ClientWebSocketListener(this);
            //Use the listener and request to start a websocket
            ws = client.newWebSocket(request, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return ws;
    }
}
/*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //startTimer();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        Log.i(TAG, "onBind");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(WebSocketsService.ACTION_NETWORK_STATE_CHANGED));
        startSocket();
        return mBinder;
    }

    private void startSocket() {
        //mWebSocketClient = new WebSocketClient(URI.create(WS_URL), this, null);
        //mWebSocketClient.connect();

        myWebSocket = null;
        //Create the builder and set up appropriate fields
        okhttp3.Request.Builder myBuilder = new okhttp3.Request.Builder();
        try {
            myBuilder.url(serverUrl + user_token.getString("authenticator") + "," + user_token.getString("expiration") );//myBuilder.addHeader("Username","Caden");
            //Create the request
            okhttp3.Request request = myBuilder.build();
            //Create the listener
            ClientWebSocketListener listener = new ClientWebSocketListener(this);
            //Use the listener and request to start a websocket
            myWebSocket = client.newWebSocket(request, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void stopSocket() {
        if (myWebSocket != null) {
            //myWebSocket.disconnect();
            myWebSocket = null;
        }
}
    }*/
