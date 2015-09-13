package com.example.android.myappportfolio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button popularMovies = (Button) findViewById(R.id.popular_movies);
        Button scoresApp = (Button) findViewById(R.id.scores_app);
        Button libraryApp = (Button) findViewById(R.id.library_app);
        Button buildItBigger = (Button) findViewById(R.id.build_it_bigger);
        Button baconReader = (Button) findViewById(R.id.bacon_reader);
        Button capstoneMyownApp = (Button) findViewById(R.id.capstone_my_own_app);

        popularMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopularMoviesUniversalActivity.class);
                startActivity(intent);
            }
        });
        scoresApp.setOnClickListener(new ButtonOnClickListner(scoresApp.getText()));
        libraryApp.setOnClickListener(new ButtonOnClickListner(libraryApp.getText()));
        buildItBigger.setOnClickListener(new ButtonOnClickListner(buildItBigger.getText()));
        baconReader.setOnClickListener(new ButtonOnClickListner(baconReader.getText()));
        capstoneMyownApp.setOnClickListener(new ButtonOnClickListner(capstoneMyownApp.getText()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement`
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ButtonOnClickListner implements View.OnClickListener {
        private final String toastString;

        private ButtonOnClickListner(CharSequence buttonName) {
            StringBuilder builder = new StringBuilder()
                    .append("This button will lounch my ")
                    .append(buttonName)
                    .append(" app!");
            toastString = builder.toString();
        }

        public void onClick(View v) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastString, duration);
            toast.show();
        }
    }
}
