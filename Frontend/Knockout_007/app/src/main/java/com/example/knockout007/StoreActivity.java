package com.example.knockout007;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Activity to show the shop. From here the user can buy items and/or sell current items for in game currency.
 */
public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
    }
}
