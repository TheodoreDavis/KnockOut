package com.example.knockout007;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameLobby extends AppCompatActivity implements ServiceConnection, WebSocketsService.CallBack {
    JSONObject user_token;
    JSONObject session_token;
    private WebSocketsService wsService;
    private RequestQueue mQueue;
    int code;
    TextView join_code;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        mQueue = Volley.newRequestQueue(this);
        join_code = findViewById(R.id.textLobbyCode);
        TextView lobbyList = findViewById(R.id.lobbyList);
        Button start = findViewById(R.id.buttonStartGame);
        Button leave = findViewById(R.id.buttonLeaveGame);
        Button refresh = findViewById(R.id.buttonRefreshLobby);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            try {
                user_token = new JSONObject();
                session_token = new JSONObject();
                user_token.put("authenticator", (String)extras.get("user_authenticator"));
                user_token.put("expiration", (String)extras.get("user_expiration"));
                session_token.put("authenticator", (String)extras.get("session_authenticator"));
                session_token.put("expiration", (String)extras.get("session_expiration"));
                //join_code.setText(String.valueOf((int)extras.get("passcode")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveGame();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wsService.sendToServer(200, null);
            }
        });
    }

    protected void startGame(){
        String url = getResources().getString(R.string.server_URL) + "session/start";



        JSONObject request = new JSONObject();
        //JSONObject addArray[] = new JSONObject[1];

        try {
            request.put("session", session_token);
            //addArray[0] = user_token;
            request.put("user", user_token);

            JsonObjectRequest postRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    request,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            try {
                                if(response.getInt("exception") == 0) {
                                    int started = response.getInt("object");
                                    //wsService.sendToServer(200, null);
                                    goToMap();
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

                            Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                            toast.show();
                            //login(username.getText().toString(), password.getText().toString());
                        }
                    }
            );

            postRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });

            mQueue.add(postRequest);
        }
        catch(JSONException e){
            Log.d("Put Error", e.getMessage());
        }
    }

    protected void leaveGame(){

    }

    protected void goToMap(){
        Intent intent = new Intent(GameLobby.this, MapsActivity.class);
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

    private void get_session_token(){

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        wsService = ((WebSocketsService.MyBinder)service).getService();
        wsService.setCallBack(this);
        wsService.setToken(user_token);
        wsService.sendToServer(200, null);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void rcvStatus(JSONObject msg) {
        String txt = new String();
        try {
            join_code.setText(msg.getInt("passcode"));
            JSONArray marray = msg.getJSONArray("users");
            for (int i = 0; i < marray.length(); i++) {
                JSONObject user = marray.getJSONObject(i);
                txt = txt.concat(user.getString("username") + "\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView lobbyList = findViewById(R.id.lobbyList);
        lobbyList.setText(txt);
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
