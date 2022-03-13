package ru.pronin.tradeBot.strategies;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.indicators.Indicator;

import java.util.List;

public interface Strategy {
    void addCandle(CustomCandle candle);
    List<Indicator> getIndicators();
    boolean isTimeToBuy();
    boolean isTimeToSell();
}
