package ru.pronin.tradeBot.brokerAPI.exceptions;

public class EventHandlerNotInitializeException extends Exception {
    public EventHandlerNotInitializeException() {
        super("Event handler has not been initialized");
    }
}