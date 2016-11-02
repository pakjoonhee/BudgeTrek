package com.example.joonheepak.finalproject.calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joonheepak.finalproject.R;
import com.example.joonheepak.finalproject.data.CalendarColumns;
import com.example.joonheepak.finalproject.data.DatabaseProvider;
import com.example.joonheepak.finalproject.data.TripData;
import com.example.joonheepak.finalproject.utlity.SimpleDividerItemDecoration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by joonheepak on 10/3/16.
 */

public class RightSideFragment extends android.support.v4.app.Fragment {
    private BudgetAdapter recyclerViewAdapter;
    @BindView(R.id.first_budget) TextView totalBudget;
    @BindView(R.id.calculated_budget) TextView calculatedBudget;
    @BindView(R.id.recycler_view_budget) RecyclerView recycleBudget;
    @BindView(R.id.floating_action) FloatingActionButton floatingAddButton;
    @BindView(R.id.firstTextviews)
    LinearLayout firstTextViews;
    @BindView(R.id.secondTextviews) LinearLayout secondTextViews;
    @BindView(R.id.budget_currency_right) TextView budgetSymbol;

    private ArrayList<TripData> budgetArrayList = new ArrayList<>();
    private ArrayList<Double> budgetList = new ArrayList<>();
    Bundle bundle;
    String monthDay;
    Context context;
    String tripName;
    String tripBudget;
    Cursor mainCursor;
    Cursor secondCursor;
    String specificBudgetName;
    String secondBudgetName;
    String specificBudgetAmount;
    String budgetID;
    Integer budgetImage;
    Resources resources;
    View rootView;
    String newMonthDay;
    RecyclerView recyclerView;
    String currencySymbol;
    Double budgetcalculation;
    SharedPreferences settings;
    Double listSum;


    public RightSideFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.right_fragment, container, false);
        ButterKnife.bind(this, rootView);
        Intent intent = getActivity().getIntent();
        tripName = intent.getStringExtra("tripname");
        tripBudget = intent.getStringExtra("budget");

        Double tripBudget2 = Double.valueOf(intent.getStringExtra("budget"));
        currencySymbol = intent.getStringExtra("currencysymbol");
        totalBudget.setText(currencySymbol + " " + String.format("%.2f", tripBudget2));
        budgetSymbol.setText(currencySymbol + " ");


        if (bundle == null) {
            firstTextViews.setVisibility(View.INVISIBLE);
            secondTextViews.setVisibility(View.INVISIBLE);
            totalBudget.setVisibility(View.INVISIBLE);
            calculatedBudget.setVisibility(View.INVISIBLE);
            floatingAddButton.setVisibility(View.INVISIBLE);
        }else if (bundle == getArguments()) {
            monthDay = bundle.getString("monthday");
            settings = getActivity().getSharedPreferences("thePosition", MODE_PRIVATE);
            try {
                if (SchematicCheck()) {
                    RetrieveData();
                    AddAdapter();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                secondSchematicCheck();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddBudget.class);
                intent.putExtra("tripname", tripName);
                intent.putExtra("tripbudget", tripBudget);
                intent.putExtra("monthday", monthDay);
                startActivityForResult(intent, 1);
            }
        });


        return rootView;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), "it worked", Toast.LENGTH_SHORT).show();
                SchematicCheck();
                mainCursor.moveToLast();
                specificBudgetName = mainCursor.getString(mainCursor.getColumnIndex("specificbudget"));
                specificBudgetAmount = mainCursor.getString(mainCursor.getColumnIndex("budgetname"));
                budgetImage = mainCursor.getInt(mainCursor.getColumnIndex("icon"));
                budgetID = mainCursor.getString(mainCursor.getColumnIndex("id"));
                budgetList.add(Double.valueOf(specificBudgetName));
                double sum = 0;
                for(Double d : budgetList)
                    sum += d;
                listSum = sum;
                calculatedBudget.setText(String.format("%.2f", listSum));

                TripData budgetInfo = new TripData();
                budgetInfo.setSpecificBudgetName(specificBudgetName);
                budgetInfo.setSpecificBudgetAmount(specificBudgetAmount);
                budgetInfo.setBudgetImage(budgetImage);
                budgetInfo.setBudgetID(budgetID);

                budgetArrayList.add(budgetInfo);
                if (budgetArrayList.size() == 1) {
                    AddAdapter();
                } else {
                    recyclerViewAdapter.notifyItemInserted(budgetArrayList.size() - 1);
                    budgetArrayList.get(0);
                }
            }
        }
    }

    public boolean SchematicCheck() {
        context = getActivity();
        String[] args = {tripName, tripBudget, monthDay};

        mainCursor = context.getContentResolver().query(DatabaseProvider.Calendar.CONTENT_URI,
                new String[] {CalendarColumns._ID, CalendarColumns.Trip_Name, CalendarColumns.Month_Day,
                        CalendarColumns.Icon, CalendarColumns.Budget, CalendarColumns.Specific_Budget,
                        CalendarColumns.Budget_Name}, "tripname=? AND budget=? AND monthday=?", args, null);

        if(mainCursor.getCount() <= 0){
            mainCursor.close();
            return false;
        }
        return true;
    }

    public void secondSchematicCheck() {
        String[] args2 = {tripName, tripBudget};
        secondCursor = context.getContentResolver().query(DatabaseProvider.Calendar.CONTENT_URI,
            new String[] {CalendarColumns._ID, CalendarColumns.Trip_Name, CalendarColumns.Month_Day,
                CalendarColumns.Icon, CalendarColumns.Budget, CalendarColumns.Specific_Budget,
                CalendarColumns.Budget_Name}, "tripname=? AND budget=?", args2, null);

        secondCursor.moveToFirst();
        while (!secondCursor.isAfterLast()) {
            secondBudgetName = secondCursor.getString(secondCursor.getColumnIndex("specificbudget"));
            budgetList.add(Double.valueOf(secondBudgetName));
            secondCursor.moveToNext();
        }

        double sum = 0;
        for(Double d : budgetList)
            sum += d;
        listSum = sum;
        calculatedBudget.setText(String.format("%.2f", listSum));

    }

    public ArrayList<TripData> RetrieveData() {
        mainCursor.moveToFirst();
        while (!mainCursor.isAfterLast()) {
            specificBudgetName = mainCursor.getString(mainCursor.getColumnIndex("specificbudget"));
            specificBudgetAmount = mainCursor.getString(mainCursor.getColumnIndex("budgetname"));
            budgetImage = mainCursor.getInt(mainCursor.getColumnIndex("icon"));
            budgetID = mainCursor.getString(mainCursor.getColumnIndex("id"));
            newMonthDay = mainCursor.getString(mainCursor.getColumnIndex("monthday"));
            TripData budgetInfo = new TripData();
            budgetInfo.setSpecificBudgetName(specificBudgetName);
            budgetInfo.setSpecificBudgetAmount(specificBudgetAmount);
            budgetInfo.setBudgetImage(budgetImage);
            budgetInfo.setBudgetID(budgetID);
            budgetInfo.setStartDate(newMonthDay);
            budgetArrayList.add(budgetInfo);
            mainCursor.moveToNext();
        }

        mainCursor.close();
        return budgetArrayList;
    }

    public void AddAdapter() {
        resources = getContext().getResources();
        context = getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_budget);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
        recyclerViewAdapter = new BudgetAdapter(context, budgetArrayList, resources, recyclerView, currencySymbol);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}
