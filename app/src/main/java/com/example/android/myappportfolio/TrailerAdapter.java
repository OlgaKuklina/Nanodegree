package com.example.android.myappportfolio;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by olgakuklina on 2015-09-07.
 */
public class TrailerAdapter extends BaseAdapter {
    private final List<TrailerData> trailerData;
    private final Activity activity;

    public TrailerAdapter(List<TrailerData> trailerData, Activity activity) {
        this.trailerData = trailerData;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return trailerData.size();
    }

    @Override
    public Object getItem(int position) {
        return trailerData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view =  activity.getLayoutInflater().inflate(R.layout.movie_trailer_list_item, null);
        } else {
            view = convertView;
        }
        TextView tralerName = (TextView) view.findViewById(R.id.trailer_name);
        tralerName.setText(trailerData.get(position).getTrailerName());

        return view;
    }
}
