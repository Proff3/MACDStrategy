package ru.pronin.tradeBot.brokerAPI;

import io.reactivex.rxjava3.functions.Consumer;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.tinkoff.TinkoffConvertor;
import ru.pronin.tradeBot.strategies.Strategy;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.util.List;
import java.util.function.Function;

public class EventHandlerFactory {

    public static Function<StreamingEvent, Boolean> getTinkoffEventHandler(List<Strategy> strategies){
        return (event) -> {
            if(event.getClass().equals(StreamingEvent.Candle.class)){
                StreamingEvent.Candle originCandle = (StreamingEvent.Candle) event;
                CustomCandle candle = TinkoffConvertor.convertStreamingEventCandleToCustom(originCandle);
                strategies
                        .stream()
                        .filter(s -> s.getFigi().equalsIgnoreCase(candle.getFigi()))
                        .forEach(s -> s.addCandle(candle));
                strategies.forEach(s -> System.out.println(s.isTimeToBuy()));
            }
            if(event.getClass().equals(StreamingEvent.InstrumentInfo.class)){
                StreamingEvent.InstrumentInfo instrumentInfo = (StreamingEvent.InstrumentInfo) event;
            }
            if(event.getClass().equals(StreamingEvent.Orderbook.class)){
                StreamingEvent.Orderbook orderBook = (StreamingEvent.Orderbook) event;
            }
            return strategies.stream().allMatch(Strategy::isOver);
        };
    };

    public static Runnable getSetterOver(List<Strategy> strategies) {
        return () -> {
          strategies.forEach(Strategy::setOver);
        };
    }
}
