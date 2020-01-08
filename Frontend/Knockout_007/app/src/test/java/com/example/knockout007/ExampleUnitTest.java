package com.example.knockout007;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import okhttp3.WebSocket;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testsLocation(){
        ClientWebSocketListener testListener = Mockito.mock(ClientWebSocketListener.class);

        WebSocket webSocket = Mockito.mock(WebSocket.class);

        MapsActivity testMaps = Mockito.mock(MapsActivity.class);
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        try {
            Mockito.when(mockJson.getInt("intent")).thenReturn(101);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Mockito.when(testMaps.sLocation(mockJson)).thenReturn(true);
        //Check if theJason handler cna assign  the correct method
       Boolean check = testMaps.sLocation(mockJson);

       assertTrue(check);


    }

    @Test
    public void testrNearbyPlayers(){
        ClientWebSocketListener testListener = Mockito.mock(ClientWebSocketListener.class);

        WebSocket webSocket = Mockito.mock(WebSocket.class);

        MapsActivity testMaps = Mockito.mock(MapsActivity.class);
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        try {
            Mockito.when(mockJson.getInt("intent")).thenReturn(102);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Mockito.when(testMaps.rNearbyPlayers(mockJson)).thenReturn(true);
        //Check if theJason handler cna assign  the correct method
        Boolean check = testMaps.rNearbyPlayers(mockJson);

        assertTrue(check);

    }

    @Test

    public void testrSpotted(){
        ClientWebSocketListener testListener = Mockito.mock(ClientWebSocketListener.class);

        WebSocket webSocket = Mockito.mock(WebSocket.class);

        MapsActivity testMaps = Mockito.mock(MapsActivity.class);
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        try {
            Mockito.when(mockJson.getInt("intent")).thenReturn(103);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Mockito.when(testMaps.rSpotted(mockJson)).thenReturn(true);
        //Check if theJason handler cna assign  the correct method
        Boolean check = testMaps.rSpotted(mockJson);

        assertTrue(check);

    }


    @Test
    public void testrSpotted(){
        ClientWebSocketListener testListener = Mockito.mock(ClientWebSocketListener.class);

        WebSocket webSocket = Mockito.mock(WebSocket.class);

        MapsActivity testMaps = Mockito.mock(MapsActivity.class);
        JSONObject mockJson = Mockito.mock(JSONObject.class);
        try {
            Mockito.when(mockJson.getInt("intent")).thenReturn(103);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Mockito.when(testMaps.rSpotted(mockJson)).thenReturn(true);
        //Check if theJason handler cna assign  the correct method
        Boolean check = testMaps.rSpotted(mockJson);

        assertTrue(check);

    }




}