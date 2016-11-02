package com.example.joonheepak.finalproject.ui;

/**
 * Created by joonheepak on 9/11/16.
 */
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.joonheepak.finalproject.R;

public class MainViewHolder extends RecyclerView.ViewHolder {
    public TextView tripName;
    public TextView tripDates;
    public TextView budget;
    public ImageView countryFlag;
    public ImageView minusSign;
    public LinearLayout backgroundImage;



    public MainViewHolder(View itemView) {
        super(itemView);
        tripName = (TextView) itemView.findViewById(R.id.trip_name);
        tripDates = (TextView) itemView.findViewById(R.id.trip_dates);
        budget = (TextView) itemView.findViewById(R.id.budget);
        countryFlag = (ImageView) itemView.findViewById(R.id.country_flag);
        backgroundImage = (LinearLayout) itemView.findViewById(R.id.trip_container);
        minusSign = (ImageView) itemView.findViewById(R.id.minus_sign);
    }

}