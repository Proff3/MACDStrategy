package ru.pronin.tradeBot.strategies.MACD;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.exceptions.WrongStrategyInitializationException;
import ru.pronin.tradeBot.strategies.Strategy;

import javax.crypto.Mac;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class MACD implements Strategy {

    private boolean isOver = false;
    private final EMA fastEMA;
    private final EMA slowEMA;
    private final EMA signalEMA;
    private BigDecimal MACD = BigDecimal.valueOf(1_000_000_000);
    private final String figi;
    private final Logger LOGGER = Logger.getLogger(MACD.getClass().toString());

    /**
     * fastEMADepth = 8
     * slowEMADepth = 17
     * signalEMADepth = 9
     */
    public MACD(String figi) {
        fastEMA = new EMA(8);
        slowEMA = new EMA(17);
        signalEMA = new EMA(9);
        this.figi = figi;
    }

    /**
     * @param fastEMA - recommended for buy - 8 and for sale - 12
     * @param slowEMA - recommended for buy - 17 and for sale - 26
     * @param signalEMA - recommended - 9
     */
    public MACD(EMA fastEMA, EMA slowEMA, EMA signalEMA, String figi) throws WrongStrategyInitializationException {
        if (fastEMA.getDepth() >= slowEMA.getDepth()
                || signalEMA.getDepth() >= slowEMA.getDepth()
                || signalEMA.getDepth() <= slowEMA.getDepth()) throw new WrongStrategyInitializationException();
        this.fastEMA = fastEMA;
        this.slowEMA = slowEMA;
        this.signalEMA = signalEMA;
        this.figi = figi;
    }

    @Override
    public void addCandle(CustomCandle candle){
        System.out.println(signalEMA.getCurrentValue() + " signal " + MACD + " MACD");
        fastEMA.addCandle(candle);
        slowEMA.addCandle(candle);
        MACD = fastEMA.getCurrentValue().subtract(slowEMA.getCurrentValue());
        if (!fastEMA.isEMAFullfilled()) return;
        CustomCandle MACDCandle = new CustomCandle(
                candle.getFigi(),
                candle.getInterval(),
                candle.getO(),
                MACD,
                candle.getH(),
                candle.getL(),
                candle.getV(),
                candle.getTime().toOffsetDateTime()
        );
        signalEMA.addCandle(MACDCandle);
    }

    @Override
    public Boolean isTimeToBuy(){
        return MACD.compareTo(signalEMA.getCurrentValue()) > 0;
    }

    @Override
    public Boolean isTimeToSell() {
        if (getSignalValue().compareTo(MACD) >= 0) return true;
        BigDecimal calculatedValue = getDifferenceInPercents().subtract(new BigDecimal(5)); // It`s a damper
        return BigDecimal.ZERO.compareTo(calculatedValue) >= 0;
    }

    @Override
    public Boolean isEnoughInformation() {
        return fastEMA.isEMAFullfilled() && slowEMA.isEMAFullfilled() && signalEMA.isEMAFullfilled();
    }

    @Override
    public Boolean isOver() {
        return isOver;
    }

    @Override
    public int getStrategyDepth() {
        return (slowEMA.getDepth() + fastEMA.getDepth()) / 2;
    }

    @Override
    public void setOver() {
        isOver = true;
    }

    @Override
    public String getFigi() {
        return figi;
    }

    public BigDecimal getSignalValue(){ return signalEMA.getCurrentValue(); }

    public BigDecimal getDifference() {
        return MACD.subtract(signalEMA.getCurrentValue()).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getDifferenceInPercents() {
        return MACD
                .subtract(signalEMA.getCurrentValue())
                .divide(MACD, 5, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
    }

    public EMA getSignalEMA() {
        return signalEMA;
    }

    public BigDecimal getMACD() {
        return MACD;
    }

}
