package com.example.xmanager;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {
    String TABLE_NAME = "Account";

    //Columns in the database
    String NAME = "name";
    String CATEGORY = "category";
    String AMOUNT = "amount";
    String DAY = "day";
    String MONTH = "month";
    String YEAR = "year";
    String BALANCE = "balance";
}
