package ru.pronin.tradeBot.brokerAPI.tinkoff;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.tinkoff.invest.openapi.model.rest.Candle;

public class TinkoffConvertor {
    static public CustomCandle convertOriginCandleToCustom(Candle candle){
        return new CustomCandle(
                candle.getFigi(),
                CustomCandleResolution.fromValue(candle.getInterval().getValue()),
                candle.getO(),
                candle.getC(),
                candle.getH(),
                candle.getL(),
                candle.getV(),
                candle.getTime()
        );
    }
}
