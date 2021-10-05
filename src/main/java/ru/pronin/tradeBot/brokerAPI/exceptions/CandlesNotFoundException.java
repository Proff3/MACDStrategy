package ru.pronin.tradeBot.brokerAPI.exceptions;

public class CandlesNotFoundException extends Exception{
    public CandlesNotFoundException() {
        super("Candles not found");
    }
}
