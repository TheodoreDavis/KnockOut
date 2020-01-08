package com.example.knockout007;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.junit.Test;

import java.lang.reflect.Array;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class loginActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private JSONObject user_token;
    private JSONObject session_token;
    private int auth;
    private int session_started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button submitBtn = findViewById(R.id.btnLoginSubmit);
        final EditText username = findViewById(R.id.editUsername);
        final EditText password = findViewById(R.id.editPassword);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginTolken = login("McWolf","password");
                login(username.getText().toString(), password.getText().toString());
            }
        });

        mQueue = Volley.newRequestQueue(this);
    }

    private void login(String username, String password) {


        String url = getResources().getString(R.string.server_URL) + "user/login";

        JSONObject user = new JSONObject();
        JSONObject token = new JSONObject();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(
                    getApplicationContext(),
                    "No Username or Password entered",
                    Toast.LENGTH_LONG).show();
            //return token;
        }

        try {
            user.put("username", username);
            user.put("password", password);

            JsonObjectRequest getRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    user,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            //  mTextViewResult.setText("Response: " +response + "\n");

                            try {
                                if (response.getInt("exception") == 0) {
    //                            try {
                                    //JSONObject token = new JSONObject();//new String(response.getString("authenticator"));
                                    //logged_in(response);
    //                            } catch (JSONException e) {
    //                                Log.d("JSON Error", e.getMessage());
    //                            }
                                    try {
                                        user_token = response.getJSONObject("object1");
                                        auth = response.getInt("object2");
                                        get_session();
                                    } catch (JSONException e) {
                                        Log.d("JSON Error", e.getMessage());
                                    }
                                }
                                else{
                                    Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG);
                                    toast.show();
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

                            Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }


            );

//            getRequest.setRetryPolicy(new RetryPolicy() {
//                @Override
//                public int getCurrentTimeout() {
//                    return 50000;
//                }
//
//                @Override
//                public int getCurrentRetryCount() {
//                    return 50000;
//                }
//
//                @Override
//                public void retry(VolleyError error) throws VolleyError {
//
//                }
//            });

            mQueue.add(getRequest);

        } catch (JSONException e) {
            Log.d("Put Error", e.getMessage());
        }

        //TODO

        //return user;

    }

    private void logged_in(){
        try {
            if(session_token.getString("authenticator") == "null"){
                Intent intent = new Intent(loginActivity.this, JoinMenu.class);
                Bundle b = new Bundle();
                b.putString("user_authenticator", user_token.getString("authenticator"));
                b.putString("user_expiration", user_token.getString("expiration"));
                intent.putExtras(b);
                //intent.putExtra("user_authenticator", user_token.getString("authenticator"));
                //intent.putExtra("user_expiration", user_token.getString("expiration"));
                startActivity(intent);
            } else if (session_started == 1){
                Intent intent = new Intent(loginActivity.this, MapsActivity.class);
                intent.putExtra("user_authenticator", user_token.getString("authenticator"));
                intent.putExtra("user_expiration", user_token.getString("expiration"));
                intent.putExtra("session_authenticator", session_token.getString("authenticator"));
                intent.putExtra("session_expiration", session_token.getString("expiration"));
                startActivity(intent);
            } else{
                Intent intent = new Intent(loginActivity.this, GameLobby.class);
                intent.putExtra("user_authenticator", user_token.getString("authenticator"));
                intent.putExtra("user_expiration", user_token.getString("expiration"));
                intent.putExtra("session_authenticator", session_token.getString("authenticator"));
                intent.putExtra("session_expiration", session_token.getString("expiration"));
                startActivity(intent);
            }
        } catch (JSONException e) {
            Log.d("JSON Error", e.getMessage());
        }

    }

    private void get_session(){

        String url = getResources().getString(R.string.server_URL) + "user/get/session";

//        JSONObject user = new JSONObject();
//        JSONObject token = new JSONObject();
//        token.put("authenticator", )
        user_token.remove("valid");

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                user_token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        //  mTextViewResult.setText("Response: " +response + "\n");
                        Toast toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG);
                        toast.show();

                        try {
                            session_token = response.getJSONObject("object1");
                            session_started = response.getInt("object2");
                            logged_in();
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


//@Test
//public void test(){
//
//}