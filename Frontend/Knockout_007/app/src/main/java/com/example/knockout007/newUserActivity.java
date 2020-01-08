package com.example.knockout007;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class newUserActivity extends AppCompatActivity {
    newUserActivity thisActivity = this;
    private RequestQueue mQueue;

    Button transmitApplication;
    EditText username;
    EditText password;
    EditText email;
    EditText name;

    private JSONObject user_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        mQueue = Volley.newRequestQueue(this);

        transmitApplication = findViewById(R.id.btnTransmitApplication);
        username = findViewById(R.id.tmlCodename);
        password = findViewById(R.id.tmlPassword);
        email = findViewById(R.id.tmlEmail);
        name = findViewById(R.id.tmlName);
        user_token = new JSONObject();


        transmitApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_user();
            }
        });

    }


    private void new_user(){
        String url = getResources().getString(R.string.server_URL) + "user/add";

        JSONObject user = new JSONObject();

        try {
            user.put("username", username.getText().toString());
            user.put("password", password.getText().toString());
            JsonObjectRequest postRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    user,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            Toast toast = Toast.makeText(thisActivity, response.toString(), Toast.LENGTH_LONG);
                            toast.show();
                            login();
                            //login(username.getText().toString(), password.getText().toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error", error.toString());

                            Toast toast = Toast.makeText(thisActivity, error.toString(), Toast.LENGTH_LONG);
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

    private void login() {


        String url = getResources().getString(R.string.server_URL) + "user/login";

        JSONObject user = new JSONObject();
        JSONObject token = new JSONObject();

        try {
            user.put("username", username.getText().toString());
            user.put("password", password.getText().toString());

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
                                        //auth = response.getInt("object2");
                                        logged_in();
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

    private void logged_in() {
        try {
            Intent intent = new Intent(newUserActivity.this, JoinMenu.class);
            Bundle b = new Bundle();
            b.putString("user_authenticator", user_token.getString("authenticator"));
            b.putString("user_expiration", user_token.getString("expiration"));
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            Log.d("JSON Error", e.getMessage());
        }

    }
}
