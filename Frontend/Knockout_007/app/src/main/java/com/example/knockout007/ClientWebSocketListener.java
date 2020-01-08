package com.example.knockout007;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


/**
 * User can create an instance of the client websocket listener to "listen" in the background of an application. When the listener receives a message, it will automatically handle it. See onOpen
 *
 */
public class ClientWebSocketListener extends WebSocketListener {

    //private JSONHandlerInterface myActivity;
    private WebSocketsService mCallback;
    private MapsActivity myMap;
    private loginActivity myLogin;

    /**
     * Constructor for websocket listener
     * @param callback
     * The activity that will be listening for the websocket. Must implement JSONHandlerInterface, i.e. have a handler inside of it for onMessage to invoke
     */
    //public ClientWebSocketListener(JSONHandlerInterface myActivity){
    public ClientWebSocketListener(WebSocketsService callback){
        mCallback = callback;
   }
    //public ClientWebSocketListener(MapsActivity myMap) {this.myActivity = myMap;}


    private static final int NORMAL_CLOSURE_STATUS = 1000;
    @Override

    //Invoked one successful connection


    public void onOpen(WebSocket webSocket, Response response) {
        //webSocket.send("100");
        int x;
        x=0;
        x=x+1;
    }

    /**
     * Invoked when the client receives any message from the server. The message is parsed and then sent into the JSONHandler of the activity tht created this instance of websocket listener
     * @param webSocket The websocket to be used
     * @param text The full message to be parsed and sent to the JSONHandler of the activity
     */
    @Override
    public void onMessage(WebSocket webSocket, String text) {
       // MainActivity.mTextViewResult.setText(text);
        //Parse code and reason
        Log.i("onMessage", text);

        JSONObject json = new JSONObject();
        try {
          json  = new JSONObject(text);
        } catch (JSONException e) {
            //Error, you didn't send me a JSON
            e.printStackTrace();
        }

        mCallback.JSONHandler(json);

    }


    /**
     * Invoked when the client receives any message from the server. The message is parsed and then sent into the JSONHandler of the activity tht created this instance of websocket listener
     * @param webSocket
     * The websocket to be used
     * @param code
     * The code of the message from the client. Used to determine the purpose of the message
     * @param reason
     * The specific reason, if any, why this message was sent. Used for debugging purposes.
     */
    public void onMessage(WebSocket webSocket, int code, String reason){

    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
      //  output("Closing : " + code + " / " + reason);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        String str = new String(response.toString());
        //MainActivity.mTextViewResult.setText("Connection failed");
    }

    public interface callback{
        boolean JSONHandler(JSONObject fullJson);
    }


    }



