package ru.pronin.tradeBot.strategies;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;

import java.util.function.Function;

public interface Strategy {
    void addCandle(CustomCandle candle);
    Boolean isTimeToBuy();
    Boolean isTimeToSell();
}
