package com.example.android.myappportfolio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class PopularMoviesActivity extends Activity {
    private static final String TAG = PopularMoviesActivity.class.getSimpleName();
    private static final String SHARED_PREF_NAME = "com.example.android.myappportfolio.popular.movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Log.d(TAG, "Hi");
        GridView gridview = (GridView) findViewById(R.id.gridview);
        final ImageAdapter adapter = new ImageAdapter(this);
        gridview.setAdapter(adapter);

        SharedPreferences prefs = this.getSharedPreferences(SHARED_PREF_NAME, 0);
        String sortOrder = prefs.getString("pref_sorting", getString(R.string.pref_sort_default));
        Log.d(TAG, "sortOrder = " + sortOrder);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                int movieId = (int) adapter.getItemId(position);
                Intent intent = new Intent(PopularMoviesActivity.this, MovieDetailsViewActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movieId);
                startActivity(intent);
            }
        });

        FetchMovieTask fetchMovieTask = new FetchMovieTask(adapter, sortOrder);
        fetchMovieTask.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_movies, menu);
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
            Intent intent = new Intent(this, PopularMoviesSettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
