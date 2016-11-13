package com.example.joonheepak.finalproject.ui;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.joonheepak.finalproject.R;
import com.example.joonheepak.finalproject.data.DatabaseColumns;
import com.example.joonheepak.finalproject.data.DatabaseProvider;
import com.example.joonheepak.finalproject.widget.CollectionWidget;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView.Adapter recyclerViewAdapter;
    Context context;
    private Tracker mTracker;
    TextView addTripButton;
    TextView minusTripButton;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);


        addTripButton = (TextView) findViewById(R.id.add_trip_button);
        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tripScreenIntent = new Intent(MainActivity.this, AddTripScreen.class);
                MainActivity.this.startActivity(tripScreenIntent);
            }
        });

        try {
            getSupportLoaderManager().initLoader(0, null, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MyApplication application = (MyApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        Log.i("New", "Setting screen name: " + "MainActivity");
//        mTracker.setScreenName("Image~" + "MainActivity");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-86045128-1"); // Replace with actual tracker id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DatabaseProvider.Trips.CONTENT_URI,
                new String[] {DatabaseColumns._ID, DatabaseColumns.Trip_Name, DatabaseColumns.Start_Date,
                        DatabaseColumns.End_Date, DatabaseColumns.Budget, DatabaseColumns.Country_Flag,
                        DatabaseColumns.Background_Image, DatabaseColumns.Currency_Symbol}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            context = getApplicationContext();
            Resources resources = getResources();
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
            recyclerView.addItemDecoration(new MarginDecoration(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerViewAdapter = new MainViewAdapter(context, resources, cursor, minusTripButton);
            recyclerView.setAdapter(recyclerViewAdapter);
        } else if (cursor == null) {
            return;
        }
        else if (cursor.getCount() == 0){
            Resources resources = getResources();
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
            recyclerView.addItemDecoration(new MarginDecoration(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerViewAdapter = new MainViewAdapter(context, resources, cursor, minusTripButton);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
        ComponentName name = new ComponentName(this, CollectionWidget.class);
        int [] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(name);
        Intent intent = new Intent(this,CollectionWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}

