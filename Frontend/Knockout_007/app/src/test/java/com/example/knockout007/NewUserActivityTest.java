package com.example.knockout007;

import org.junit.*;
import org.junit.runner.RunWith;
//import androidx.test.runner.AndroidJUnit4;

//@RunWith(AndroidJUnit4.class)
public class NewUserActivityTest {

    private String str;

    @Before
    public void initString(){
        str = "test";
    }

    @Test
    public void test_username(){
        //onView(withId(R.id.editUsername)).perform(typeText(str), closeSoftKeyboard());
    }
}
