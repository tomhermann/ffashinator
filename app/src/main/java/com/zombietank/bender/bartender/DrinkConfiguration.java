package com.zombietank.bender.bartender;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DrinkConfiguration {
    private final Map<Integer, Integer> valvePours = new LinkedHashMap<>(3);

    public void setValvePourAmount(int valve, int shots) {
        valvePours.put(valve, shots);
    }

    public Map<Integer, Integer> getPoursPerValve() {
        return Collections.unmodifiableMap(valvePours);
    }

    public int getTotalShotCount() {
        int shotCount = 0;
        for (Integer value : valvePours.values()) {
            shotCount += value;
        }
        return shotCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DrinkConfiguration{[");
        for (Map.Entry<Integer, Integer> entry : valvePours.entrySet()) {
            sb.append("valve: ").append(entry.getKey()).append(", shots: ").append(entry.getValue()).append("; ");
        }
        sb.append("]}");
        return sb.toString();
    }
}
