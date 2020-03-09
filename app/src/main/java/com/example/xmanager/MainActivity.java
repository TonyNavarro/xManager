package com.example.xmanager;

import androidx.appcompat.app.AppCompatActivity;

import static android.provider.BaseColumns._ID;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.xmanager.Constants.TABLE_NAME;
import static com.example.xmanager.Constants.NAME;
import static com.example.xmanager.Constants.CATEGORY;
import static com.example.xmanager.Constants.AMOUNT;
import static com.example.xmanager.Constants.DAY;
import static com.example.xmanager.Constants.MONTH;
import static com.example.xmanager.Constants.YEAR;
import static com.example.xmanager.Constants.BALANCE;


import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String[] FROM = {_ID, NAME, CATEGORY, AMOUNT, DAY, MONTH, YEAR, BALANCE};
    private static String ORDER_BY = _ID + "DESC";
    final ArrayList<String> theList = new ArrayList<>();
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this);


        displayTransactions();

    }

    //It updates the ListView when this activity is visited back.
    @Override
    protected void onResume() {
        super.onResume();
        displayTransactions();
    }

    public void launchAddIncome(View view) {
        Intent addIncome = new Intent(this, AddIncome.class);
        startActivity(addIncome);

    }

    public Cursor getTransactions() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, null);
        return cursor;
    }

    public void displayTransactions() {
        final Cursor cursor = getTransactions();
        final ListView listView = findViewById(R.id.latestTransactionTable);
        while (cursor.moveToNext()) {
            theList.add(cursor.getString(2) + "   " + cursor.getString(3));
            ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
            listView.setAdapter(listAdapter);
            System.out.println("The value for the balance is: " + cursor.getString(7));
        }

        cursor.close();
        displayBalance();
    }

    public void displayBalance() {
        final Cursor cursor = getTransactions();
        cursor.moveToLast();
        String balance = cursor.getString(7);
        TextView vBalance = findViewById(R.id.vBalance);
        vBalance.setText(balance);
    }

}
