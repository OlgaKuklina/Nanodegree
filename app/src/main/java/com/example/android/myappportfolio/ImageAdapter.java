package com.example.android.myappportfolio;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olgakuklina on 2015-08-25.
 */
public class ImageAdapter extends BaseAdapter {

    private static final String TAG = ImageAdapter.class.getSimpleName();
    private static final int IMAGE_WIDTH = 185;
    private static final int IMAGE_HEIGHT = 278;
    private final ArrayList<MovieData> finalMoviePosters = new ArrayList<>();
    private final float density;

    private final Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
        density = mContext.getResources().getDisplayMetrics().density;
    }

    public int getCount() {
        return finalMoviePosters.size();
    }

    public Object getItem(int position) {
        return finalMoviePosters.get(position);
    }

    public long getItemId(int position) {
        return finalMoviePosters.get(position).getMovieId();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "position = " + position);
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams((int) (IMAGE_WIDTH * density), (int) (IMAGE_HEIGHT * density)));
            Log.v(TAG, "density = " + density);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso pic = Picasso.with(mContext);
        pic.load(finalMoviePosters.get(position).getMoviePoster())
                .error(R.drawable.no_movies)
                .into(imageView);
        return imageView;
    }

    public void add(MovieData res) {
        finalMoviePosters.add(res);
    }

    public void addAll(List<MovieData> res) {
        finalMoviePosters.addAll(res);
    }

    public void clearData() {
        finalMoviePosters.clear();
        notifyDataSetChanged();
    }
    public List<MovieData> getMovieData() {
        return finalMoviePosters;
    }
}
