package ru.pronin.tradeBot;

import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.EventHandlerFactory;
import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;
import ru.pronin.tradeBot.brokerAPI.tinkoff.*;
import ru.pronin.tradeBot.indicators.MACD;
import ru.pronin.tradeBot.indicators.Indicator;
import ru.pronin.tradeBot.indicators.StochasticOscillator;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> figiStocks = List.of("BBG000B9XRY4", "BBG002B04MT8"); //APPLE, UBER
        StochasticOscillator so = new StochasticOscillator(14, 3);
        BrokerDAO broker = new BrokerDAOTinkoffImpl(
                true,
                new InstrumentsDataDAOTinkoffImpl(),
                new SubscriptionDAOTinkoffImpl(),
                new TradingDAOTinkoffImpl(),
                new PortfolioDAOTinkoffImpl());
        List<CustomCandle> candles = broker.getInstrumentsDataDAO().getRequiredNumberOfCandles(figiStocks.get(1), 100, CustomCandleResolution._5MIN);
        candles.forEach(c -> {
            so.addCandle(c);
            System.out.println("Current value " + so.getValue() + " EMA value " + so.getEmaValue() + " time " + c.getTime());
        });
//        Runnable runnableStrategies = new RunnableStrategy<StreamingEvent>(
//                strategies,
//                broker,
//                EventHandlerFactory.getTinkoffEventHandler(strategies),
//                figiStocks,
//                CustomCandleResolution._5MIN);
//        runnableStrategies.run();
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
