package ru.pronin.tradeBot.brokerAPI.tinkoff;

import org.reactivestreams.Subscriber;
import ru.pronin.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.tradeBot.brokerAPI.InstrumentsDataDAO;
import ru.pronin.tradeBot.brokerAPI.SubscriptionDAO;
import ru.pronin.tradeBot.brokerAPI.TradingDAO;
import ru.pronin.tradeBot.brokerAPI.exceptions.CandlesNotFoundException;
import ru.pronin.tradeBot.brokerAPI.exceptions.NoMarketInstrumentException;
import ru.tinkoff.invest.openapi.*;
import ru.tinkoff.invest.openapi.model.rest.*;
import ru.tinkoff.invest.openapi.model.streaming.CandleInterval;
import ru.tinkoff.invest.openapi.model.streaming.StreamingEvent;
import ru.tinkoff.invest.openapi.model.streaming.StreamingRequest;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

import java.io.FileReader;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class DAOTinkoffImpl implements BrokerDAO<MarketInstrument, Candle, CandleResolution, MarketOrderRequest, LimitOrderRequest> {

    public static MarketContext MARKET;
    public static SandboxContext SANDBOX;
    public static OrdersContext ORDERS;
    public static PortfolioContext PORTFOLIO;
    public static StreamingContext STREAM;
    private final InstrumentsDataDAO<MarketInstrument, Candle, CandleResolution> instrumentsDataDAO =
            new InstrumentsDataDAOTinkoffImpl();
    private final SubscriptionDAO<CandleResolution> subscriptionDAO = new SubscriptionDAOTinkoffImpl();
    private final TradingDAO<MarketOrderRequest, LimitOrderRequest> tradingDAO = new TradingDAOTinkoffImpl();

    public DAOTinkoffImpl(Boolean sandboxMode) {
        OpenApi api = new OkHttpOpenApi(getToken(), sandboxMode);
        SANDBOX = api.getSandboxContext();
        MARKET = api.getMarketContext();
        ORDERS = api.getOrdersContext();
        PORTFOLIO = api.getPortfolioContext();
        STREAM = api.getStreamingContext();
    }

    @Override
    public InstrumentsDataDAO<MarketInstrument, Candle, CandleResolution> getInstrumentsDataDao() {
        return instrumentsDataDAO;
    }

    @Override
    public SubscriptionDAO<CandleResolution> getSubscriptionDao() {
        return subscriptionDAO;
    }

    @Override
    public TradingDAO<MarketOrderRequest, LimitOrderRequest> getTradingDao() {
        return tradingDAO;
    }

    private String getToken() {
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

