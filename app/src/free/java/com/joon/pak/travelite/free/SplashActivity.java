package com.joon.pak.travelite.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by joonheepak on 9/9/16.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, com.joon.pak.travelite.free.MainActivity.class);
        startActivity(intent);
        finish();
    }
}
