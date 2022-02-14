package ru.pronin.tradeBot.indicators;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.indicators.utils.CandleArray;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StochasticOscillator implements Indicator {

    private final int DEPTH;
    private final EMA EMA;
    private final BigDecimal MULTIPLIER = new BigDecimal(100);

    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal value;
    private final CandleArray candles;
    private boolean isOver = false;

    public StochasticOscillator(int depth,int emaDepth) {
        DEPTH = depth;
        EMA = new EMA(emaDepth);
        candles = new CandleArray(depth);
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
        return candles.getSize() >= DEPTH && EMA.isEnoughInformation();
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
        candles.add(candle);
        updateLimits(candle);
        value = processCandle(candle);
        CustomCandle candleWithCalculatedValue = CustomCandle.getCandleWithNewCloseValue(candle, value);
        EMA.addCandle(candleWithCalculatedValue);
    }

    private void updateLimits(CustomCandle candle) {
        BigDecimal currentMax = candles.getCandles().stream().map(CustomCandle::getH).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal currentMin = candles.getCandles().stream().map(CustomCandle::getL).min(BigDecimal::compareTo).orElse(new BigDecimal(1_000_000));
        max = candle.getH().compareTo(currentMax) > 0 ? candle.getH() : currentMax;
        min = candle.getL().compareTo(currentMin) < 0 ? candle.getL() : currentMin;
    }

    private BigDecimal processCandle(CustomCandle candle) {
        BigDecimal numerator = candle.getC().subtract(min);
        BigDecimal denominator = max.compareTo(min) == 0 ? BigDecimal.ONE : max.subtract(min);
        return MULTIPLIER.multiply(numerator.divide(denominator, 4, RoundingMode.HALF_UP));
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getEmaValue() {
        return EMA.getCurrentValue();
    }
}
