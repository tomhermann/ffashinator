package com.zombietank.bender.bartender;

public class PourInformation {

    private final long valveOneDuration;
    private final long valveTwoDuration;
    private final long valveThreeDuration;

    public PourInformation(long valveOneDuration, long valveTwoDuration, long valveThreeDuration) {
        this.valveOneDuration = valveOneDuration;
        this.valveTwoDuration = valveTwoDuration;
        this.valveThreeDuration = valveThreeDuration;
    }

    public long getValveOneDuration() {
        return valveOneDuration;
    }

    public long getValveTwoDuration() {
        return valveTwoDuration;
    }

    public long getValveThreeDuration() {
        return valveThreeDuration;
    }
}
