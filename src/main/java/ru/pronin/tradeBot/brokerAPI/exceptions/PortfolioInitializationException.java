package ru.pronin.tradeBot.brokerAPI.exceptions;

public class PortfolioInitializationException extends Exception{
    public PortfolioInitializationException() {
        super("Portfolio context has not been initialized");
    }
}
