package ru.pronin.tradeBot.brokerAPI;

import java.util.List;

public interface InstrumentsDataDAO<TInstrument, TCandle, TCandleResolution> {
    List<TInstrument> getAllInstruments();
    List<TCandle> getCandlesForWeeks(String figi, int numberOfWeeks, TCandleResolution candleResolution) throws Exception;
}
