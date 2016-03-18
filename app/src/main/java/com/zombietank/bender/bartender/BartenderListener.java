package com.zombietank.bender.bartender;

import io.particle.android.sdk.cloud.SparkCloudException;

public interface BartenderListener {
    void pouring();
    void pourComplete(PourInformation pourInformation);
    void pourFailed(SparkCloudException cause);
}
