package ru.pronin.tradeBot.strategies.manager;

import ru.pronin.tradeBot.indicators.StochasticOscillator;

import java.math.BigDecimal;

public class StochasticManager implements IndicatorManager<StochasticOscillator> {

    private final IndicatorName indicatorName = IndicatorName.STOCHASTIC_OSCILLATOR;

    @Override
    public boolean isTimeToBuy(StochasticOscillator indicator) {
        BigDecimal value = indicator.getValue();
        BigDecimal emaValue = indicator.getEmaValue();
        return false;
    }

    @Override
    public boolean isTimeToSell(StochasticOscillator indicator) {
        return false;
    }
}
