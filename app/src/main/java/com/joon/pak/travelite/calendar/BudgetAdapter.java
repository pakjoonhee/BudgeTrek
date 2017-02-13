package com.joon.pak.travelite.calendar;

/**
 * Created by joonheepak on 10/7/16.
 */


import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joon.pak.travelite.R;
import com.joon.pak.travelite.data.DatabaseProvider;
import com.joon.pak.travelite.data.TripData;
import com.joon.pak.travelite.utlity.ImageConvert;

import java.util.ArrayList;


public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    ArrayList<TripData> tripData;
    Context context;
    Resources resources;
    Resources.Theme theme;
    ArrayList<String> budgetIDArray = new ArrayList<String>();
    String SQLPosition;
    RecyclerView recyclerView;
    String currencySymbol;


    public BudgetAdapter(Context context, ArrayList<TripData> tripData, Resources resources, RecyclerView recyclerView, String currencySymbol) {

        this.tripData = tripData;
        this.context = context;
        this.resources = resources;
        this.recyclerView = recyclerView;
        this.currencySymbol = currencySymbol;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView budgetImage;
        public TextView budgetName;
        public TextView budgetAmount;
        public RelativeLayout budgetBackground;
        public ImageView minusSign;
        public TextView budgetCurrency;

        public ViewHolder(View itemView) {
            super(itemView);
            budgetImage = (ImageView) itemView.findViewById(R.id.budget_icon);
            budgetAmount = (TextView) itemView.findViewById(R.id.the_budget_amount);
            budgetName = (TextView) itemView.findViewById(R.id.the_budget_name);
            budgetBackground = (RelativeLayout) itemView.findViewById(R.id.budget_background);
            minusSign = (ImageView) itemView.findViewById(R.id.minus_sign);
            budgetCurrency = (TextView) itemView.findViewById(R.id.budget_currency);
            minusSign.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.equals(minusSign)) {
                removeAt(getAdapterPosition());
            }
        }
    }

    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_views, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TripData tripInfo = tripData.get(position);
        String theBudgetName = tripInfo.getSpecificBudgetAmount();
        Double theBudgetAmount = Double.valueOf(tripInfo.getSpecificBudgetName());
        //naming errors go way back...
        String theBudgetAmount2 = ImageConvert.numberFormat(theBudgetAmount);
        String theBudgetId = tripInfo.getBudgetID();
        budgetIDArray.add(theBudgetId);
        Integer theBudgetImage = tripInfo.getBudgetImage();
        holder.minusSign.setVisibility(View.INVISIBLE);
        holder.budgetAmount.setText(theBudgetName);
        holder.budgetCurrency.setText(currencySymbol + " ");
        holder.budgetName.setText(theBudgetAmount2);
        holder.budgetBackground.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.minusSign.setVisibility(View.VISIBLE);
                return false;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.budgetImage.setImageDrawable(resources.getDrawable(theBudgetImage, theme));
        }

    }

    @Override
    public int getItemCount() {
        return tripData.size();
    }

    public void removeAt(int position) {

        SQLPosition = budgetIDArray.get(position);
        tripData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tripData.size());
        budgetIDArray.remove(position);
        String[] budgetIdArray = {SQLPosition};
        context.getContentResolver().delete(DatabaseProvider.Calendar.CONTENT_URI, "id=?", budgetIdArray);

    }

}



