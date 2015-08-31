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

/**
 * Created by olgakuklina on 2015-08-25.
 */
public class ImageAdapter extends BaseAdapter {

    private static final String TAG = ImageAdapter.class.getSimpleName();
    private final ArrayList<MovieData> finalMoviePosters = new ArrayList<>();

    private final Context mContext;
    public ImageAdapter(Context c) {
        mContext = c;
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
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            float density = mContext.getResources().getDisplayMetrics().density;
            imageView.setLayoutParams(new GridView.LayoutParams((int)(185 * density), (int)(278 * density)));
            Log.v(TAG, "density = " + density);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        //imageView.setImageResource(R.drawable.z);
        Picasso pic = Picasso.with(mContext);
//        pic.setLoggingEnabled(true);
//        pic.setIndicatorsEnabled(true);
        pic.load(finalMoviePosters.get(position).getMoviePoster())
                .into(imageView);
        return imageView;
    }

    public void add(MovieData res) {
        finalMoviePosters.add(res);
    }
}
