package com.example.android.myappportfolio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olgakuklina on 2015-09-09.
 */
public class FavoriteMovieCollectionDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FavoriteMovieCollectionDBName.db";

    public FavoriteMovieCollectionDbHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE favorite (_id INTEGER PRIMARY KEY, movieid INTEGER, title TEXT, movieplot TEXT, path TEXT, year TEXT, duration INTEGER, voteaverage REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS favorite");
        onCreate(db);
    }
}
