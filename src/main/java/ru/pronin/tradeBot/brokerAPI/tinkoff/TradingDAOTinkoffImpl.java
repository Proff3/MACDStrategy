package ru.pronin.tradeBot.brokerAPI.tinkoff;

import ru.pronin.tradeBot.brokerAPI.TradingDAO;
import ru.tinkoff.invest.openapi.model.rest.LimitOrderRequest;
import ru.tinkoff.invest.openapi.model.rest.MarketOrderRequest;
import ru.tinkoff.invest.openapi.model.rest.Order;

public class TradingDAOTinkoffImpl implements TradingDAO<MarketOrderRequest, LimitOrderRequest, Order> {
    @Override
    public void placeLimitOrder(String figi, LimitOrderRequest limitRequest, String brokerAccountID) {

    }

    @Override
    public void placeMarketOrder(String figi, MarketOrderRequest marketRequest, String brokerAccountID) {

    }
}
