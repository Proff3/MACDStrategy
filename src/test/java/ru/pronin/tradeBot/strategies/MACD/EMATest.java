package ru.pronin.tradeBot.strategies.MACD;

import org.junit.Assert;
import org.junit.Test;
import ru.tinkoff.invest.openapi.model.rest.Candle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class EMATest {

    Function<BigDecimal, Candle> createCandle = closeValue -> {
        Candle candle = new Candle();
        candle.setC(closeValue);
        return candle;
    };

    @Test
    public void getCurrentValueByAveragingCandles(){
        EMA ema = new EMA(3);
        ema.addCandle(createCandle.apply(BigDecimal.ONE));
        ema.addCandle(createCandle.apply(BigDecimal.TEN));
        ema.addCandle(createCandle.apply(BigDecimal.ONE));
        Assert.assertEquals(BigDecimal.valueOf(4).setScale(1, RoundingMode.HALF_UP), ema.getCurrentValue());
    }

    @Test
    public void getCurrentValueByEMAStrategy(){
        EMA ema = new EMA(4);
        ema.addCandle(createCandle.apply(BigDecimal.valueOf(5.3)));
        ema.addCandle(createCandle.apply(BigDecimal.valueOf(6.7)));
        ema.addCandle(createCandle.apply(BigDecimal.valueOf(7.9)));
        ema.addCandle(createCandle.apply(BigDecimal.valueOf(7.1)));
        ema.addCandle(createCandle.apply(BigDecimal.valueOf(5.2)));

        Assert.assertEquals(BigDecimal.valueOf(6.130), ema.getCurrentValue().setScale(2, RoundingMode.HALF_UP));
    }
}