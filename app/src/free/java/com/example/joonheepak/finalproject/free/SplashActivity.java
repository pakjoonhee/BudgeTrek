package com.example.joonheepak.finalproject.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.joonheepak.finalproject.ui.*;

/**
 * Created by joonheepak on 9/9/16.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, com.example.joonheepak.finalproject.free.MainActivity.class);
        startActivity(intent);
        finish();
    }
}
