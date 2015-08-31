package com.example.android.myappportfolio;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by olgakuklina on 2015-08-29.
 */
class FetchDetailsMovieTask extends AsyncTask<Void, Void, ArrayList<MovieData>>{
    private static final String POSTER_BASE_URI = "http://image.tmdb.org/t/p/w185";
    private static final String TAG = FetchDetailsMovieTask.class.getSimpleName();
    private final ImageAdapter adapter;

    FetchDetailsMovieTask(ImageAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<MovieData> doInBackground(Void... params) {
        ArrayList<MovieData>  moviePosters = new ArrayList<>();
        try {

            JSONObject jObj = JSONLoader.load("/discover/movie");
            Log.v(TAG, "page:" + jObj.getInt("page"));
            JSONArray movieArray = jObj.getJSONArray("results");
            Log.v(TAG,"length:" + movieArray.length());
            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.optJSONObject(i);
                String moviePoster = movie.getString("poster_path");
                int movieId = movie.getInt("id");
                MovieData data = new MovieData(POSTER_BASE_URI + moviePoster,movieId);
                moviePosters.add(data);
                Log.v(TAG, "moviePoster = " + moviePoster);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error ", e);
        }
        return moviePosters;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieData> moviePosters) {
        super.onPostExecute(moviePosters);
        if (moviePosters != null) {
            for (MovieData res : moviePosters) {
                adapter.add(res);
            }
            adapter.notifyDataSetChanged();
        }

    }
}

