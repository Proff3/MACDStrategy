package ru.pronin.tradeBot.brokerAPI.tinkoff;

import ru.pronin.tradeBot.brokerAPI.InstrumentsDataDAO;
import ru.pronin.tradeBot.brokerAPI.exceptions.CandlesNotFoundException;
import ru.pronin.tradeBot.brokerAPI.exceptions.NoMarketInstrumentException;
import ru.tinkoff.invest.openapi.MarketContext;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static ru.pronin.tradeBot.brokerAPI.tinkoff.DAOTinkoffImpl.MARKET;

public class InstrumentsDataDAOTinkoffImpl implements InstrumentsDataDAO<MarketInstrument, Candle, CandleResolution> {

    List<MarketInstrument> instruments;

    @Override
    public List<MarketInstrument> getAllInstruments() {
        if (instruments == null) {
            try {
                instruments = MARKET.getMarketStocks().get().getInstruments();
                return instruments;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return instruments;
    }

    @Override
    public List<Candle> getCandlesForWeeks(String figi, int numberOfWeeks, CandleResolution candleResolution) throws Exception {
        OffsetDateTime dateFrom = OffsetDateTime.now().minusWeeks(numberOfWeeks);
        return getCandlesForTime(dateFrom, figi, candleResolution);
    }

    private List<Candle> getCandlesForTime(OffsetDateTime dateFrom, String figi, CandleResolution resolution) throws Exception {
        List<Candle> resultCandles = new ArrayList<>();
        while(dateFrom.isBefore(OffsetDateTime.now().minusDays(1))){
            List<Candle> middlewareCandles = MARKET
                    .getMarketCandles(figi, dateFrom, dateFrom.plusWeeks(1), resolution)
                    .get()
                    .orElseThrow(() -> {
                        try {
                            instruments
                                    .stream()
                                    .filter(instrument -> instrument.getFigi().equalsIgnoreCase(figi))
                                    .findFirst()
                                    .orElseThrow(NoMarketInstrumentException::new);
                            return new CandlesNotFoundException();
                        } catch (NoMarketInstrumentException e) {
                            return new NoMarketInstrumentException();
                        }
                    })
                    .getCandles();
            dateFrom = dateFrom.plusWeeks(1);
            resultCandles.addAll(middlewareCandles);
        }
        return resultCandles;
    }
}
