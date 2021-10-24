package ru.pronin.tradeBot.brokerAPI.tinkoff;

import org.reactivestreams.Subscriber;
import ru.pronin.tradeBot.brokerAPI.SubscriptionDAO;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.brokerAPI.exceptions.EventHandlerNotInitializeException;
import ru.pronin.tradeBot.brokerAPI.exceptions.StreamInitializationException;
import ru.tinkoff.invest.openapi.StreamingContext;
import ru.tinkoff.invest.openapi.model.streaming.CandleInterval;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;
import ru.tinkoff.invest.openapi.model.streaming.StreamingRequest;

import java.util.function.Function;

public class SubscriptionDAOTinkoffImpl implements SubscriptionDAO<StreamingEvent> {

    private Boolean isSubscribeSet;
    private StreamingContext STREAM;

    public void setSTREAM(StreamingContext STREAM) throws StreamInitializationException {
        if (this.STREAM != null) throw new StreamInitializationException();
        this.STREAM = STREAM;
    }

    @Override
    public void subscribeOnCandles(String figi, CustomCandleResolution customCandleResolution)
            throws EventHandlerNotInitializeException {
        if (!isSubscribeSet) throw new EventHandlerNotInitializeException();
        CandleInterval interval = CandleInterval.valueOf(customCandleResolution.getValue());
        StreamingRequest candleRequest = StreamingRequest.subscribeCandle(figi, interval);
        STREAM.sendRequest(candleRequest);
    }

    @Override
    public void subscribeOnInstrumentInfo(String figi) throws EventHandlerNotInitializeException {
        if (isSubscribeSet) throw new EventHandlerNotInitializeException();
        StreamingRequest instrumentInfoRequest = StreamingRequest.subscribeInstrumentInfo(figi);
        STREAM.sendRequest(instrumentInfoRequest);
    }

    @Override
    public void subscribeOnOrderBook(String figi, int depth) throws EventHandlerNotInitializeException {
        if (isSubscribeSet) throw new EventHandlerNotInitializeException();
        StreamingRequest OrderBookRequest = StreamingRequest.subscribeOrderbook(figi, depth);
        STREAM.sendRequest(OrderBookRequest);
    }

    @Override
    public void setSubscriber(Function<StreamingEvent, Boolean> eventHandler) {
        Subscriber<StreamingEvent> subscriber = new TinkoffSubscriber(eventHandler);
        STREAM.subscribe(subscriber);
        isSubscribeSet = true;
    }
}
