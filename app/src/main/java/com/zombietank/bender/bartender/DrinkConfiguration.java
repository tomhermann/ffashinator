package com.zombietank.bender.bartender;

import java.util.LinkedHashMap;
import java.util.Map;

public class DrinkConfiguration {
    private final Map<Integer, Integer> valvePours = new LinkedHashMap<>(3);

    public void setValvePourAmount(int valve, int shots) {
        valvePours.put(valve, shots);
    }

    public long getPourDurationInMilliseconds(int valve) {
        return valvePours.get(valve) * 1500;
    }

    public String buildCommand() {
        String result = "";
        for (int i = 0; i < 3; i++) {
            Integer shotCount = valvePours.get(i);
            result += (shotCount == null ? "0" : shotCount);
        }
        return result;
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
