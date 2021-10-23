package ru.pronin.tradeBot.brokerAPI;

public abstract class BrokerDAO {

    private InstrumentsDataDAO instrumentsDataDAO;
    private SubscriptionDAO subscriptionDAO;
    private TradingDAO tradingDAO;
    private PortfolioDAO portfolioDAO;

    public BrokerDAO(
            InstrumentsDataDAO instrumentsDataDAO,
            SubscriptionDAO subscriptionDAO,
            TradingDAO tradingDAO,
            PortfolioDAO portfolioDAO) {
        this.instrumentsDataDAO = instrumentsDataDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.tradingDAO = tradingDAO;
        this.portfolioDAO = portfolioDAO;
    }

    public InstrumentsDataDAO getInstrumentsDataDAO() {
        return instrumentsDataDAO;
    }

    public SubscriptionDAO getSubscriptionDAO() {
        return subscriptionDAO;
    }

    public TradingDAO getTradingDAO() {
        return tradingDAO;
    }

    public PortfolioDAO getPortfolioDAO() {
        return portfolioDAO;
    }
}