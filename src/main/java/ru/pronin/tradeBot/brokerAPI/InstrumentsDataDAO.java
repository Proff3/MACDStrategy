package ru.pronin.tradeBot.brokerAPI;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;

import java.time.OffsetDateTime;
import java.util.List;

public interface InstrumentsDataDAO {
    List<CustomMarketInstrument> getAllInstruments();
    List<CustomCandle> getRequiredNumberOfCandles(boolean USASession, String figi, int numberOfCandles, CustomCandleResolution customCandleResolution) throws Exception;
    List<CustomCandle> getCandlesFromDateTime(boolean USASession, String figi, OffsetDateTime startTime, CustomCandleResolution customCandleResolution) throws Exception;

}
