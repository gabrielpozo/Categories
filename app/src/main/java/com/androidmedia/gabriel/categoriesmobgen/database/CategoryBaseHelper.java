package com.androidmedia.gabriel.categoriesmobgen.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gabriel on 5/31/2017.
 */

public class CategoryBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";


    public CategoryBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table " + CategoryDbSchema.CategoryTable.NAME + "("+
                CategoryDbSchema.CategoryTable.Cols.ID + "," +
                CategoryDbSchema.CategoryTable.Cols.TITLE + "," +
                CategoryDbSchema.CategoryTable.Cols.URL + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
