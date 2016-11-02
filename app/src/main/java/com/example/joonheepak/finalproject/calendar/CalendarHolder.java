package com.example.joonheepak.finalproject.calendar;

/**
 * Created by joonheepak on 10/3/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.joonheepak.finalproject.R;

public class CalendarHolder extends RecyclerView.ViewHolder {
    public TextView theMonth;
    public TextView theDay;
    public RelativeLayout theBackground;


    public CalendarHolder(View itemView) {
        super(itemView);
        theMonth = (TextView) itemView.findViewById(R.id.the_month);
        theDay = (TextView) itemView.findViewById(R.id.the_day);
        theBackground = (RelativeLayout) itemView.findViewById(R.id.second_relative);

    }

}