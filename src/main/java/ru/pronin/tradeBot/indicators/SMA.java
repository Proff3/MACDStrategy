package ru.pronin.tradeBot.indicators;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.indicators.utils.CandleArray;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

public class SMA implements Indicator {

    private final int DEPTH;
    private final CandleArray candles;

    private BigDecimal currentValue;
    private boolean isOver;

    public SMA(int depth) {
        DEPTH = depth;
        candles = new CandleArray(depth);
    }

    @Override
    public void addCandle(CustomCandle candle) {
        candles.add(candle);
        double currentValueInDouble = candles.getCandles()
                .stream()
                .collect(Collectors.averagingDouble(c -> c.getC().doubleValue()));
        currentValue = new BigDecimal(currentValueInDouble).setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public Boolean isEnoughInformation() {
        return candles.getSize() > DEPTH;
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

    public BigDecimal getCurrentValue() {
        return currentValue;
    }
}
