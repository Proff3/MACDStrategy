package ru.pronin.tradeBot.indicators.utils;

import org.junit.jupiter.api.Test;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;

import java.time.OffsetDateTime;
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
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        OffsetDateTime now = OffsetDateTime.now().plusSeconds(i++);
        CustomCandle candle = createCandle.get();
        candle.setTime(now);
        candles.add(candle);
        candles.add(candle);
        assertEquals(4, candles.getSize());
    }

    @Test
    void lengthWithSameCandlesTest() {
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        OffsetDateTime now = OffsetDateTime.now().plusSeconds(i++);
        CustomCandle candle = createCandle.get();
        candle.setTime(now);
        candles.add(candle);
        int sizeBefore = candles.getSize();
        candles.add(candle);
        int sizeAfter = candles.getSize();
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    void lengthTest() {
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        candles.add(createCandle.get());
        assertEquals(DEPTH, candles.getSize());
    }
}