package com.example.android.myappportfolio;

import java.util.List;

/**
 * Created by olgakuklina on 2015-09-10.
 */
public class MovieDetailsViewActivityState {
    private final List<TrailerData> trailerDatas;
    private final List<ReviewData> reviewDatas;
    private final MovieDataContainer movieDataContainer;

    public MovieDetailsViewActivityState(List<TrailerData> trailerDatas, List<ReviewData> reviewDatas, MovieDataContainer movieDataContainer) {
        this.trailerDatas = trailerDatas;
        this.reviewDatas = reviewDatas;
        this.movieDataContainer = movieDataContainer;
    }

    public List<TrailerData> getTrailerDatas() {
        return trailerDatas;
    }

    public List<ReviewData> getReviewDatas() {
        return reviewDatas;
    }

    public MovieDataContainer getMovieDataContainer() {
        return movieDataContainer;
    }
}
