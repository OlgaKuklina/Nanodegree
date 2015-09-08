package com.example.android.myappportfolio;

/**
 * Created by olgakuklina on 2015-09-08.
 */
public class ReviewData {
    private String reviewAuthor;
    private String reviewContent;

    public ReviewData(String reviewAuthor, String reviewContent) {
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }
}
