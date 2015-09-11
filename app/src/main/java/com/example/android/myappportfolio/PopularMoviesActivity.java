package com.example.android.myappportfolio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class PopularMoviesActivity extends Activity {
    private static final String TAG = PopularMoviesActivity.class.getSimpleName();
    private static final String SHARED_PREF_NAME = "com.example.android.myappportfolio.popular.movies";
    private ImageAdapter adapter;
    private String sortOrder;
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Log.d(TAG, "Hi");
        gridview = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this);
        gridview.setAdapter(adapter);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                int movieId = (int) adapter.getItemId(position);
                Intent intent = new Intent(PopularMoviesActivity.this, MovieDetailsViewActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movieId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = this.getSharedPreferences(SHARED_PREF_NAME, 0);
        String sortOrderUpdate;
        sortOrderUpdate = prefs.getString("pref_sorting", getString(R.string.pref_sort_default));
        Log.d(TAG, "sortOrderUpdate = " + sortOrderUpdate);

        boolean isConnected = checkInternetConnection();
        Log.v(TAG, "Network is" + isConnected);
        if (!isConnected && !sortOrderUpdate.equals("favorites")) {
            Log.e(TAG, "Network is not available");

            Toast toast = Toast.makeText(getApplicationContext(), R.string.network_not_available_message, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        if(!sortOrderUpdate.equals(sortOrder) || sortOrderUpdate.equals("favorites")) {
            sortOrder = sortOrderUpdate;
            adapter.clearData();
            if(sortOrderUpdate.equals("favorites")){
                FetchFavoriteMovieTask task = new FetchFavoriteMovieTask(adapter, getContentResolver());
                task.execute();
            } else {
                gridview.setOnScrollListener(new PopularMovieViewScrollListener());
            }
        }
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

    private boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


    private class PopularMovieViewScrollListener implements AbsListView.OnScrollListener, FetchMovieListener {
        private static final int PAGE_SIZE = 20;
        private boolean loadingState = false;


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {

                if (!loadingState) {
                    FetchMovieTask fetchMovieTask = new FetchMovieTask(adapter, sortOrder, this);
                    fetchMovieTask.execute(totalItemCount / PAGE_SIZE + 1);
                    loadingState = true;

                }
            }

        }


        @Override
        public void onFetchCompleted() {
            loadingState = false;
        }

        @Override
        public void onFetchFailed() {
            loadingState = false;
            Toast.makeText(PopularMoviesActivity.this, R.string.popular_movies_activity_text_conection_error, Toast.LENGTH_LONG).show();
        }
    }
}
