package com.example.android.myappportfolio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsViewActivity extends Activity {
    private static final String TAG = MovieDetailsViewActivity.class.getSimpleName();
    private ImageView moviePoster;
    private TextView movieDate;
    private TextView movieDuration;
    private TextView movieVoteAverage;
    private TextView moviePlot;
    private TextView title;
    private LinearLayout trailerList;
    private LinearLayout reviewList;
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
        trailerList = (LinearLayout) findViewById(R.id.movie_trailers);
        reviewList = (LinearLayout) findViewById(R.id.movie_reviews);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, -1);
            FetchDetailsMovieTask task = new FetchDetailsMovieTask();
            task.execute(id);
            FetchTrailerMovieTask tTask = new FetchTrailerMovieTask();
            tTask.execute(id);
            FetchReviewMovieTask rTask = new FetchReviewMovieTask();
            rTask.execute(id);
        }

    }

    private void populateTrailerList(List<TrailerData> data) {
        for (final TrailerData trailer : data) {
            View view = getLayoutInflater().inflate(R.layout.movie_trailer_list_item, null);
            TextView tralerName = (TextView) view.findViewById(R.id.trailer_name);
            tralerName.setText(trailer.getTrailerName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, trailer.getTrailerUri()));
                }
            });
            trailerList.addView(view);
        }
    }

    private void populateReviewList(List<ReviewData> data) {
        for (final ReviewData review : data) {
            View view = getLayoutInflater().inflate(R.layout.movie_review_list_item, null);
            TextView reviewAuthor = (TextView) view.findViewById(R.id.review_author);
            reviewAuthor.setText(review.getReviewAuthor());
            TextView reviewContent = (TextView) view.findViewById(R.id.review_content);
            reviewContent.setText(review.getReviewContent());
            reviewList.addView(view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_movie_details_view, menu);
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
                            .error(R.drawable.no_movies)
                            .into(moviePoster);

                    if (StringUtils.isNotBlank(jObj.getString("release_date"))) {
                        movieDate.setText(jObj.getString("release_date"));
                    } else {
                        movieDate.setText(R.string.details_view_no_release_date);
                    }
                    if (jObj.get("runtime") != null) {
                        movieDuration.setText(jObj.getString("runtime") + getString(R.string.details_view_text_minutes));
                    } else {
                        movieDuration.setVisibility(View.GONE);
                    }
                    if (StringUtils.isNotBlank(jObj.getString("vote_average"))) {
                        movieVoteAverage.setText(jObj.getString("vote_average") + getString(R.string.details_view_text_vote_average_divider));
                    } else {
                        movieVoteAverage.setVisibility(View.GONE);
                    }
                    if (StringUtils.isBlank(jObj.getString("overview"))) {
                        moviePlot.setText(R.string.details_view_no_description);
                    } else {
                        moviePlot.setText(jObj.getString("overview"));
                    }
                    title.setText(jObj.getString("title"));

                } catch (JSONException e) {
                    Log.e(TAG, "", e);
                }
            }

        }
    }

    private class FetchTrailerMovieTask extends AsyncTask<Integer, Void, JSONObject> {
        private static final String TRAILER_BASE_URI = "http://www.youtube.com/watch?v=";

        @Override
        protected JSONObject doInBackground(Integer... params) {
            JSONObject jObj = JSONLoader.load("/movie/" + params[0] + "/videos");

            return jObj;
        }

        @Override
        protected void onPostExecute(JSONObject jObj) {
            super.onPostExecute(jObj);
            if (jObj != null) {
                List<TrailerData> trailerData = new ArrayList<TrailerData>();
                try {
                    JSONArray array = jObj.getJSONArray("results");
                    for(int i = 0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        trailerData.add(new TrailerData(Uri.parse(TRAILER_BASE_URI + object.getString("key")), object.getString("name")));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "", e);
                }
                populateTrailerList(trailerData);
            }

        }
    }

    private class FetchReviewMovieTask extends AsyncTask<Integer, Void, JSONObject> {
        private static final String TRAILER_BASE_URI = "http://www.youtube.com/watch?v=";

        @Override
        protected JSONObject doInBackground(Integer... params) {
            JSONObject jObj = JSONLoader.load("/movie/" + params[0] + "/reviews");

            return jObj;
        }

        @Override
        protected void onPostExecute(JSONObject jObj) {
            super.onPostExecute(jObj);
            if (jObj != null) {
                List<ReviewData> reviewData  = new ArrayList<ReviewData>();
                try {
                    JSONArray array = jObj.getJSONArray("results");
                    for(int i = 0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        reviewData.add(new ReviewData(object.getString("author"), object.getString("content")));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "", e);
                }
                populateReviewList(reviewData);
            }

        }
    }
}
