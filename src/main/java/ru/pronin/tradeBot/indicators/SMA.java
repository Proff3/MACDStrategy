package ru.pronin.tradeBot.indicators;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.indicators.utils.ScalableMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class SMA implements Indicator {

    private final int DEPTH;
    private final ScalableMap<ZonedDateTime, CustomCandle> candleMap;

    private BigDecimal currentValue;
    private boolean isOver;

    public SMA(int depth) {
        DEPTH = depth;
        candleMap = new ScalableMap<>(depth);
    }

    @Override
    public void addCandle(CustomCandle candle) {
        candleMap.addValue(candle.getTime(), candle);
        double currentValueInDouble = candleMap.getValues()
                .stream()
                .collect(Collectors.averagingDouble(c -> c.getC().doubleValue()));
        currentValue = new BigDecimal(currentValueInDouble).setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public Boolean isEnoughInformation() {
        return candleMap.getSize() > DEPTH;
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
