package ru.pronin.tradeBot.brokerAPI.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import ru.tinkoff.invest.openapi.model.rest.OperationType;

public enum CustomOperationType {
    BUY("Buy"),
    SELL("Sell");

    private final String value;

    CustomOperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static CustomOperationType fromValue(String text) {
        for (CustomOperationType b : CustomOperationType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
