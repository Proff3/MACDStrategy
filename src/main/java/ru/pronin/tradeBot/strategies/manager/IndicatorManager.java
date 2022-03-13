package ru.pronin.tradeBot.strategies.manager;

import ru.pronin.tradeBot.indicators.Indicator;

public interface IndicatorManager<T extends Indicator> {
     boolean isTimeToBuy(T indicator);
     boolean isTimeToSell(T indicator);
}
