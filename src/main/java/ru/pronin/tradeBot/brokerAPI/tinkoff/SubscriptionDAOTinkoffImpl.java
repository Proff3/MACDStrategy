package ru.pronin.tradeBot.brokerAPI.tinkoff;

import org.reactivestreams.Subscriber;
import ru.pronin.tradeBot.brokerAPI.SubscriptionDAO;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.streaming.CandleInterval;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;
import ru.tinkoff.invest.openapi.model.streaming.StreamingRequest;

import java.util.function.Function;
import static ru.pronin.tradeBot.brokerAPI.tinkoff.DAOTinkoffImpl.STREAM;

public class SubscriptionDAOTinkoffImpl implements SubscriptionDAO<CandleResolution> {

    @Override
    public void subscribeOnCandles(String figi, CandleResolution candleResolution) {
        CandleInterval interval = CandleInterval.valueOf(candleResolution.getValue());
        StreamingRequest candleRequest = StreamingRequest.subscribeCandle(figi, interval);
        STREAM.sendRequest(candleRequest);
    }

    @Override
    public void subscribeOnInstrumentInfo(String figi) {
        StreamingRequest instrumentInfoRequest = StreamingRequest.subscribeInstrumentInfo(figi);
        STREAM.sendRequest(instrumentInfoRequest);
    }

    @Override
    public void subscribeOnOrderBook(String figi, int depth) {
        StreamingRequest OrderBookRequest = StreamingRequest.subscribeOrderbook(figi, depth);
        STREAM.sendRequest(OrderBookRequest);
    }

    @Override
    public void setSubscriber(Function<StreamingEvent, Boolean> eventHandler) {
        Subscriber<StreamingEvent> subscriber = new TinkoffSubscriber(eventHandler);
        STREAM.subscribe(subscriber);
    }
}
