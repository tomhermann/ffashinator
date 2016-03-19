package com.zombietank.bender.bartender;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.inject.Inject;

import java.io.IOException;
import java.util.Collections;

import io.particle.android.sdk.cloud.SparkCloud;
import io.particle.android.sdk.cloud.SparkCloudException;
import io.particle.android.sdk.cloud.SparkDevice;
import io.particle.android.sdk.utils.Async;
import roboguice.fragment.RoboFragment;

public class Bartender extends RoboFragment {
    public static final String TAG = Bartender.class.getName();

    @Inject
    private SharedPreferences preferences;
    private BartenderListener listener;
    private boolean pouring = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (BartenderListener) context;
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }

    public boolean isPouring() {
        return pouring;
    }

    public void pour(final DrinkConfiguration drink) {
        pouring = true;
        listener.pouring();

        Async.executeAsync(SparkCloud.get(getContext()), new Async.ApiWork<SparkCloud, PourInformation>() {
            @Override
            public PourInformation callApi(SparkCloud sparkCloud) throws SparkCloudException, IOException {
                sparkCloud.logIn(preferences.getString("email_address", ""), preferences.getString("password", ""));
                SparkDevice device = sparkCloud.getDevice(preferences.getString("device_id", ""));
                try {
                    Log.i(TAG, "Go for: " + drink);
                    device.callFunction("pourDrink", Collections.singletonList(drink.buildCommand()));
                } catch (SparkDevice.FunctionDoesNotExistException e) {
                    throw new SparkCloudException(e);
                }

                return new PourInformation(drink.getPourDurationInMilliseconds(0),
                        drink.getPourDurationInMilliseconds(1),
                        drink.getPourDurationInMilliseconds(2));
            }

            @Override
            public void onSuccess(PourInformation pourInformation) {
                Log.i(TAG, "Finished Pouring!");
                pouring = false;
                listener.pourRequestComplete(pourInformation);
            }

            @Override
            public void onFailure(SparkCloudException exception) {
                Log.e(TAG, "Failed to pour", exception);
                pouring = false;
                listener.pourFailed(exception);
            }
        });
    }
}
