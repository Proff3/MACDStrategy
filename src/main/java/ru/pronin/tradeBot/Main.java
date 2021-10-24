package ru.pronin.tradeBot;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.tradeBot.brokerAPI.tinkoff.TinkoffConvertor;
import ru.pronin.tradeBot.strategies.MACD.EMA;
import ru.tinkoff.invest.openapi.*;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

import java.io.FileReader;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Main {

    public static MarketContext MARKET;
    public static SandboxContext SANDBOX;
    public static OrdersContext ORDERS;
    public static PortfolioContext PORTFOLIO;
    public static List<MarketInstrument> MARKET_INSTRUMENTS;

    static {
        OpenApi api = new OkHttpOpenApi(getToken(), true);
        SANDBOX = api.getSandboxContext();
        MARKET = api.getMarketContext();
        ORDERS = api.getOrdersContext();
        PORTFOLIO = api.getPortfolioContext();
        try {
            MARKET_INSTRUMENTS = MARKET.getMarketStocks().get().getInstruments();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String appleFigi = "BBG000B9XRY4";
        OffsetDateTime dateFrom = OffsetDateTime.now().minusWeeks(1L);
        List<Candle> appleCandles =
                MARKET
                        .getMarketCandles(appleFigi, dateFrom, OffsetDateTime.now(), CandleResolution.HOUR)
                        .get()
                        .orElse(null)
                        .getCandles();
        EMA ema = new EMA(5);
        appleCandles.stream()
                .map(TinkoffConvertor::convertOriginCandleToCustom)
                .forEach(ema::addCandle);
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
