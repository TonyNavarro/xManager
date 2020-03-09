package com.example.xmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;

import java.util.Calendar;

import static com.example.xmanager.Constants.NAME;
import static com.example.xmanager.Constants.CATEGORY;
import static com.example.xmanager.Constants.AMOUNT;
import static com.example.xmanager.Constants.DAY;
import static com.example.xmanager.Constants.MONTH;
import static com.example.xmanager.Constants.TABLE_NAME;
import static com.example.xmanager.Constants.YEAR;
import static com.example.xmanager.Constants.BALANCE;


public class AddIncome extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Database database;
    private EditText itemField, amountField;
    private TextView dateField, warningLabel;
    private Spinner categoryField;
    private int day, month, year, itemSelected;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String[] categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        database = new Database(this);

        // CONFIGURE THE SPINNER

        categoryField = findViewById(R.id.categoryField);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryField.setAdapter(adapter);
        categoryField.setSelection(0);
        categoryField.setOnItemSelectedListener(this);

        // DISPLAY THE CALENDAR
        dateField = findViewById(R.id.dateField);
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddIncome.this, android.R.style.Widget, mDateSetListener, year, month, day);
                dialog.getWindow();//.setLayout(width,2000);//setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dateField.setText(date);
            }
        };


    }

    public void addIncome(String name, String category, double amount, int day, int month, int year, double balance) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(CATEGORY, category);
        values.put(AMOUNT, amount);
        values.put(DAY, day);
        values.put(MONTH, month);
        values.put(YEAR, year);
        values.put(BALANCE, balance);
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    public void SaveData(View view) {
        warningLabel = findViewById(R.id.warningLabel);
        itemField = findViewById(R.id.itemField);
        categoryField = findViewById(R.id.categoryField);
        amountField = findViewById(R.id.amountField);
        categoryList = getResources().getStringArray(R.array.category_list);

        //Get the inputs from the user

        String itemInput = itemField.getText().toString();
        String amountInput = amountField.getText().toString();
        String categoryInput = categoryList[itemSelected];


        if (itemInput.isEmpty()) {
            warningLabel.setText("Name Cannot Be Empty!");
            warningLabel.setVisibility(View.VISIBLE);
            warningLabel.setTextColor(Color.RED);
        } else if (amountInput.isEmpty()) {
            warningLabel.setText("Amount Cannot Be Empty!");
            warningLabel.setVisibility(View.VISIBLE);
            warningLabel.setTextColor(Color.RED);

        } else {
            double finalAmountInput = Double.parseDouble(amountInput);
            double lastBalance = getBalance();
            double finalBalance = lastBalance + finalAmountInput;
            addIncome(itemInput, categoryInput, finalAmountInput, day, month, year, finalBalance);
            warningLabel.setVisibility(View.INVISIBLE);

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        itemSelected = i;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public double getBalance() {
        //check if the table is empty
        double finalBalance;
        SQLiteDatabase db = database.getReadableDatabase();
        String query = " SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        if (count > 0) {
            String query2 = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor1 = db.rawQuery(query2, null);
            cursor1.moveToLast();
            String balance = cursor1.getString(7);
            finalBalance = Double.parseDouble(balance);
            System.out.println("the value is " + finalBalance);
            cursor1.close();
            return finalBalance;

        } else {
            finalBalance = 0.00;
            cursor.close();
            return finalBalance;
        }
    }


}
