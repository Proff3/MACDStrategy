package ru.pronin.tradeBot.brokerAPI;

import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * @param <TInstrument> - type of instrument
 * @param <TCandle> - type of candle
 * @param <TCandleResolution> - type of candle interval
 * @param <TMarketOrderRequest> - type of market order request
 * @param <TLimitOrderRequest> - type of limit order request
 */
public abstract class BrokerDAO<TInstrument, TCandle, TCandleResolution, TMarketOrderRequest, TLimitOrderRequest, TOrder> {

    private InstrumentsDataDAO<TInstrument, TCandle, TCandleResolution> instrumentsDataDAO;
    private SubscriptionDAO<TCandleResolution> subscriptionDAO;
    private TradingDAO<TMarketOrderRequest, TLimitOrderRequest, TOrder> tradingDAO;

    public BrokerDAO(InstrumentsDataDAO<TInstrument, TCandle, TCandleResolution> instrumentsDataDAO,
                     SubscriptionDAO<TCandleResolution> subscriptionDAO,
                     TradingDAO<TMarketOrderRequest, TLimitOrderRequest, TOrder> tradingDAO) {
        this.instrumentsDataDAO = instrumentsDataDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.tradingDAO = tradingDAO;
    }
}