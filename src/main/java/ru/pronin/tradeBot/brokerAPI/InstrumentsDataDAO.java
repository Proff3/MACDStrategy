package ru.pronin.tradeBot.brokerAPI;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.brokerAPI.entities.CustomMarketInstrument;

import java.time.OffsetDateTime;
import java.util.List;

public interface InstrumentsDataDAO {
    List<CustomMarketInstrument> getAllInstruments();
    List<CustomCandle> getRequiredNumberOfCandles(String figi, int numberOfCandles, CustomCandleResolution customCandleResolution) throws Exception;
    List<CustomCandle> getCandlesFromDateTime(String figi, OffsetDateTime startTime, CustomCandleResolution customCandleResolution) throws Exception;
}
