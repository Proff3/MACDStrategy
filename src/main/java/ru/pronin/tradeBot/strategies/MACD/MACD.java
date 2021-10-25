package ru.pronin.tradeBot.strategies.MACD;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.strategies.Strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MACD implements Strategy {

    private final EMA fastEMA;
    private final EMA slowEMA;
    private final EMA signalEMA;
    private BigDecimal MACD = BigDecimal.valueOf(1_000_000_000);
    private BigDecimal difference = BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP);
    private BigDecimal differenceInPercents = BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP);
    private final String figi;

    /**
     * @param fastEMADepth - recommended for buy - 8 and for sale - 12
     * @param slowEMADepth - recommended for buy - 17 and for sale - 26
     * @param signalEMADepth - recommended - 9
     */
    public MACD(int fastEMADepth, int slowEMADepth, int signalEMADepth, String figi) {
        fastEMA = new EMA(fastEMADepth);
        slowEMA = new EMA(slowEMADepth);
        signalEMA = new EMA(signalEMADepth);
        this.figi = figi;
    }

    @Override
    public void addCandle(CustomCandle candle){
        fastEMA.addCandle(candle);
        slowEMA.addCandle(candle);
        MACD = fastEMA.getCurrentValue().subtract(slowEMA.getCurrentValue());
        candle.setC(MACD);
        signalEMA.addCandle(candle);
        difference = MACD.subtract(signalEMA.getCurrentValue());
        differenceInPercents = MACD
                .subtract(signalEMA.getCurrentValue())
                .divide(candle.getC(), 5, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }

    @Override
    public Boolean isTimeToBuy(){
        return MACD.compareTo(signalEMA.getCurrentValue()) > 0;
    }

    @Override
    public Boolean isTimeToSell() {
        return getDifferenceInPercents().compareTo(new BigDecimal(5)) < 0;
    }

    //write method
    @Override
    public Boolean isOver() {
        return false;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public BigDecimal getDifferenceInPercents() {
        return differenceInPercents;
    }

    public String getFigi() {
        return figi;
    }
}
