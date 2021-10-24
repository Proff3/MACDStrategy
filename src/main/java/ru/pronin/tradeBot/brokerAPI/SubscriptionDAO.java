package ru.pronin.tradeBot.brokerAPI;

import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.brokerAPI.exceptions.EventHandlerNotInitializeException;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.util.function.Function;

public interface SubscriptionDAO<SE> {
    void subscribeOnCandles(String figi, CustomCandleResolution customCandleResolution) throws EventHandlerNotInitializeException;
    void subscribeOnInstrumentInfo(String figi) throws EventHandlerNotInitializeException;
    void subscribeOnOrderBook(String figi, int depth) throws EventHandlerNotInitializeException;
    /**
     * @param eventHandler - function, that applies the stream event and returns boolean means end of subscription
     */
    void setSubscriber(Function<SE, Boolean> eventHandler);
}
