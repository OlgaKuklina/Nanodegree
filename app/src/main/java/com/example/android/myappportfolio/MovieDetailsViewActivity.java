package com.example.android.myappportfolio;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetailsViewActivity extends Activity {
    private static final String TAG = MovieDetailsViewActivity.class.getSimpleName();
    private ImageView moviePoster;
    private TextView movieDate;
    private TextView movieDuration;
    private TextView movieVoteAverage;
    private TextView moviePlot;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_view);

        moviePoster = (ImageView) findViewById(R.id.movie_poster);
        movieDate = (TextView) findViewById(R.id.movie_date);
        movieDuration = (TextView) findViewById(R.id.movie_duration);
        movieVoteAverage = (TextView) findViewById(R.id.vote_average);
        moviePlot = (TextView) findViewById(R.id.movie_plot);
        title = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, -1);
            FetchDetailsMovieTask task = new FetchDetailsMovieTask();
            task.execute(id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchDetailsMovieTask extends AsyncTask<Integer, Void, JSONObject> {
        private static final String POSTER_BASE_URI = "http://image.tmdb.org/t/p/w185";

        @Override
        protected JSONObject doInBackground(Integer... params) {
            JSONObject jObj = JSONLoader.load("/movie/" + params[0]);

            return jObj;
        }

        @Override
        protected void onPostExecute(JSONObject jObj) {
            super.onPostExecute(jObj);
            if (jObj != null) {

                try {

                    Picasso pic = Picasso.with(MovieDetailsViewActivity.this);
                    pic.load(POSTER_BASE_URI + jObj.getString("poster_path"))
                            .into(moviePoster);
                    movieDate.setText(jObj.getString("release_date"));
                    movieDuration.setText(jObj.getString("runtime"));
                    movieVoteAverage.setText(jObj.getString("vote_average"));
                    moviePlot.setText(jObj.getString("overview"));
                    title.setText(jObj.getString("title"));

                } catch (JSONException e) {
                    Log.e(TAG, "", e);
                }
            }

        }
    }

}
