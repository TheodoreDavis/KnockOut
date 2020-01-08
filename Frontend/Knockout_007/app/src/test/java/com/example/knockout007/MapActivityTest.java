package com.example.knockout007;

import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class MapActivityTest {

    private MapsActivity mapsActivity;
    private Location mockLoc;

    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
@Test
    public void test_user_location(){
        when(mockLoc.getLatitude()).thenReturn(25.5);
        when(mockLoc.getLongitude()).thenReturn(30.0);

        assertThat(mapsActivity.get_user_location().latitude, is(25.5));
    }
}
