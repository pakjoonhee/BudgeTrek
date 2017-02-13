package com.joon.pak.travelite.free;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.joon.pak.travelite.R;
import com.joon.pak.travelite.calendar.TwoFragmentsCalendar;
import com.joon.pak.travelite.data.DatabaseColumns;
import com.joon.pak.travelite.data.DatabaseProvider;
import com.joon.pak.travelite.ui.*;
import com.joon.pak.travelite.utlity.ImageConvert;
import com.joon.pak.travelite.widget.CollectionWidget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView.Adapter recyclerViewAdapter;
    Context context;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    public FloatingActionButton fabPlus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main_free);

        Intent intent = getIntent();
        if (intent.getStringExtra("id") == null) {

        } else {
            String positionID = intent.getStringExtra("id");
            String[] positionIDArray = {positionID};
            getContentResolver().delete(DatabaseProvider.Trips.CONTENT_URI, "_id=?", positionIDArray);
        }


        JodaTimeAndroid.init(this);
        fabPlus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fabPlus.setOnClickListener(new View.OnClickListener() {
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

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3086253353164761~5017874930");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-86045128-1");
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
            recyclerViewAdapter = new MainViewAdapter(context, resources, cursor);
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
            recyclerViewAdapter = new MainViewAdapter(context, resources, cursor);
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

    public class MainViewAdapter extends RecyclerView.Adapter<MainViewHolder> {

        private Context context;
        private Resources resources;
        private ArrayList<String> tripIDArray = new ArrayList<String>();
        private String SQLPosition;
        private Cursor cursor;

        public MainViewAdapter(Context context, Resources resources, Cursor cursor){

            this.context = context;
            this.resources = resources;
            this.cursor = cursor;

        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_content, parent, false);

            return new MainViewHolder(view);

        }

        @Override
        public void onBindViewHolder(final MainViewHolder holder, final int position) {

            cursor.moveToPosition(position);
            tripIDArray.add(cursor.getString(cursor.getColumnIndex("_id")));
            Bitmap countryFlag = ImageConvert.getImage(cursor.getBlob(cursor.getColumnIndex("countryflag")));
            Bitmap backgroundImage = ImageConvert.getImage(cursor.getBlob(cursor.getColumnIndex("backgroundimage")));
            Drawable finalBackground = new BitmapDrawable(resources, backgroundImage);
            final String positionID = cursor.getString(cursor.getColumnIndex("_id"));
            final String tripName = cursor.getString(cursor.getColumnIndex("tripname"));
            final String tripStart = cursor.getString(cursor.getColumnIndex("startdate"));
            final String tripEnd = cursor.getString(cursor.getColumnIndex("enddate"));
            final String currencySymbol = cursor.getString(cursor.getColumnIndex("currencysymbol"));
            Double budget2 = Double.valueOf(cursor.getString(cursor.getColumnIndex("budget")));
            final String budget = ImageConvert.numberFormat(budget2);
            holder.minusSign.setVisibility(View.INVISIBLE);
            holder.tripName.setText(tripName);
            holder.tripDates.setText(tripStart + " - " + tripEnd);
            holder.budget.setText(currencySymbol + " " + budget);
            holder.countryFlag.setImageBitmap(countryFlag);
            holder.backgroundImage.setBackground(finalBackground);
            holder.backgroundImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent twoFragmentIntent = new Intent(context, TwoFragmentsCalendar.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    twoFragmentIntent.putExtra("id", positionID);
                    twoFragmentIntent.putExtra("tripname", tripName);
                    twoFragmentIntent.putExtra("tripstart", tripStart);
                    twoFragmentIntent.putExtra("tripend", tripEnd);
                    twoFragmentIntent.putExtra("budget", budget);
                    twoFragmentIntent.putExtra("currencysymbol", currencySymbol);
                    context.startActivity(twoFragmentIntent);
                }
            });

            holder.minusSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(position);

                }
            });
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();

        }

        public void removeAt(int position) {

            SQLPosition = tripIDArray.get(position);
            tripIDArray.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tripIDArray.size());
            String[] budgetIdArray = {SQLPosition};
            context.getContentResolver().delete(DatabaseProvider.Trips.CONTENT_URI, "_id=?", budgetIdArray);

        }

    }
}


