package ru.pronin.tradeBot.strategies;

import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.EventHandlerFactory;
import ru.pronin.tradeBot.brokerAPI.InstrumentsDataDAO;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.brokerAPI.exceptions.EventHandlerNotInitializeException;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.function.Function;

public class RunnableStrategy<SE> implements Runnable{

    private final List<Strategy> strategies;
    private final BrokerDAO broker;
    private final Function<SE, Boolean> handlerEvent;
    private final List<String> figiStocks;
    private final CustomCandleResolution resolution;

    public RunnableStrategy(List<Strategy> strategies,
                            BrokerDAO brokerDAO,
                            Function<SE, Boolean> handlerEvent,
                            List<String> figiStocks,
                            CustomCandleResolution resolution) {
        this.strategies = strategies;
        this.broker = brokerDAO;
        this.handlerEvent = handlerEvent;
        this.figiStocks = figiStocks;
        this.resolution = resolution;
    }

    @Override
    public void run() {
        strategies.forEach(s -> {
            int requiredNumberOfCandles = s.getStrategyDepth();
            try {
                List<CustomCandle> candles = broker
                        .getInstrumentsDataDAO()
                        .getRequiredNumberOfCandles(s.getFigi(), requiredNumberOfCandles, resolution);
                candles.forEach(s::addCandle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        broker.getSubscriptionDAO().setSubscriber(handlerEvent, EventHandlerFactory.getSetterOver(strategies));
        figiStocks.forEach(figi ->{
            try {
                broker.getSubscriptionDAO().subscribeOnCandles(figi, resolution);
            } catch (EventHandlerNotInitializeException e) {
                e.printStackTrace();
            }
        });
//        InstrumentsDataDAO
//        strategies.forEach(s -> {
//            OffsetDateTime date = OffsetDateTime.now();
//            while(!s.isEnoughInformation()){
//
//            }
//        });
    }
}
