package com.joon.pak.travelite.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.joon.pak.travelite.R;

/**
 * Created by joonheepak on 9/15/16.
 */
public class AddTripScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip_screen);


        Button addTripButton = (Button) findViewById(R.id.trip_text_button);
        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent autoFillTrip = new Intent(AddTripScreen.this, AddTripDetails.class);
                EditText tripName = (EditText) findViewById(R.id.edit_trip);
                autoFillTrip.putExtra("tripName", tripName.getText().toString());
                AddTripScreen.this.startActivity(autoFillTrip);
            }
        });

    }
}
