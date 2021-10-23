package ru.pronin.tradeBot.brokerAPI;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.brokerAPI.entities.CustomMarketInstrument;

import java.util.List;

public interface InstrumentsDataDAO {
    List<CustomMarketInstrument> getAllInstruments();
    List<CustomCandle> getCandlesForWeeks(String figi, int numberOfWeeks, CustomCandleResolution customCandleResolution) throws Exception;
}
