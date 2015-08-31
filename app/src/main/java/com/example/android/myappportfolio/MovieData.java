package com.example.android.myappportfolio;

/**
 * Created by olgakuklina on 2015-08-30.
 */
public class MovieData {
    private final String moviePoster;
    private final int movieId;


    public MovieData(String moviePoster, int movieId) {
        this.moviePoster = moviePoster;
        this.movieId = movieId;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public int getMovieId() {
        return movieId;
    }
}
