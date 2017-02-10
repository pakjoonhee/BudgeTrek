package com.example.joonheepak.finalproject.calendar;

/**
 * Created by joonheepak on 10/2/16.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.joonheepak.finalproject.R;
import com.example.joonheepak.finalproject.free.MainActivity;

/**
 * Created by joonheepak on 10/2/16.
 */

public class TwoFragmentsCalendar extends AppCompatActivity {
    String positionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.both_fragments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        positionId = intent.getStringExtra("id");
                if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, new RightSideFragment())
                    .commit();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            Intent deleteIntent = new Intent(TwoFragmentsCalendar.this, MainActivity.class);
            deleteIntent.putExtra("id", positionId);
            startActivity(deleteIntent);
        }

        return super.onOptionsItemSelected(item);
    }

}
