package com.example.knockout007;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;


/**
 * The main activity is to be used only for demo and testing purposes. The actual game will begin with the login activity, and then be redirected to the maps activity. From the main activity
 * Developers and testers can access any other activity, as well as see textual information of the current session displayed on screen
 */
public class MainActivity extends AppCompatActivity implements JSONHandlerInterface {
    MainActivity thisActivity = this;
    private WebSocketsService wsService;
    public static final String EXTRA_MAP = "com.example.knockout007.MAP";
    public static final String EXTRA_VOLLeY = "volley";
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private OkHttpClient client = new OkHttpClient();
    private WebSocket myWebSocket;
    private  String serverUrl = "http://cs309-ad-8.misc.iastate.edu:8080/websocket/";
    private JSONObject loginTolken;


    /**
     * Creates a main activity from the last saved instance
     * @param savedInstanceState
     * The last saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.btnLogin);
        mTextViewResult = findViewById(R.id.txtParse);
     //   Button buttonParse = findViewById(R.id.btnParse);
     //   Button buttonPut = findViewById(R.id.btnPut);
        Button buttonWebSockets = findViewById(R.id.btnWebSockets);
        Button buttonMap = findViewById(R.id.btnMap);
        Button buttonSlash = findViewById(R.id.btnSlash);
       // Button buttonUsers = findViewById(R.id.btnUsers);
      //  Button buttonItems = findViewById(R.id.btnItems);
        Button buttonNewUser = findViewById(R.id.btnNewUser);

        mQueue = Volley.newRequestQueue(this);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Button click to the login screen
             */
            public void onClick(View v) {
                toLoginScreen();

            }
        });
        buttonWebSockets.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Button click to manually start a websocket connection
             */
            public void onClick(View v) {




            }
        });
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Button click to the mapActivity
             */
            public void onClick(View v) { toMapScreen();}
        });

        buttonSlash.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Access the homepage of the server
             */
            public void onClick(View v) {slash();}
        });

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Button click to the newUser activity
             */
            public void onClick(View v) {toNewUserScreen();}
        });
    }

    /**
     * Called by the websocket listener. Takes the JSON object and pulls the code identifier then sends the JSON to the proper helper method
     * @param fullJson
     * The json object created by the onMessage method in clientWebsocketListener. Expected to have intent, reason, and another json object tht will be sent to the appropriate method as determined
     * by this handler.
     * @return
     * Used for testing purposes, returns true on a successfully handled json object
     */
    public boolean JSONHandler(JSONObject fullJson){

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




        try {
            methodId = json.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch(methodId) {
            case -1:
                //Error, no methodID
                break;


            case 101:
                //Client sends location data
                sLocation(json);
                break;


            case 102:
                //Client asked for the location of nearby players, server responded
                rNearbyPlayers(json);
                break;


            case 103:
                //A player spotted you
                rSpotted(json);
                break;


            case 104 :
                //Inventory updated successfully
                mTextViewResult.setText("Inventory updated successfully");
                break;


            case 105:
                mTextViewResult.setText("Inventory did not update successfully");
                //Inventory did not update successfully
                break;

            case 106:
                //Client asked for their inventory
                updateInventory(json);
                break;



        }
        mTextViewResult.setText("methodId: " + methodId);
        mTextViewResult.setText('\n'+"Reason: " + reason);
        mTextViewResult.setText('\n' + "Object: " + json);
        return true;

    }

    /**
     * Updates the inventory
     * @param json
     * The json object sent by the jsonHandler
     */
    public void updateInventory(JSONObject json) {
        
    }


    /**
     * Notifies the payer that they were spotted
     * @param json
     * The json object sent by the jsonHandler
     * @return
     * True if the json was handled successfully
     */
    public boolean rSpotted(JSONObject json) {
return true;
    }


    /**
     * The response of the server after the client asked for nearby player locations. Should display the locations fo nearby players.
     * @param json
     * The json object sent by the jsonHandler
     * @return
     * True if the json was handled successfully
     */
    public boolean  rNearbyPlayers(JSONObject json) {

        return true;
    }

    /**
     * The server wants the client to send their location data.
     * @param json
     * The json object sent by the jsonHandler
     * @return
     * True if the json was handled successfully
     */
    public boolean sLocation(JSONObject json) {
        return true;
    }


    private void toNewUserScreen(){
        Intent intent = new Intent(this, newUserActivity.class);
        startActivity(intent);
    }

    private void toMapScreen(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void toLoginScreen(){
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    private void jsonPut(){
        String url = "http://cs309-ad-8.misc.iastate.edu:8080/user/add";

        JSONObject user = new JSONObject();

        try {
            user.put("username", "Sean");
            user.put("password", "DoubleDip");
            JsonObjectRequest putRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    user,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            mTextViewResult.setText("Response: " +response + "\n");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error", error.toString());
                            mTextViewResult.setText("Error: " + error + "\n");
                        }
                    }
            );

            mQueue.add(putRequest);
        }
        catch(JSONException e){
            Log.d("Put Error", e.getMessage());
        }

    }

    private void slash(){
        String url =  "http://cs309-ad-8.misc.iastate.edu:8080/";
        StringRequest putRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        mTextViewResult.setText("Response: " +response + "\n");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        mTextViewResult.setText("Error: " + error + "\n");
                    }
                });

        mQueue.add(putRequest);

    }

    private void items(){
        String url =  "http://cs309-ad-8.misc.iastate.edu:8080/items/";
        StringRequest putRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        mTextViewResult.setText("Response: " +response + "\n");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        mTextViewResult.setText("Error: " + error + "\n");
                    }
                });

        mQueue.add(putRequest);

    }

    private void users(){
        String url =  "http://cs309-ad-8.misc.iastate.edu:8080/user/";
        StringRequest putRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        mTextViewResult.setText("Response: " +response + "\n");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        mTextViewResult.setText("Error: " + error + "\n");
                    }
                });

        mQueue.add(putRequest);
    }

    private void jsonParse(){
        //myjson used to simulate a server sending json to the client
        String url = "https://api.myjson.com/bins/ktyi6";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                //This will be called if our request was successful
                try {


                    JSONArray jsonArray = response.getJSONArray("Team13");

                    mTextViewResult.setText("");
                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject members = jsonArray.getJSONObject(i);

                        String firstName = members.getString( "name");
                        String password = members.getString("password");
                        String country = members.getString("country");


                        mTextViewResult.append(firstName +"'s password is "+password + ". He lives in " + country + ". \n\n");


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //This will be called if our request was unsuccessful
                mTextViewResult.append("Error Error Error Error");
            }
        });

        mQueue.add(request);
    }





    private JSONObject login(String username, String password) {


        String url = "http://cs309-ad-8.misc.iastate.edu:8080/user/login";

        JSONObject user = new JSONObject();
         JSONObject tolken = new JSONObject();

        try {
            user.put("username", username);
            user.put("password", password);

            JsonObjectRequest getRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    user,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            //  mTextViewResult.setText("Response: " +response + "\n");
                            Toast toast = Toast.makeText(thisActivity, response.toString(), Toast.LENGTH_LONG);
                            toast.show();
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

        } catch (JSONException e) {
            Log.d("Put Error", e.getMessage());
        }

        return tolken;

    }

    /**
     *Used to update the map when the server sends new player locations
     *
     */
    private void updateMap(){

    }

    /**
     * Used to send inventory information to the server. Inventory managment is handeled inside of the client app
     */
    private void sendInventory(){


    }


}
