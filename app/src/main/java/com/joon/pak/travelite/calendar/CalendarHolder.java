package com.joon.pak.travelite.calendar;

/**
 * Created by joonheepak on 10/3/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joon.pak.travelite.R;

public class CalendarHolder extends RecyclerView.ViewHolder {
    public Button theMonth;
    public Button theDay;
    public LinearLayout theBackground;


    public CalendarHolder(View itemView) {
        super(itemView);
        theMonth = (Button) itemView.findViewById(R.id.the_month);
        theDay = (Button) itemView.findViewById(R.id.the_day);
        theBackground = (LinearLayout) itemView.findViewById(R.id.second_relative);

    }

}