package ru.pronin.tradeBot.indicators;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;

public interface Indicator {
    void addCandle(CustomCandle candle);
    Boolean isEnoughInformation();
    Boolean isOver();
    int getIndicatorDepth();
    void setOver();
}
