package ru.pronin.tradeBot.brokerAPI.exceptions;

import java.util.function.Supplier;

public class NoMarketInstrumentException extends Exception {
    public NoMarketInstrumentException() {
        super("No marketInstrument found");
    }
}
