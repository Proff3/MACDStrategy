package ru.pronin.tradeBot.brokerAPI.tinkoff;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InstrumentsDataDAOTinkoffImplTest {

    private BrokerDAO tinkoff;

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
            candles = tinkoffInstruments.getCandlesForWeeks("BBG000B9XRY4", 1, CustomCandleResolution.HOUR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(candles.size() > 0);
    }

    @Test
    public void testSetMARKET() {
        verify(tinkoffInstruments, times(1)).setMARKET(any());
    }

}