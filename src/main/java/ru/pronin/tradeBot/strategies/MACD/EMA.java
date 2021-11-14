package ru.pronin.tradeBot.strategies.MACD;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.tinkoff.invest.openapi.model.rest.Candle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EMA {
    private final Logger LOGGER = Logger.getLogger(String.valueOf(EMA.class));
    private final List<CustomCandle> listOfCandles = new ArrayList<>();
    private BigDecimal currentValue = BigDecimal.ZERO.setScale(5,RoundingMode.HALF_UP);
    private BigDecimal previousValue = BigDecimal.ZERO.setScale(5,RoundingMode.HALF_UP);
    private final int timePeriod;
    private final int depth;
    private final BigDecimal alpha;

    public EMA(int timePeriod) {
        this.timePeriod = timePeriod;
        alpha = BigDecimal.valueOf(2.0 / (timePeriod + 1));
        depth = timePeriod * 5;
    }

    public void addCandle(CustomCandle currentCandle){
        if (listOfCandles.isEmpty()) {
            currentValue = currentCandle.getC();
            listOfCandles.add(currentCandle);
            return;
        }
        CustomCandle lastCandle = listOfCandles.get(listOfCandles.size() - 1);
        if (lastCandle.getTime().isEqual(currentCandle.getTime())) {
            listOfCandles.remove(lastCandle);
            currentValue = previousValue;
        } else {
            previousValue = currentValue;
        }
        listOfCandles.add(currentCandle);
        if(timePeriod >= listOfCandles.size()){
            Double currentValueInDouble = listOfCandles
                    .stream()
                    .map(candle -> candle.getC().doubleValue())
                    .collect(Collectors.averagingDouble(currentCandleDouble -> currentCandleDouble));
            currentValue = BigDecimal.valueOf(currentValueInDouble);
            previousValue = currentValue;
        } else {
            BigDecimal alphaValue = currentCandle.getC().multiply(alpha).setScale(5,RoundingMode.HALF_UP);
            BigDecimal candleValue = currentValue.multiply(BigDecimal.ONE.subtract(alpha)).setScale(5,RoundingMode.HALF_UP);
            currentValue = alphaValue.add(candleValue);
        }
        if (listOfCandles.size() >= depth) listOfCandles.remove(0);
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public Boolean isEMAFullfilled(){
        return listOfCandles.size() > timePeriod;
    }

    public int getDepth() {
        return depth;
    }
}
