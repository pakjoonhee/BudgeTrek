package com.example.joonheepak.finalproject.ui;

/**
 * Created by joonheepak on 9/11/16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joonheepak.finalproject.R;
import com.example.joonheepak.finalproject.calendar.TwoFragmentsCalendar;
import com.example.joonheepak.finalproject.data.DatabaseProvider;
import com.example.joonheepak.finalproject.data.TripData;
import com.example.joonheepak.finalproject.utlity.ImageConvert;

import java.util.ArrayList;


public class MainViewAdapter extends RecyclerView.Adapter<MainViewHolder> {
    ArrayList<TripData> tripData;
    Context context;
    Resources resources;
    ArrayList<String> tripIDArray = new ArrayList<String>();
    String SQLPosition;
    Cursor cursor;
    TextView minusTripButton;

    public MainViewAdapter(Context context, Resources resources, Cursor cursor, TextView minusTripButton){

        this.context = context;
        this.resources = resources;
        this.cursor = cursor;
        this.minusTripButton = minusTripButton;

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
        final String tripName = cursor.getString(cursor.getColumnIndex("tripname"));
        final String tripStart = cursor.getString(cursor.getColumnIndex("startdate"));
        final String tripEnd = cursor.getString(cursor.getColumnIndex("enddate"));
        final String currencySymbol = cursor.getString(cursor.getColumnIndex("currencysymbol"));
        Double budget2 = Double.valueOf(cursor.getString(cursor.getColumnIndex("budget")));
        final String budget = ImageConvert.numberFormat(budget2);
//        holder.minusSign.setVisibility(View.INVISIBLE);
        holder.tripName.setText(tripName);
        holder.tripDates.setText(tripStart + " - " + tripEnd);
        holder.budget.setText(currencySymbol + " " + budget);
        holder.countryFlag.setImageBitmap(countryFlag);
        holder.backgroundImage.setBackground(finalBackground);
        holder.backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent twoFragmentIntent = new Intent(context, TwoFragmentsCalendar.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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