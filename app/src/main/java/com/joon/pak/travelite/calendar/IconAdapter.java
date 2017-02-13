package com.joon.pak.travelite.calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joon.pak.travelite.R;
import com.joon.pak.travelite.data.TripData;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by joonheepak on 10/24/16.
 */

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {

    ArrayList<TripData> tripData;
    Context context;

    public IconAdapter(ArrayList<TripData> tripData, Context context) {
        this.tripData = tripData;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout iconBackground;
        public TextView iconDescription;
        public ImageView theIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            iconDescription = (TextView) itemView.findViewById(R.id.icon_description);
            iconBackground = (LinearLayout) itemView.findViewById(R.id.icon_layout);
            theIcon = (ImageView) itemView.findViewById(R.id.the_icon);
        }
    }


    @Override
    public IconAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_views, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TripData tripInfo = tripData.get(position);
        holder.theIcon.setBackgroundResource(tripInfo.getIconImage());
        holder.iconDescription.setText(tripInfo.getIconDescription());
        holder.iconBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = context.getSharedPreferences("theIcon", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("theIcon", tripInfo.getIconImage());
                editor.apply();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tripData.size();
    }
}
