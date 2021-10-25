package ru.pronin.tradeBot.strategies.MACD;

import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.tinkoff.*;
import ru.pronin.tradeBot.strategies.Strategy;

import java.util.function.Function;

public class BrokerMACDImplementation implements Runnable {

    //BBG000B9XRY4 - apple
    Strategy MACD = new MACD(8, 17, 9, "BBG000B9XRY4");

    BrokerDAO broker = new BrokerDAOTinkoffImpl(
            true,
            new InstrumentsDataDAOTinkoffImpl(),
            new SubscriptionDAOTinkoffImpl(),
            new TradingDAOTinkoffImpl(),
            new PortfolioDAOTinkoffImpl()
    );

    @Override
    public void run() {
        //broker.getSubscriptionDAO().setSubscriber();
    }
}
