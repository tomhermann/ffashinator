package com.zombietank.bender.bartender;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DrinkConfigurationTest {

    private DrinkConfiguration drink;

    @Before
    public void setUp() throws Exception {
        drink = new DrinkConfiguration();
    }

    @Test
    public void buildCommand() throws Exception {
        assertEquals("000", drink.buildCommand());
        drink.setValvePourAmount(0, 1);
        assertEquals("100", drink.buildCommand());
        drink.setValvePourAmount(1, 2);
        assertEquals("120", drink.buildCommand());
        drink.setValvePourAmount(2, 5);
        assertEquals("125", drink.buildCommand());
    }
}
