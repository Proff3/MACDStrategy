package ru.pronin.tradeBot.brokerAPI.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CandleResolution {
    _1MIN("1min"),
    _2MIN("2min"),
    _3MIN("3min"),
    _5MIN("5min"),
    _10MIN("10min"),
    _15MIN("15min"),
    _30MIN("30min"),
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month");

    private final String value;

    CandleResolution(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CandleResolution fromValue(String text) {
        for (CandleResolution b : CandleResolution.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
