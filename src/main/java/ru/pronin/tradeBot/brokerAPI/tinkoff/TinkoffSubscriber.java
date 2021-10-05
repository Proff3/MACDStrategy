package ru.pronin.tradeBot.brokerAPI.tinkoff;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;


public class TinkoffSubscriber implements Subscriber<StreamingEvent> {

    private final Logger LOGGER = Logger.getLogger(String.valueOf(TinkoffSubscriber.class));
    private Subscription subscription;
    private final Function<StreamingEvent, Boolean> eventHandler;

    public TinkoffSubscriber(Function<StreamingEvent, Boolean> eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        subscription.request(10);
    }

    @Override
    public void onNext(StreamingEvent streamingEvent) {
        Boolean isEnded = eventHandler.apply(streamingEvent);
        int n = isEnded ? 0 : 100;
        subscription.request(n);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        LOGGER.info("Completed");
    }
}
