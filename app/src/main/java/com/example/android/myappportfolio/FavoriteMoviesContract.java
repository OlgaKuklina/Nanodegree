package com.example.android.myappportfolio;

import android.provider.BaseColumns;

/**
 * Created by olgakuklina on 2015-09-09.
 */
public final class FavoriteMoviesContract {

    private FavoriteMoviesContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class FavoriteMovieColumn implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_MOVIE_ID = "movieid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_MOVIE_PLOT = "movieplot";
        public static final String COLUMN_POSTER_PATH = "path";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_VOTE_AVERAGE = "voteaverage";
    }
}

