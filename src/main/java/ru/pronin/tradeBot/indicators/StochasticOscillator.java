package ru.pronin.tradeBot.indicators;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class StochasticOscillator implements Indicator {

    private final int DEPTH;
    private final EMA EMA;
    private final BigDecimal MULTIPLIER = new BigDecimal(100);

    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal value;
    private final List<CustomCandle> candles = new ArrayList<>();
    private boolean isOver = false;

    public StochasticOscillator(int depth,int emaDepth) {
        DEPTH = depth;
        EMA = new EMA(emaDepth);
    }

    @Override
    public void addCandle(CustomCandle candle) {
        if(max == null || min == null) {
            updateLimits(candle);
            return;
        }
        calculate(candle);
    }

    @Override
    public Boolean isEnoughInformation() {
        return candles.size() >= DEPTH && EMA.isEnoughInformation();
    }

    @Override
    public Boolean isOver() {
        return isOver;
    }

    @Override
    public int getIndicatorDepth() {
        return DEPTH;
    }

    @Override
    public void setOver() {
        isOver = true;
    }

    private void calculate(CustomCandle candle) {
        updateLimits(candle);
        value = processCandle(candle);
        CustomCandle candleWithCalculatedValue = CustomCandle.getCandleWithNewCloseValue(candle, value);
        EMA.addCandle(candleWithCalculatedValue);
    }

    private void updateLimits(CustomCandle candle) {
        if(candles.size() > DEPTH) candles.remove(0);
        BigDecimal currentMax = candles.stream().map(CustomCandle::getC).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal currentMin = candles.stream().map(CustomCandle::getC).min(BigDecimal::compareTo).orElse(new BigDecimal(1_000_000));
        max = candle.getC().compareTo(currentMax) > 0 ? candle.getC() : currentMax;
        min = candle.getC().compareTo(currentMin) < 0 ? candle.getC() : currentMin;
        candles.add(candle);
    }

    private BigDecimal processCandle(CustomCandle candle) {
        BigDecimal numerator = candle.getC().subtract(min);
        BigDecimal denominator = max.compareTo(min) == 0 ? BigDecimal.ONE : max.subtract(min);
        return MULTIPLIER.multiply(numerator.divide(denominator, RoundingMode.HALF_UP));
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getEmaValue() {
        return EMA.getCurrentValue();
    }
}
