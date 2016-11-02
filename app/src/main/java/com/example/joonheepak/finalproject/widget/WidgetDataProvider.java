package com.example.joonheepak.finalproject.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.joonheepak.finalproject.R;
import com.example.joonheepak.finalproject.data.DatabaseColumns;
import com.example.joonheepak.finalproject.data.DatabaseProvider;

/**
 * Created by joonheepak on 10/17/16.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    Context context = null;
    Cursor cursor;
    String tripName;
    String tripDates;
    String budget;
    RemoteViews view;

    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (cursor.moveToPosition(position)) {
            tripName = cursor.getString(cursor.getColumnIndex("tripname"));
            String startDate = cursor.getString(cursor.getColumnIndex("startdate"));
            String endDate = cursor.getString(cursor.getColumnIndex("enddate"));
            String currencySymbol = cursor.getString(cursor.getColumnIndex("currencysymbol"));

            tripDates = startDate + " - " + endDate;
            budget = cursor.getString(cursor.getColumnIndex("budget"));
            String finalBudget = currencySymbol + " " + budget;
            view = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            view.setTextViewText(R.id.trip_name_widget, tripName);
            view.setTextViewText(R.id.trip_budget_widget, finalBudget);
            view.setTextViewText(R.id.trip_dates_widget, tripDates);
        }
//        Intent newIntent = new Intent();
//        newIntent.putExtra("tripname", tripName);
//        view.setOnClickFillInIntent(R.id.rootview, newIntent);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void initData() {
        cursor = context.getContentResolver().query(DatabaseProvider.Trips.CONTENT_URI,
                new String[] {DatabaseColumns._ID, DatabaseColumns.Trip_Name, DatabaseColumns.Start_Date,
                        DatabaseColumns.End_Date, DatabaseColumns.Budget, DatabaseColumns.Country_Flag,
                        DatabaseColumns.Background_Image, DatabaseColumns.Currency_Symbol}, null, null, null);
    }

}

