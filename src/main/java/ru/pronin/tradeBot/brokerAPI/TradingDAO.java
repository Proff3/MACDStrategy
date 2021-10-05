package ru.pronin.tradeBot.brokerAPI;

public interface TradingDAO<TMarketOrderRequest, TLimitOrderRequest, TOrder> {
    void placeLimitOrder(String figi, TLimitOrderRequest limitRequest, String brokerAccountID);
    void placeMarketOrder(String figi, TMarketOrderRequest marketRequest, String brokerAccountID);

}
