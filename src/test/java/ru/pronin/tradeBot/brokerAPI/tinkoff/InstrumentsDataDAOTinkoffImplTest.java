package ru.pronin.tradeBot.brokerAPI.tinkoff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class InstrumentsDataDAOTinkoffImplTest {

    private BrokerDAO tinkoff;
    private final Logger LOGGER = Logger.getLogger(InstrumentsDataDAOTinkoffImplTest.class.toString());

    @Spy
    private InstrumentsDataDAOTinkoffImpl tinkoffInstruments = new InstrumentsDataDAOTinkoffImpl();

    @BeforeEach
    void init(){
        tinkoff = new BrokerDAOTinkoffImpl(
                true,
                tinkoffInstruments,
                new SubscriptionDAOTinkoffImpl(),
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
    }

    @Test
    public void testGetAllInstruments() {
        List<CustomMarketInstrument> instrumentList = tinkoffInstruments.getAllInstruments();
        assertNotNull(instrumentList.get(0));
    }

    @Test
    public void testGetCandlesForWeeks() {
        //apple figi - BBG000B9XRY4
        List<CustomCandle> candles = null;
        try {
            candles = tinkoffInstruments.getRequiredNumberOfCandles("BBG000B9XRY4", 20, CustomCandleResolution.HOUR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert candles != null;
        assertTrue(candles.size() > 0);
    }

    @Test
    public void testSetMARKET() {
        verify(tinkoffInstruments, times(1)).setMARKET(any());
    }

    @Test
    void getRequiredNumberOfCandles() {
        String figi = "BBG000B9XRY4";
        assertAll(
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(figi, 10, CustomCandleResolution._1MIN);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(figi, 10, CustomCandleResolution._30MIN);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(figi, 10, CustomCandleResolution.HOUR);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(figi, 10, CustomCandleResolution.DAY);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(figi, 10, CustomCandleResolution.WEEK);
                    assertNotEquals(0, candles.size());
                },
                () -> {
                    List<CustomCandle> candles = tinkoffInstruments
                            .getRequiredNumberOfCandles(figi, 10, CustomCandleResolution.MONTH);
                    assertNotEquals(0, candles.size());
                }
        );
    }

    @Test
    void getCandlesFromDateTime() throws Exception {
        String figi = "BBG000B9XRY4";
        List<CustomCandle> candles = tinkoffInstruments
                .getCandlesFromDateTime(figi,
                        OffsetDateTime.now().minusYears(1),
                        CustomCandleResolution.HOUR);
        assertNotEquals(0, candles.size());
    }
}