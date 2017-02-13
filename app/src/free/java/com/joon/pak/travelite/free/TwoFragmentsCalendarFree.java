package com.joon.pak.travelite.free;

/**
 * Created by joonheepak on 10/2/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joon.pak.travelite.R;
import com.joon.pak.travelite.calendar.RightSideFragment;
import com.joon.pak.travelite.ui.MainActivity;

/**
 * Created by joonheepak on 10/2/16.
 */

public class TwoFragmentsCalendarFree extends AppCompatActivity {
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
            Intent deleteIntent = new Intent(TwoFragmentsCalendarFree.this, com.joon.pak.travelite.free.MainActivity.class);
            deleteIntent.putExtra("id", positionId);
            startActivity(deleteIntent);
        }

        return super.onOptionsItemSelected(item);
    }

}
