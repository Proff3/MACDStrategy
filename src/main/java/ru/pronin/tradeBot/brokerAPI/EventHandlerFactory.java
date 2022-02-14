package ru.pronin.tradeBot.brokerAPI;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.tinkoff.TinkoffConvertor;
import ru.pronin.tradeBot.indicators.Indicator;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.util.List;
import java.util.function.Function;

public class EventHandlerFactory {

    public static Function<StreamingEvent, Boolean> getTinkoffEventHandler(List<Indicator> strategies){
        return (event) -> {
            if(event.getClass().equals(StreamingEvent.Candle.class)){
                StreamingEvent.Candle originCandle = (StreamingEvent.Candle) event;
                CustomCandle candle = TinkoffConvertor.convertStreamingEventCandleToCustom(originCandle);
                strategies
                        .stream()
                        //.filter(s -> s.getFigi().equalsIgnoreCase(candle.getFigi()))
                        .forEach(s -> s.addCandle(candle));
            }
            if(event.getClass().equals(StreamingEvent.InstrumentInfo.class)){
                StreamingEvent.InstrumentInfo instrumentInfo = (StreamingEvent.InstrumentInfo) event;
            }
            if(event.getClass().equals(StreamingEvent.Orderbook.class)){
                StreamingEvent.Orderbook orderBook = (StreamingEvent.Orderbook) event;
            }
            return strategies.stream().allMatch(Indicator::isOver);
        };
    }

    public static Runnable getSetterOver(List<Indicator> strategies) {
        return () -> strategies.forEach(Indicator::setOver);
    }
}
