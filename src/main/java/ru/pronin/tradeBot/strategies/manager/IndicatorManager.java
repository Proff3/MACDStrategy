package ru.pronin.tradeBot.strategies.manager;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.indicators.Indicator;

public interface IndicatorManager<T extends Indicator> {
     boolean isTimeToBuy(T indicator, CustomCandle candle);
     boolean isTimeToSell(T indicator, CustomCandle candle);
     void processIndicatorValues(T indicator);
     IndicatorName getName();
}
