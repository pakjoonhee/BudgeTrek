package com.example.joonheepak.finalproject.ui;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.joonheepak.finalproject.R;
import com.example.joonheepak.finalproject.data.DatabaseColumns;
import com.example.joonheepak.finalproject.data.DatabaseProvider;
import com.example.joonheepak.finalproject.utlity.ImageConvert;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by joonheepak on 9/15/16.
 */
public class AddTripDetails extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;

    @BindView(R.id.dateStartText) TextView dateStartText;
    @BindView(R.id.dateEndText) TextView dateEndText;
    @BindView(R.id.trip_name_text_awesome) TextView dateStuff;
    @BindView(R.id.flag_button) TextView flagButton;
    @BindView(R.id.flag_image_button) ImageButton flagIcon;
    @BindView(R.id.currency_symbol_button) Button currencySymbolButton;
    @BindView(R.id.trip_name) EditText editTripName;
    @BindView(R.id.start_date_button) ImageButton startDateButton;
    @BindView(R.id.end_date_button) ImageButton endDateButton;
    @BindView(R.id.currency_edit) EditText budgetEdit;
    @BindView(R.id.upload_image) ImageView uploadImage;
    @BindView(R.id.done) TextView doneText;
    int theMonth;
    int REQUEST_STORAGE = 5;
    Context mContext;
    String startDateData;
    String endDateData;
    CountryPicker picker;
    Bitmap backgroundImage;
    byte[] theBackgroundImage;
    byte[] countryFlagImage;
    int imageResourceNumber;
    String amount;
    NumberPicker NumberPicker1;
    NumberPicker NumberPicker2;
    NumberPicker NumberPicker3;
    String currencySymbol2;
    LinearLayout layout;
    private static int RESULT_LOAD_IMAGE = 3;
    private String dateStart;
    private String dateEnd;
    boolean dateCheck2;
    private String theMonthCheck;
    private String theDayCheck;
    private int theYear;
    private String dateCheckFormat1;
    private boolean dateCheck3;
    private String dateCheckFormat2;
    private Date date1;
    private Date date2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        uploadImage.setImageResource(R.drawable.cave_picture);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        final String USCurrency = "$";
        currencySymbolButton.setText(USCurrency);
        currencySymbol2 = USCurrency;
        startDateButton.setImageResource(R.drawable.calendar_icon);
        endDateButton.setImageResource(R.drawable.calendar_icon);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");


        currencySymbolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker = CountryPicker.newInstance("Select Country");
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        if (code.equals("AQ")) {
                            currencySymbolButton.setText(USCurrency);
                            currencySymbol2 = USCurrency;
                        } else {
                            Currency currencySymbol1 = Currency.getInstance(getCurrencySymbol(code));
                            currencySymbol2 = currencySymbol1.getSymbol();
                            currencySymbolButton.setText(currencySymbol2);
                        }


                        picker.dismiss();


                    }
                });
            }
        });

        editTripName.setText(tripName);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();
            }
        });
        mContext = this;
        doneText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    date1 = new SimpleDateFormat("yyyy/MM/dd").parse(dateCheckFormat1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    date2 = new SimpleDateFormat("yyyy/MM/dd").parse(dateCheckFormat2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (countryFlagImage == null || editTripName.getText().toString().matches("") || dateStartText == null ||
                        dateEndText == null || budgetEdit.getText().toString().matches("") || currencySymbol2 == null) {
                    new MaterialDialog.Builder(mContext)
                            .title(R.string.dialog_title)
                            .content(R.string.dialog_content)
                            .positiveText(R.string.positive_button)
                            .negativeText(R.string.negative_button)
                            .show();
                } else if (!dateCheck2 || !dateCheck3 || date2.before(date1)) {
                    new MaterialDialog.Builder(mContext)
                            .title(R.string.dialog_title)
                            .content(R.string.false_dates)
                            .positiveText(R.string.positive_button)
                            .negativeText(R.string.negative_button)
                            .show();

                } else if (theBackgroundImage == null) {
                    new MaterialDialog.Builder(mContext)
                            .title(R.string.dialog_title)
                            .content(R.string.upload_image)
                            .positiveText(R.string.positive_button)
                            .negativeText(R.string.negative_button)
                            .show();
                } else {

                    String userValue = budgetEdit.getText().toString();
                    amount = String.valueOf(Math.round(Double.parseDouble(userValue) * 100)/100.0);
                    Intent backToMain = new Intent(AddTripDetails.this, MainActivity.class);
                    ContentValues values = new ContentValues();
                    ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();;

                    values.put(DatabaseColumns.Trip_Name, editTripName.getText().toString());
                    values.put(DatabaseColumns.Budget, amount);
                    values.put(DatabaseColumns.Start_Date, dateStartText.getText().toString());
                    values.put(DatabaseColumns.End_Date, dateEndText.getText().toString());
                    values.put(DatabaseColumns.Background_Image, theBackgroundImage);
                    values.put(DatabaseColumns.Country_Flag, countryFlagImage);
                    values.put(DatabaseColumns.Currency_Symbol, currencySymbol2);

                    operations.add(ContentProviderOperation.newInsert(DatabaseProvider.Trips.CONTENT_URI).withValues(values).build());
                    try {
                        getContentResolver().applyBatch(DatabaseProvider.AUTHORITY, operations);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (OperationApplicationException e) {
                        e.printStackTrace();
                    }

                    startActivity(backToMain);
                }
            }
        });


    }



    private void checkPermissions() {
        if (hasPermissionGranted()) {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
        } else {
            requestPermission();
        }
    }

    public boolean hasPermissionGranted(){
        return  ContextCompat.checkSelfPermission(AddTripDetails.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(AddTripDetails.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        View parentLayout = findViewById(R.id.blahcrap);

        if (requestCode == REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);


            } else if (ActivityCompat.shouldShowRequestPermissionRationale(AddTripDetails.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Drawable d = getResources().getDrawable(R.drawable.flowers, null);
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                byte[] imageSet = ImageConvert.getBytes(bitmap);
                theBackgroundImage = imageSet;
                uploadImage.setImageBitmap(bitmap);

                Snackbar.make(parentLayout, R.string.storage_permission,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(AddTripDetails.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        REQUEST_STORAGE);
                            }
                        })
                        .show();



            } else {
                Drawable d = getResources().getDrawable(R.drawable.flowers, null);
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                byte[] imageSet = ImageConvert.getBytes(bitmap);
                theBackgroundImage = imageSet;
                uploadImage.setImageBitmap(bitmap);

                Snackbar.make(parentLayout, R.string.settings_permission,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", AddTripDetails.this.getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("dateStartText", dateStartText.getText().toString());
        savedInstanceState.putString("dateEndText", dateEndText.getText().toString());
        savedInstanceState.putString("currencySymbol", currencySymbol2);
        savedInstanceState.putInt("imageResourceNumber", imageResourceNumber);
        savedInstanceState.putByteArray("theBackgroundImage", theBackgroundImage);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startDateData = savedInstanceState.getString("dateStartText");
        dateStartText.setText(startDateData);
        endDateData = savedInstanceState.getString("dateEndText");
        dateEndText.setText(endDateData);
        currencySymbol2 = savedInstanceState.getString("currencySymbol");
        currencySymbolButton.setText(currencySymbol2);
        imageResourceNumber = savedInstanceState.getInt("imageResourceNumber");
        flagIcon.setImageResource(imageResourceNumber);
        theBackgroundImage = savedInstanceState.getByteArray("theBackgroundImage");
        if (theBackgroundImage != null) {
            Bitmap backgroundBitmap = ImageConvert.getImage(theBackgroundImage);
            uploadImage.setImageBitmap(backgroundBitmap);
        }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            uploadImage = (ImageView) findViewById(R.id.upload_image);
            backgroundImage = BitmapFactory.decodeFile(picturePath);
            theBackgroundImage = ImageConvert.getBytes(backgroundImage);
            uploadImage.setImageBitmap(backgroundImage);


        }
    }

    public static String getCurrencySymbol(String countryCode) {
        return Currency.getInstance(new Locale("en", countryCode)).getCurrencyCode();

    }

    public void onClickFlag(View v) {
        picker = CountryPicker.newInstance("Select Country");
        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {

            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {


                flagButton.setText(name);
                flagIcon.setImageResource(flagDrawableResID);
                Bitmap bm = BitmapFactory.decodeResource(getResources(), flagDrawableResID);
                countryFlagImage = ImageConvert.getBytes(bm);
                imageResourceNumber = flagDrawableResID;
                picker.dismiss();
            }
        });
    }

    public static boolean validateDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    public void finalCheckDates() {
        theMonthCheck = null;
        theDayCheck = null;
        theYear = NumberPicker3.getValue();
        if (NumberPicker1.getValue() < 10) {
            theMonthCheck = "0" + NumberPicker1.getValue();
        } else {
            theMonthCheck = String.valueOf(NumberPicker1.getValue());
        }
        if (NumberPicker2.getValue() < 10) {
            theDayCheck = "0" + NumberPicker2.getValue();
        } else {
            theDayCheck = String.valueOf(NumberPicker2.getValue());
        }

    }

    public void onClickDates1(View v) {
        wheelDialog();
        new AlertDialog.Builder(AddTripDetails.this)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finalCheckDates();
                        dateStart = theMonthCheck + "/" + theDayCheck + "/" + theYear;
                        dateCheckFormat1 = theYear + "/" + theMonthCheck + "/" + theDayCheck;
                        dateCheck2 = validateDate(dateCheckFormat1);
                        dateStartText.setText(dateStart);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();

    }

    public void onClickDates2(View v) {
        wheelDialog();
        new AlertDialog.Builder(AddTripDetails.this)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finalCheckDates();
                        dateEnd = theMonthCheck + "/" + theDayCheck + "/" + theYear;
                        dateCheckFormat2 = theYear + "/" + theMonthCheck + "/" + theDayCheck;
                        dateCheck3 = validateDate(dateCheckFormat2);
                        dateEndText.setText(dateEnd);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();

    }

    public void wheelDialog() {
        layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        NumberPicker1 = new NumberPicker(mContext);
        NumberPicker1.setMaxValue(12);
        NumberPicker1.setMinValue(1);

        NumberPicker2 = new NumberPicker(mContext);
        NumberPicker2.setMaxValue(31);
        NumberPicker2.setMinValue(1);

        NumberPicker3 = new NumberPicker(mContext);
        NumberPicker3.setMaxValue(2050);
        NumberPicker3.setMinValue(2016);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.weight = 1;

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.weight = 1;

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params3.weight = 1;

        layout.setLayoutParams(params);
        layout.addView(NumberPicker1,params1);
        layout.addView(NumberPicker2,params2);
        layout.addView(NumberPicker3, params3);

        NumberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                theMonth = newVal;

            }

        });
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            return 0;
        }
    }

}

//        budgetEdit.setFocusable(true);
//        budgetEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if(hasFocus){
//                    Toast.makeText(getApplicationContext(), "on focus", Toast.LENGTH_LONG).show();
//                }else {
//                    SharedPreferences settings = getSharedPreferences("theCountry", MODE_PRIVATE);
//                    if (settings.getString("countryCode", null) == null) {
//                        return;
//                    } else {
//                        String code = settings.getString("countryCode", null);
//                        String currencyCode = getCurrencySymbol(code);
//                        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale(code));
//                        format.setCurrency(Currency.getInstance(currencyCode));
//                        String userValue = budgetEdit.getText().toString().replaceAll("[^\\d]", "");
//                        Double amount = Double.parseDouble(userValue);
//                        String formattedValue = format.format(amount);
//                        budgetEdit.setText(formattedValue);
//
//                    }
//                }
//            }
//
//        });

