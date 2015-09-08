package com.example.android.myappportfolio;

import android.net.Uri;

/**
 * Created by olgakuklina on 2015-09-07.
 */
public class TrailerData {
    private Uri trailerUri;
    private String trailerName;

    public TrailerData(Uri trailerUri, String trailerName) {
        this.trailerUri = trailerUri;
        this.trailerName = trailerName;
    }

    public Uri getTrailerUri() {
        return trailerUri;
    }

    public String getTrailerName() {
        return trailerName;
    }
}
