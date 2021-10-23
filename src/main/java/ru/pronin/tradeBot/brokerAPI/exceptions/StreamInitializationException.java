package ru.pronin.tradeBot.brokerAPI.exceptions;

public class StreamInitializationException extends Exception{
    public StreamInitializationException() {
        super("Stream has been initialized");
    }
}
