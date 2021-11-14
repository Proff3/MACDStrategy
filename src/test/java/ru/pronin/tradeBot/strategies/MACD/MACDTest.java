package ru.pronin.tradeBot.strategies.MACD;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.exceptions.WrongStrategyInitializationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class MACDTest {

    private final Logger LOGGER = Logger.getLogger(MACDTest.class.toString());
    private MACD strategy;
    private final Random random = new Random();
    long i = 0;

    Function<BigDecimal, CustomCandle> createCandle = (value) -> {
        CustomCandle candle = new CustomCandle();
        candle.setC(value);
        candle.setTime(OffsetDateTime.now().plusSeconds(i++));
        return candle;
    };

    @BeforeEach
    void init() throws WrongStrategyInitializationException {
        strategy = new MACD(
                new EMA(2),     //fast
                new EMA(4),     //slow
                new EMA(3),     //signal
                "BBG000B9XRY4");
    }

    @Test
    void addCandle() {
        strategy.addCandle(createCandle.apply(new BigDecimal("5.3")));
        strategy.addCandle(createCandle.apply(new BigDecimal("6.7")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.9")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("5.2")));
        assertEquals(new BigDecimal("0.25"), strategy.getSignalValue().setScale(2, RoundingMode.HALF_UP));
        strategy.addCandle(createCandle.apply(new BigDecimal("4.1")));
        assertEquals(new BigDecimal("-0.19"), strategy.getSignalValue().setScale(2, RoundingMode.HALF_UP));
        strategy.addCandle(createCandle.apply(new BigDecimal("3.5")));
        assertEquals(new BigDecimal("-0.44"), strategy.getSignalValue().setScale(2, RoundingMode.HALF_UP));
        strategy.addCandle(createCandle.apply(new BigDecimal("5.4")));
        assertEquals(new BigDecimal("-0.23"), strategy.getSignalValue().setScale(2, RoundingMode.HALF_UP));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.3")));
        assertEquals(new BigDecimal("0.20"), strategy.getSignalValue().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void isTimeToBuy() {
        strategy.addCandle(createCandle.apply(new BigDecimal("5.3")));
        strategy.addCandle(createCandle.apply(new BigDecimal("6.7")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.9")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("5.2")));
        strategy.addCandle(createCandle.apply(new BigDecimal("4.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("3.5")));
        assertEquals(false, strategy.isTimeToBuy());
        strategy.addCandle(createCandle.apply(new BigDecimal("5.4")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.3")));
        assertEquals(true, strategy.isTimeToBuy());
    }

    @Test
    void isTimeToSell() {
        strategy.addCandle(createCandle.apply(new BigDecimal("5.3")));
        strategy.addCandle(createCandle.apply(new BigDecimal("6.7")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.9")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("5.2")));
        strategy.addCandle(createCandle.apply(new BigDecimal("4.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("3.5")));
        assertEquals(true, strategy.isTimeToSell());
        strategy.addCandle(createCandle.apply(new BigDecimal("5.4")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.3")));
        assertEquals(false, strategy.isTimeToSell());
    }

    @Test
    void isOver() {

    }

    @Test
    void getDifference() {
        strategy.addCandle(createCandle.apply(new BigDecimal("5.3")));
        strategy.addCandle(createCandle.apply(new BigDecimal("6.7")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.9")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("5.2")));
        strategy.addCandle(createCandle.apply(new BigDecimal("4.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("3.5")));
        strategy.addCandle(createCandle.apply(new BigDecimal("5.4")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.3")));
        assertEquals(new BigDecimal("0.43"), strategy.getDifference().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void getDifferenceInPercents() {
        strategy.addCandle(createCandle.apply(new BigDecimal("5.3")));
        strategy.addCandle(createCandle.apply(new BigDecimal("6.7")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.9")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("5.2")));
        strategy.addCandle(createCandle.apply(new BigDecimal("4.1")));
        strategy.addCandle(createCandle.apply(new BigDecimal("3.5")));
        strategy.addCandle(createCandle.apply(new BigDecimal("5.4")));
        strategy.addCandle(createCandle.apply(new BigDecimal("7.3")));
        assertEquals(new BigDecimal("68.21"), strategy.getDifferenceInPercents().setScale(2, RoundingMode.HALF_UP));
    }
}