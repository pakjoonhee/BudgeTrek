package com.joon.pak.travelite.calendar;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joon.pak.travelite.R;
import com.joon.pak.travelite.data.TripData;
import com.joon.pak.travelite.utlity.SimpleDividerItemDecoration;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Created by joonheepak on 10/3/16.
 */

public class LeftSideFragment extends Fragment {
    Context context;
    RecyclerView.Adapter CalendarAdapter;
    LocalDate startDate;
    LocalDate endDate;
    ArrayList<TripData> dates;

    public LeftSideFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_recycler, container, false);
        Intent intent = getActivity().getIntent();
        String[] tripStart = intent.getStringExtra("tripstart").split("/");
        String tripStart2 = tripStart[2] + "-" + tripStart[0] + "-" + tripStart[1];
        String[] tripEnd = intent.getStringExtra("tripend").split("/");
        String tripEnd2 = tripEnd[2] + "-" + tripEnd[0] + "-" + tripEnd[1];

        startDate = new LocalDate(tripStart2);
        endDate = new LocalDate(tripEnd2);


        dates = new ArrayList<TripData>();
        int days = Days.daysBetween(startDate, endDate).getDays()+1;
        for (int i=0; i < days; i++) {
            LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);
            TripData tripData = new TripData();
            tripData.setMonth(d.toString("MMM"));
            tripData.setDay(d.toString("dd"));
            dates.add(tripData);
        }
        context = getActivity();
        RecyclerView recyclerCalendar = (RecyclerView) rootView.findViewById(R.id.calendar_recycler_view);
        recyclerCalendar.setHasFixedSize(true);
        recyclerCalendar.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        CalendarAdapter = new CalendarAdapter(context, dates);
        recyclerCalendar.setAdapter(CalendarAdapter);

        return rootView;

    }

}
