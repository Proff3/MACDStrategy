package ru.pronin.tradeBot.brokerAPI;

import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.util.function.Function;

public interface SubscriptionDAO<TCandleResolution> {
    void subscribeOnCandles(String figi, TCandleResolution candleResolution);
    void subscribeOnInstrumentInfo(String figi);
    void subscribeOnOrderBook(String figi, int depth);
    /**
     * @param eventHandler - function, that applies the stream event and returns boolean means end of subscription
     */
    void setSubscriber(Function<StreamingEvent, Boolean> eventHandler);
}
