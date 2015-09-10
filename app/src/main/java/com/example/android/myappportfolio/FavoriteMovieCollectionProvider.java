package com.example.android.myappportfolio;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class FavoriteMovieCollectionProvider extends ContentProvider {
    private static final String TAG = "FavMovieCollectionProv";
    private static final String AUTHORITY = "com.example.popularmovies.provider";
    private FavoriteMovieCollectionDbHelper helper;
    private UriMatcher matcher;

    public FavoriteMovieCollectionProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(FavoriteMoviesContract.FavoriteMovieColumn.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/movie";
            case 2:
                return "vnd.android.cursor.item/movie";
            default:
                return null;
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long rowId = db.insert(FavoriteMoviesContract.FavoriteMovieColumn.TABLE_NAME, null, values);
        Log.v(TAG, "rowId = " + rowId);
        return ContentUris.withAppendedId(uri, values.getAsInteger(FavoriteMoviesContract.FavoriteMovieColumn.COLUMN_NAME_MOVIE_ID));
    }

    @Override
    public boolean onCreate() {
        helper = new FavoriteMovieCollectionDbHelper(getContext());
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, FavoriteMoviesContract.FavoriteMovieColumn.TABLE_NAME, 1);
        matcher.addURI(AUTHORITY, FavoriteMoviesContract.FavoriteMovieColumn.TABLE_NAME + "/#", 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (matcher.match(uri)) {
            case 1:
                builder.setTables(FavoriteMoviesContract.FavoriteMovieColumn.TABLE_NAME);
                break;
            case 2:
                builder.setTables(FavoriteMoviesContract.FavoriteMovieColumn.TABLE_NAME);
                builder.appendWhere(FavoriteMoviesContract.FavoriteMovieColumn.COLUMN_NAME_MOVIE_ID + " = " +
                        uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        Cursor cursor = builder.query(
                        db,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.update(FavoriteMoviesContract.FavoriteMovieColumn.TABLE_NAME, values, selection, selectionArgs);
    }
}
