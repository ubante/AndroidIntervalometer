package com.ubante.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ubante.intervalometer.models.Movie;
import com.ubante.intervalometer.models.Output;
import com.ubante.intervalometer.models.Reality;
import com.ubante.intervalometer.models.Settings;

public class MainActivity extends ActionBarActivity {

    public EditText etDelay, etNumberOfFrames, etShutterSpeed;
    public TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        etDelay = (EditText) findViewById(R.id.etDelay);
        etNumberOfFrames = (EditText) findViewById(R.id.etNumberOfFrames);
        etShutterSpeed = (EditText) findViewById(R.id.etShutterSpeed);
        tvOutput = (TextView) findViewById(R.id.tvOutput);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCompute(View v) {
        // what happens when the Compute button is clicked
        String delayString = etDelay.getText().toString();
        String numberOfFrames = etNumberOfFrames.getText().toString();
        String shutterSpeed = etShutterSpeed.getText().toString();

        Settings settings = new Settings(delayString,numberOfFrames,shutterSpeed);
        Movie movie = new Movie(settings);
        Reality reality = new Reality(settings);
        Output output = new Output(settings,movie,reality);
        String longOutput = output.getOutput();
        String simpleOutput = String.format("You gave %s delay for %s frames with %s shutter speed.",delayString,numberOfFrames,shutterSpeed);
        String combinedOutput = String.format("%s\n%s\n",simpleOutput,longOutput);
        tvOutput.setText(longOutput);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
