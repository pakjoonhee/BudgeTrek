package com.joon.pak.travelite.calendar;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joon.pak.travelite.R;
import com.joon.pak.travelite.data.CalendarColumns;
import com.joon.pak.travelite.data.DatabaseProvider;
import com.joon.pak.travelite.data.TripData;
import com.joon.pak.travelite.utlity.ImageConvert;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
    private SharedPreferences settings;
    private Integer result;

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
                if (theImage == 0 || budgetName.getText().toString().matches("") || budgetAmount.getText().toString().matches("")) {
                    new MaterialDialog.Builder(AddBudget.this)
                            .title(R.string.dialog_title)
                            .content(R.string.dialog_content)
                            .positiveText(R.string.positive_button)
                            .negativeText(R.string.negative_button)
                            .show();
                } else {

                    try {
                        if (new AsyncHttpTask().execute().get() == 0) {
                            theImage = R.drawable.etc_icon;
                        } else {
                            theImage = result;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(AddBudget.this, RightSideFragment.class);
                    String userValue = budgetAmount.getText().toString();
                    Double doubleRound = Math.round(Double.parseDouble(userValue) * 100)/100.0;
                    amount = ImageConvert.numberFormat(doubleRound);
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
    public class AsyncHttpTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void...voids) {

            settings = getSharedPreferences("theIcon", MODE_PRIVATE);
            result = settings.getInt("theIcon", 0);
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }
    }

}
