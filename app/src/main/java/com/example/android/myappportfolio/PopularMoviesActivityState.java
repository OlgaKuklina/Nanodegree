package com.example.android.myappportfolio;

import java.util.List;

/**
 * Created by olgakuklina on 2015-09-10.
 */
public class PopularMoviesActivityState {
    private final String sortOrder;
    private final List<MovieData> movieDatas;

    public PopularMoviesActivityState(String sortOrder, List<MovieData> movieDatas) {
        this.sortOrder = sortOrder;
        this.movieDatas = movieDatas;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public List<MovieData> getMovieDatas() {
        return movieDatas;
    }
}
