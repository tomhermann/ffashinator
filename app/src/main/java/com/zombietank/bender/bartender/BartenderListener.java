package com.zombietank.bender.bartender;

public interface BartenderListener {
    void pouring();
    void pourComplete(boolean success);
}
