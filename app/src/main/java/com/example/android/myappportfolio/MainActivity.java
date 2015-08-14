package com.example.android.myappportfolio;

import android.app.Activity;
import android.content.Context;
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
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);

        button0.setOnClickListener(new ButtonOnClickListner(button0.getText()));
        button1.setOnClickListener(new ButtonOnClickListner(button1.getText()));
        button2.setOnClickListener(new ButtonOnClickListner(button2.getText()));
        button3.setOnClickListener(new ButtonOnClickListner(button3.getText()));
        button4.setOnClickListener(new ButtonOnClickListner(button4.getText()));
        button5.setOnClickListener(new ButtonOnClickListner(button5.getText()));

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ButtonOnClickListner implements View.OnClickListener {
        private final String toastString;

        private ButtonOnClickListner(CharSequence buttonName){
            StringBuilder builder = new StringBuilder()
                    .append("This button will lounch my ")
                    .append(buttonName)
                    .append(" app!");
            toastString =  builder.toString();
        }

        public void onClick(View v) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastString, duration);
            toast.show();
        }
    }
}
