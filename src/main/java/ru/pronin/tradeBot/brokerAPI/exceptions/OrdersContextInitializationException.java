package ru.pronin.tradeBot.brokerAPI.exceptions;

public class OrdersContextInitializationException extends Exception{
    public OrdersContextInitializationException() {
        super("Orders context has been initialized");
    }
}
