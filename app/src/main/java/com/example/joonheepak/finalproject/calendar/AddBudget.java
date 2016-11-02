package com.example.joonheepak.finalproject.calendar;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.joonheepak.finalproject.R;
import com.example.joonheepak.finalproject.data.CalendarColumns;
import com.example.joonheepak.finalproject.data.DatabaseProvider;
import com.example.joonheepak.finalproject.data.TripData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joonheepak on 10/5/16.
 */

public class AddBudget extends Activity {

    @BindView(R.id.done_button)
    TextView doneButton;
    @BindView(R.id.budget_amount)
    EditText budgetAmount;
    @BindView(R.id.budget_name)
    EditText budgetName;
    ContentValues values = new ContentValues();
    ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
    ArrayList<TripData> iconDetails = new ArrayList<TripData>();;
    String tripName;
    String tripBudget;
    String monthDay;
    Integer theImage;
    String amount;
    RecyclerView recyclerView;
    IconAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        tripName = intent.getStringExtra("tripname");
        tripBudget = intent.getStringExtra("tripbudget");
        monthDay = intent.getStringExtra("monthday");

        setContentView(R.layout.add_budget);
        ButterKnife.bind(this);
        theImage = R.drawable.etc_icon;

        String[] iconDescriptions = {getString(R.string.food), getString(R.string.lodging), getString(R.string.flight),
                getString(R.string.leisure), getString(R.string.etc)};
        Integer[] iconImages = {R.drawable.dining_icon, R.drawable.hotel_icon, R.drawable.flight_icon,
                R.drawable.local_bar_icon, R.drawable.etc_icon};

        for (int i=0; i < iconDescriptions.length; i++) {
            TripData tripData = new TripData();
            tripData.setIconDescription(iconDescriptions[i]);
            tripData.setIconImage(iconImages[i]);
            iconDetails.add(tripData);
        }

        recyclerView = (RecyclerView) findViewById(R.id.icon_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new IconAdapter(iconDetails, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theImage == null || budgetName.getText().toString().matches("") || budgetAmount.getText().toString().matches("")) {
                    new MaterialDialog.Builder(AddBudget.this)
                            .title(R.string.dialog_title)
                            .content(R.string.dialog_content)
                            .positiveText(R.string.positive_button)
                            .negativeText(R.string.negative_button)
                            .show();
                } else {
                    SharedPreferences settings = getSharedPreferences("theIcon", MODE_PRIVATE);
                    if (settings.getInt("theIcon", 0) < 0) {
                        return;
                    } else {
                        theImage = settings.getInt("theIcon", 0);
                    }
                    Intent intent = new Intent(AddBudget.this, RightSideFragment.class);
                    String userValue = budgetAmount.getText().toString();
                    Double doubleRound = Math.round(Double.parseDouble(userValue) * 100)/100.0;
                    amount = String.format("%.2f", doubleRound);
                    values.put(CalendarColumns.Icon, theImage);
                    values.put(CalendarColumns.Trip_Name, tripName);
                    values.put(CalendarColumns.Budget, tripBudget);
                    values.put(CalendarColumns.Month_Day, monthDay);
                    values.put(CalendarColumns.Specific_Budget, amount);
                    values.put(CalendarColumns.Budget_Name, budgetName.getText().toString());
                    operations.add(ContentProviderOperation.newInsert(DatabaseProvider.Calendar.CONTENT_URI).withValues(values).build());
                    try {
                        getContentResolver().applyBatch(DatabaseProvider.AUTHORITY, operations);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (OperationApplicationException e) {
                        e.printStackTrace();
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
