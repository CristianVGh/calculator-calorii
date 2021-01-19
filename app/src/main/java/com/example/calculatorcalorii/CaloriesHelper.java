package com.example.calculatorcalorii;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CaloriesHelper extends SQLiteOpenHelper {

    // este incrementat de fiecare data cand se modifica DB
    private static final int DATABASE_VERSION = 1;

    // Numele fisierului local in dispozitivul mobil care salveaza toate datele
    private static final String DATABASE_NAME = "calories.db";

    //constructor care apeleaza constructorul superclasei folosind context
    //argumentul 3 reprezinta cursor factory si este null, nefiind folosit aici
    public CaloriesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + CaloriesContract.UsersEntry.TABLE_NAME + " (" +
                CaloriesContract.UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CaloriesContract.UsersEntry.COLUMN_USERNAME + " TEXT NOT NULL, " +
                CaloriesContract.UsersEntry.COLUMN_PASSWORD + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_USERS_TABLE);
        Log.d("CALORIEHELPER", " created users");

        final String SQL_CREATE_DAYS_TABLE = "CREATE TABLE " + CaloriesContract.DaysEntry.TABLE_NAME + " (" +
                CaloriesContract.DaysEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CaloriesContract.DaysEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                CaloriesContract.DaysEntry.COLUMN_STEPS + " INTEGER NOT NULL, " +
                CaloriesContract.DaysEntry.COLUMN_WEIGHT + " FLOAT NOT NULL, " +
                CaloriesContract.DaysEntry.COLUMN_CALORIES + " FLOAT NOT NULL, " +
                CaloriesContract.DaysEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + CaloriesContract.DaysEntry.COLUMN_USER_ID + ") REFERENCES " +
                CaloriesContract.UsersEntry.TABLE_NAME + " (" + CaloriesContract.UsersEntry._ID + " ));";
        db.execSQL(SQL_CREATE_DAYS_TABLE);
        Log.d("CALORIESHELPER", "CREATED DAYS");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CaloriesContract.UsersEntry.TABLE_NAME);
        Log.d("onUpgrade DB", " ");

        db.execSQL("DROP TABLE IF EXISTS " + CaloriesContract.DaysEntry.TABLE_NAME);
        Log.d("onUpgrade DB", " ");

        // Creeaza tabela din nou
        onCreate(db);
    }
}
