package ru.pronin.tradeBot.brokerAPI.tinkoff;

import ru.pronin.tradeBot.brokerAPI.InstrumentsDataDAO;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCurrency;
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
import java.util.stream.Collectors;

public class InstrumentsDataDAOTinkoffImpl implements InstrumentsDataDAO {

    private MarketContext MARKET;
    List<CustomMarketInstrument> instruments;

    public void setMARKET(MarketContext MARKET) {
        this.MARKET = MARKET;
    }

    @Override
    public List<CustomMarketInstrument> getAllInstruments() {
        if (instruments == null) {
            try {
                List<ru.tinkoff.invest.openapi.model.rest.MarketInstrument> tinkoffInstruments = MARKET.getMarketStocks().get().getInstruments();
                instruments = tinkoffInstruments
                        .stream()
                        .map(InstrumentsDataDAOTinkoffImpl::tinkoffInstrumentToCustom)
                        .collect(Collectors.toList());
                return instruments;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return instruments;
    }

    @Override
    public List<CustomCandle> getCandlesForWeeks(String figi, int numberOfWeeks, CustomCandleResolution customCandleResolution) throws Exception {
        OffsetDateTime dateFrom = OffsetDateTime.now().minusWeeks(numberOfWeeks);
        return getCandlesForTime(dateFrom, figi, customCandleResolution);
    }

    private List<CustomCandle> getCandlesForTime(OffsetDateTime dateFrom, String figi, CustomCandleResolution resolution) throws Exception {
        CandleResolution candleResolution = CandleResolution.fromValue(resolution.getValue());
        List<CustomCandle> resultCustomCandles = new ArrayList<>();
        while(dateFrom.isBefore(OffsetDateTime.now().minusDays(1))){
            List<CustomCandle> middlewareCustomCandles = MARKET
                    .getMarketCandles(figi, dateFrom, dateFrom.plusWeeks(1), candleResolution)
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
                    .getCandles()
                    .stream()
                    .map(InstrumentsDataDAOTinkoffImpl::tinkoffCandleToCustom)
                    .collect(Collectors.toList());
            dateFrom = dateFrom.plusWeeks(1);
            resultCustomCandles.addAll(middlewareCustomCandles);
        }
        return resultCustomCandles;
    }

    private static CustomMarketInstrument tinkoffInstrumentToCustom(MarketInstrument tinkoffInstrument){
        CustomCurrency customCurrency = CustomCurrency.valueOf(tinkoffInstrument.getCurrency().toString());
        return new CustomMarketInstrument(
                tinkoffInstrument.getFigi(),
                tinkoffInstrument.getTicker(),
                tinkoffInstrument.getIsin(),
                customCurrency,
                tinkoffInstrument.getName()
        );
    }

    private static CustomCandle tinkoffCandleToCustom(Candle candle){
        CustomCandleResolution resolution = CustomCandleResolution.fromValue(candle.getInterval().getValue());
        return new CustomCandle(
                candle.getFigi(),
                resolution,
                candle.getO(),
                candle.getC(),
                candle.getH(),
                candle.getL(),
                candle.getV(),
                candle.getTime()
        );
    }
}
