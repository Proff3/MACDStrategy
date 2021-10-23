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

    public void setInstrumentsDataDAO(InstrumentsDataDAO instrumentsDataDAO) {
        this.instrumentsDataDAO = instrumentsDataDAO;
    }

    public SubscriptionDAO getSubscriptionDAO() {
        return subscriptionDAO;
    }

    public void setSubscriptionDAO(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
    }

    public TradingDAO getTradingDAO() {
        return tradingDAO;
    }

    public void setTradingDAO(TradingDAO tradingDAO) {
        this.tradingDAO = tradingDAO;
    }

    public PortfolioDAO getPortfolioDAO() {
        return portfolioDAO;
    }

    public void setPortfolioDAO(PortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }
}