package com.example.android.myappportfolio;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesUniversalActivityFragment extends Fragment {

    private static final String TAG = PopularMoviesUniversalActivityFragment.class.getSimpleName();
    private static final String SHARED_PREF_NAME = "com.example.android.myappportfolio.popular.movies";
    private ImageAdapter adapter;
    private String sortOrder;
    private GridView gridview;

    public PopularMoviesUniversalActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this + ": onCreate()");
        adapter = new ImageAdapter(getActivity());
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies_universal, container, false);

        gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                int movieId = (int) adapter.getItemId(position);
                Intent intent = new Intent(getActivity(), DetailsViewUniversalActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movieId);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = getActivity().getSharedPreferences(SHARED_PREF_NAME, 0);
        String sortOrderUpdate;
        sortOrderUpdate = prefs.getString("pref_sorting", getString(R.string.pref_sort_default));
        Log.d(TAG, "sortOrderUpdate = " + sortOrderUpdate + ", sortOrder = " + sortOrder);

        boolean isConnected = checkInternetConnection();
        Log.v(TAG, "Network is" + isConnected);
        if (!isConnected && !sortOrderUpdate.equals("favorites")) {
            Log.e(TAG, "Network is not available");

            Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.network_not_available_message, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        if (!sortOrderUpdate.equals(sortOrder) || sortOrderUpdate.equals("favorites")) {
            sortOrder = sortOrderUpdate;
            adapter.clearData();
            if (sortOrderUpdate.equals("favorites")) {
                FetchFavoriteMovieTask task = new FetchFavoriteMovieTask(adapter, getActivity().getContentResolver());
                task.execute();
            }
        }
        if (!sortOrderUpdate.equals("favorites")) {
            gridview.setOnScrollListener(new PopularMovieViewScrollListener());
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

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
            Toast.makeText(getActivity(), R.string.popular_movies_activity_text_conection_error, Toast.LENGTH_LONG).show();
        }
    }
}
