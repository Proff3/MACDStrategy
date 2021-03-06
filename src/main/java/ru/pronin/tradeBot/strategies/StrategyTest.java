package ru.pronin.tradeBot.strategies;

import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.entities.CustomMarketInstrument;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.indicators.Indicator;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategyTest implements Runnable {
    final List<Indicator> strategies;
    final OffsetDateTime startDate;
    final CustomCandleResolution resolution;
    final BrokerDAO broker;

    public StrategyTest(List<Indicator> strategies, OffsetDateTime startDate, CustomCandleResolution resolution, BrokerDAO broker) {
        this.strategies = strategies;
        this.startDate = startDate;
        this.resolution = resolution;
        this.broker = broker;
    }

    @Override
    public void run() {
        Map<String, List<CustomCandle>> candlesMap = new HashMap<>();
        List<CustomMarketInstrument> instruments = broker.getInstrumentsDataDAO().getAllInstruments();
        instruments.forEach(instrument -> {
            String figi = instrument.getFigi();
            List<CustomCandle> middlewareCandles = new ArrayList<>();
            try {
                middlewareCandles =
                        broker.getInstrumentsDataDAO().getCandlesFromDateTime(figi, startDate, resolution);
            } catch (Exception e) {
                e.printStackTrace();
            }
            candlesMap.put(figi, middlewareCandles);
        });
        //strategies.forEach();
    }
}
