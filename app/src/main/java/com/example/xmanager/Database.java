package com.example.xmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.xmanager.Constants.TABLE_NAME;
import static com.example.xmanager.Constants.NAME;
import static com.example.xmanager.Constants.CATEGORY;
import static com.example.xmanager.Constants.AMOUNT;
import static com.example.xmanager.Constants.DAY;
import static com.example.xmanager.Constants.MONTH;
import static com.example.xmanager.Constants.YEAR;
import static com.example.xmanager.Constants.BALANCE;


public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;


    //Constructor of the class
    public Database(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + CATEGORY + " TEXT, "
                + AMOUNT + " DOUBLE, "
                + DAY + " INTEGER, "
                + MONTH + " INTEGER, "
                + YEAR + " INTEGER, "
                + BALANCE + " DOUBLE );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
