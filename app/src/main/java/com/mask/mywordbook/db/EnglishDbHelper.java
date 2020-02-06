package com.mask.mywordbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EnglishDbHelper extends SQLiteOpenHelper {

    public final static String TABLE = "Word";

    private final static String CREATE_TABLE = "create table Word (" +
            "id integer primary key autoincrement," +
            "target text not null," +
            "result text not null)";

    public EnglishDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists word");
        onCreate(db);

    }
}
