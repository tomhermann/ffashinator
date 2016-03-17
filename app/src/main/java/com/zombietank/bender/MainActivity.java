package com.zombietank.bender;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import bender.zombietank.com.ffashinator.R;
import roboguice.activity.RoboActivity;
import roboguice.activity.RoboAppCompatActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboAppCompatActivity {
    @InjectView(R.id.fab)
    private FloatingActionButton floatingDrinkingButton;

    @InjectView(R.id.drinkOneSeekBar)
    private SeekBar drinkOneSeekBar;
    @InjectView(R.id.drinkTwoSeekBar)
    private SeekBar drinkTwoSeekBar;
    @InjectView(R.id.drinkThreeSeekBar)
    private SeekBar drinkThreeSeekBar;

    @InjectView(R.id.drinkOneLabel)
    private TextView drinkOneLabel;
    @InjectView(R.id.drinkTwoLabel)
    private TextView drinkTwoLabel;
    @InjectView(R.id.drinkThreeLabel)
    private TextView drinkThreeLabel;

    @InjectView(R.id.toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        floatingDrinkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drinkOneSeekBar.setOnSeekBarChangeListener(new DrinkBarListener(drinkOneLabel));
        drinkTwoSeekBar.setOnSeekBarChangeListener(new DrinkBarListener(drinkTwoLabel));
        drinkThreeSeekBar.setOnSeekBarChangeListener(new DrinkBarListener(drinkThreeLabel));
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class DrinkBarListener implements SeekBar.OnSeekBarChangeListener {
        private final TextView drinkBarLabel;

        public DrinkBarListener(TextView drinkBarLabel) {
            this.drinkBarLabel = drinkBarLabel;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            drinkBarLabel.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
