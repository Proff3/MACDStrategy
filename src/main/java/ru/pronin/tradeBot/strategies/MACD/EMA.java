package ru.pronin.tradeBot.strategies.MACD;

import ru.tinkoff.invest.openapi.model.rest.Candle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EMA {
    private final Logger LOGGER = Logger.getLogger(String.valueOf(EMA.class));
    private final List<Candle> listOfCandles = new ArrayList<>();
    private BigDecimal currentValue = BigDecimal.ZERO.setScale(5,RoundingMode.HALF_UP);
    private final int timePeriod;
    private final BigDecimal alpha;

    public EMA(int timePeriod) {
        this.timePeriod = timePeriod;
        alpha = BigDecimal.valueOf(2.0 / (timePeriod + 1));
    }

    public void addCandle(Candle candle){
        listOfCandles.add(candle);
        if(timePeriod == listOfCandles.size()){
            Double currentValueInDoble = listOfCandles
                    .stream()
                    .map(currentCandle -> currentCandle.getC().doubleValue())
                    .collect(Collectors.averagingDouble(currentCandleDouble -> currentCandleDouble));
            currentValue = BigDecimal.valueOf(currentValueInDoble);
        } else {
            BigDecimal alphaValue = candle.getC().multiply(alpha);
            BigDecimal candleValue = currentValue.multiply(BigDecimal.ONE.subtract(alpha));
            currentValue = alphaValue.add(candleValue);
        }
        LOGGER.info(currentValue.setScale(2, RoundingMode.HALF_UP) + " " + candle.getTime().toString());
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }
}
