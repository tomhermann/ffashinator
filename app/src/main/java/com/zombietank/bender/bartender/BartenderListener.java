package com.zombietank.bender.bartender;

import io.particle.android.sdk.cloud.SparkCloudException;

public interface BartenderListener {
    void pouring();
    void pourRequestComplete(PourInformation pourInformation);
    void pourFailed(SparkCloudException cause);
}
