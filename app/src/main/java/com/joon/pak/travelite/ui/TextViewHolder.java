package com.joon.pak.travelite.ui;

/**
 * Created by joonheepak on 9/11/16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joon.pak.travelite.R;

public class TextViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public TextViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.trip_name);
    }
}