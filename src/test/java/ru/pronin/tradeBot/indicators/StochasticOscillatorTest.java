package ru.pronin.tradeBot.indicators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class StochasticOscillatorTest {

    private StochasticOscillator so;
    private long i = 0;

    Function<BigDecimal, CustomCandle> createCandle = (value) -> {
        CustomCandle candle = new CustomCandle();
        candle.setC(value);
        candle.setTime(OffsetDateTime.now().plusSeconds(i++));
        return candle;
    };

    @BeforeEach
    void init() {
        so = new StochasticOscillator(14, 3);
    }

    @Test
    void addCandle() {
        so.addCandle(createCandle.apply(new BigDecimal(100)));
        so.addCandle(createCandle.apply(new BigDecimal(200)));
        so.addCandle(createCandle.apply(new BigDecimal(300)));
        so.addCandle(createCandle.apply(new BigDecimal(400)));
        so.addCandle(createCandle.apply(new BigDecimal(500)));
    }

    @Test
    void isEnoughInformation() {
    }

    @Test
    void isOver() {
    }

    @Test
    void getIndicatorDepth() {
    }

    @Test
    void setOver() {
    }

    @Test
    void getValue() {
    }

    @Test
    void getEmaValue() {
    }
}