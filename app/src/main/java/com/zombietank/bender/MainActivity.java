package com.zombietank.bender;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.inject.Inject;
import com.zombietank.bender.bartender.Bartender;
import com.zombietank.bender.bartender.BartenderListener;
import com.zombietank.bender.bartender.DrinkConfiguration;
import com.zombietank.bender.settings.SettingsActivity;

import roboguice.activity.RoboAppCompatActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboAppCompatActivity implements BartenderListener {
    private static final String BARTENDER_TAG = "BARTENDER_TAG";
    private static final int BUTTON_FADE_TIME_MILLIS = 1000;
    @InjectView(R.id.fab)
    private FloatingActionButton dispenseButton;

    @InjectView(R.id.valveOneSeekBar)
    private SeekBar valveOneSeekBar;
    @InjectView(R.id.valveTwoSeekBar)
    private SeekBar valveTwoSeekBar;
    @InjectView(R.id.valveThreeSeekBar)
    private SeekBar valveThreeSeekBar;

    @InjectView(R.id.valveOneLabel)
    private TextView valveOneLabel;
    @InjectView(R.id.valveTwoLabel)
    private TextView valveTwoLabel;
    @InjectView(R.id.valveThreeLabel)
    private TextView valveThreeLabel;


    @InjectView(R.id.valve_one_quantity)
    private TextView valveOneQuantity;
    @InjectView(R.id.valve_two_quantity)
    private TextView valveTwoQuantity;
    @InjectView(R.id.valve_three_quantity)
    private TextView valveThreeQuantity;

    @InjectView(R.id.toolbar)
    private Toolbar toolbar;

    @Inject
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        valveOneSeekBar.setOnSeekBarChangeListener(new DrinkBarListener(valveOneQuantity));
        valveTwoSeekBar.setOnSeekBarChangeListener(new DrinkBarListener(valveTwoQuantity));
        valveThreeSeekBar.setOnSeekBarChangeListener(new DrinkBarListener(valveThreeQuantity));

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(new Bartender(), BARTENDER_TAG).commit();
        }

        dispenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bartender bartender = findBartender();
                bartender.pour(drink());
            }
        });

    }

    private DrinkConfiguration drink() {
        DrinkConfiguration drinkConfiguration = new DrinkConfiguration();
        drinkConfiguration.setValvePourAmount(0, valveOneSeekBar.getProgress());
        drinkConfiguration.setValvePourAmount(1, valveTwoSeekBar.getProgress());
        drinkConfiguration.setValvePourAmount(2, valveThreeSeekBar.getProgress());
        return drinkConfiguration;
    }

    @Override
    protected void onResume() {
        super.onResume();

        setValveLabels();

        Bartender bartender = findBartender();
        if (bartender.isPouring()) {
            pouring();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Bartender findBartender() {
        return (Bartender) getSupportFragmentManager().findFragmentByTag(BARTENDER_TAG);
    }

    @Override
    public void pouring() {
        hideDispenseButton();
        dispenseButton.setEnabled(false);
        Snackbar.make(dispenseButton, "Now Pouring!", Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void pourComplete(boolean success) {
        showDispenseButton();
        if(success) {
            Snackbar.make(dispenseButton, "Pour Complete! Enjoy!", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(dispenseButton, "Failed to pour your drink, sorry.", Snackbar.LENGTH_LONG).show();
        }
        dispenseButton.setEnabled(true);
    }

    private void hideDispenseButton() {
        dispenseButton.setEnabled(false);
        dispenseButton.animate()
                .alpha(0)
                .setDuration(BUTTON_FADE_TIME_MILLIS)
                .start();
    }

    private void showDispenseButton() {
        dispenseButton.animate()
                .alpha(1)
                .setDuration(BUTTON_FADE_TIME_MILLIS)
                .start();
    }

    private void setValveLabels() {
        valveOneLabel.setText(sharedPreferences.getString("valve_one_type", getString(R.string.valve_one_label)));
        valveTwoLabel.setText(sharedPreferences.getString("valve_two_type", getString(R.string.valve_two_label)));
        valveThreeLabel.setText(sharedPreferences.getString("valve_three_type", getString(R.string.valve_three_label)));
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
