package ru.pronin.tradeBot.indicators.utils;

import org.junit.jupiter.api.Test;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CandleArrayTest {

    private final int DEPTH = 5;

    CandleArray candles = new CandleArray(DEPTH);
    long i = 0;

    Supplier<CustomCandle> createCandle = () -> {
        CustomCandle candle = new CustomCandle();
        candle.setTime(OffsetDateTime.now().plusSeconds(i++));
        return candle;
    };

    @Test
    void addSameCandle() {
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        OffsetDateTime now = OffsetDateTime.now().plusSeconds(i++);
        CustomCandle candle = createCandle.get();
        candle.setTime(now);
        candles.addCandle(candle);
        candles.addCandle(candle);
        assertEquals(4, candles.getSize());
    }

    @Test
    void lengthWithSameCandlesTest() {
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        OffsetDateTime now = OffsetDateTime.now().plusSeconds(i++);
        CustomCandle candle = createCandle.get();
        candle.setTime(now);
        candles.addCandle(candle);
        int sizeBefore = candles.getSize();
        candles.addCandle(candle);
        int sizeAfter = candles.getSize();
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    void lengthTest() {
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        candles.addCandle(createCandle.get());
        assertEquals(DEPTH, candles.getSize());
    }
}