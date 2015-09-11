package com.example.android.myappportfolio;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by olgakuklina on 2015-08-30.
 */
public class JSONLoader {
    private static final String APP_KEY = "your app key";
    private static final String MOVIE_BASE_URI = "http://api.themoviedb.org/3";
    private static final String TAG = JSONLoader.class.getSimpleName();

    public static JSONObject load(String relativeUri) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieJsonStr = null;
        try {
            Uri builtUri = Uri.parse(MOVIE_BASE_URI + relativeUri).buildUpon()
                    .appendQueryParameter("api_key", APP_KEY).build();
            URL url = new URL(builtUri.toString());
            Log.v(TAG, "Built URI " + builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.v(TAG, "Movie JSON String: " + movieJsonStr);
            JSONObject jObj = new JSONObject(movieJsonStr);
            return jObj;

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

    }
}
