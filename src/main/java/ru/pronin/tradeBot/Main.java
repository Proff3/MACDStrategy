package ru.pronin.tradeBot;

import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.EventHandlerFactory;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.brokerAPI.exceptions.EventHandlerNotInitializeException;
import ru.pronin.tradeBot.brokerAPI.tinkoff.*;
import ru.pronin.tradeBot.strategies.MACD.EMA;
import ru.pronin.tradeBot.strategies.MACD.MACD;
import ru.pronin.tradeBot.strategies.RunnableStrategy;
import ru.pronin.tradeBot.strategies.Strategy;
import ru.tinkoff.invest.openapi.*;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

import java.io.FileReader;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        List<String> figiStocks = List.of("BBG000B9XRY4"); //APPLE
        List<Strategy> strategies = List.of(new MACD("BBG000B9XRY4"));
        BrokerDAO broker = new BrokerDAOTinkoffImpl(
                true,
                new InstrumentsDataDAOTinkoffImpl(),
                new SubscriptionDAOTinkoffImpl(),
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
        Runnable runnableStrategies = new RunnableStrategy<StreamingEvent>(
                strategies,
                broker,
                EventHandlerFactory.getTinkoffEventHandler(strategies),
                figiStocks,
                CustomCandleResolution._5MIN);
        runnableStrategies.run();
    }

    private static String getToken() {
        Properties prop = new Properties();
        FileReader fr;
        try {
            fr = new FileReader("./src/main/resources/application.properties");
            prop.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty("tinkoff.sandbox.token");
    }

}
