package com.example.joonheepak.finalproject.calendar;

/**
 * Created by joonheepak on 10/2/16.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.joonheepak.finalproject.R;

/**
 * Created by joonheepak on 10/2/16.
 */

public class TwoFragmentsCalendar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.both_fragments);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, new RightSideFragment())
                    .commit();
        }

    }

}
