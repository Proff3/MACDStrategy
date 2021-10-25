package ru.pronin.tradeBot.brokerAPI;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.tinkoff.TinkoffConvertor;
import ru.pronin.tradeBot.strategies.Strategy;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.util.function.Function;

public class EventHandlerFactory {
    public static Function<StreamingEvent, Boolean> getTinkoffEventHandler(Strategy strategy){
        return (event) -> {
            if(event.getClass().equals(StreamingEvent.Candle.class)){
                StreamingEvent.Candle originCandle = (StreamingEvent.Candle) event;
                CustomCandle candle = TinkoffConvertor.convertStreamingEventCandleToCustom(originCandle);
                strategy.addCandle(candle);
            }
            if(event.getClass().equals(StreamingEvent.InstrumentInfo.class)){
                StreamingEvent.InstrumentInfo instrumentInfo = (StreamingEvent.InstrumentInfo) event;
            }
            if(event.getClass().equals(StreamingEvent.Orderbook.class)){
                StreamingEvent.Orderbook orderbook = (StreamingEvent.Orderbook) event;
            }
            return strategy.isOver();
        };
    };
    public static Function<Integer, Boolean> getTinkoffEventHandlerr(Strategy strategy){
        return (event) -> {
//            if(event.getClass().equals(StreamingEvent.Candle.class)){
//                StreamingEvent.Candle originCandle = (StreamingEvent.Candle) event;
//                CustomCandle candle = TinkoffConvertor.convertStreamingEventCandleToCustom(originCandle);
//                strategy.addCandle(candle);
//            }
//            if(event.getClass().equals(StreamingEvent.InstrumentInfo.class)){
//                StreamingEvent.InstrumentInfo instrumentInfo = (StreamingEvent.InstrumentInfo) event;
//            }
//            if(event.getClass().equals(StreamingEvent.Orderbook.class)){
//                StreamingEvent.Orderbook orderbook = (StreamingEvent.Orderbook) event;
//            }
            return strategy.isOver();
        };
    };
}
