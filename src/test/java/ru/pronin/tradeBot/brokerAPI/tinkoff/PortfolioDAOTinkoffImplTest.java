package ru.pronin.tradeBot.brokerAPI.tinkoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.entities.CustomPortfolioPosition;
import ru.pronin.tradeBot.brokerAPI.enums.CustomInstrumentType;
import ru.pronin.tradeBot.brokerAPI.enums.CustomOperationType;
import ru.pronin.tradeBot.brokerAPI.exceptions.PortfolioInitializationException;
import ru.tinkoff.invest.openapi.model.rest.InstrumentType;
import ru.tinkoff.invest.openapi.model.rest.PortfolioPosition;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PortfolioDAOTinkoffImplTest {

    Logger LOGGER = LoggerFactory.getLogger(PortfolioDAOTinkoffImplTest.class);
    @Spy
    private BrokerDAO tinkoff = new BrokerDAOTinkoffImpl(
            true,
            new InstrumentsDataDAOTinkoffImpl(),
            new SubscriptionDAOTinkoffImpl(),
            new TradingDAOTinkoffImpl(),
            new PortfolioDAOTinkoffImpl());

    Function<String, Integer> countPositionsForFigi = (figi) -> tinkoff
            .getPortfolioDAO()
            .getPortfolio()
            .getPositions()
            .stream()
            .filter(pos -> pos.getFigi().equalsIgnoreCase(figi))
            .mapToInt(CustomPortfolioPosition::getLots)
            .sum();

    @Test
    void getPortfolio() throws ExecutionException, InterruptedException {
        int beforePlacingOrder = countPositionsForFigi.apply("BBG000B9XRY4");
        int lots = 10;
        //orders complete immediately cause it`s a sandbox mode
        tinkoff.getTradingDAO().placeLimitOrder(
                "BBG000B9XRY4",
                lots,
                CustomOperationType.BUY,
                BigDecimal.valueOf(144)
        );
        int afterPlacingOrder = countPositionsForFigi.apply("BBG000B9XRY4");
        assertEquals(lots, afterPlacingOrder - beforePlacingOrder);
    }

    @Test
    void setPORTFOLIO() {
        assertThrows(PortfolioInitializationException.class,
                () -> {
            PortfolioDAOTinkoffImpl portfolio = (PortfolioDAOTinkoffImpl)tinkoff.getPortfolioDAO();
            portfolio.setPORTFOLIO(null);
        });
    }
}
