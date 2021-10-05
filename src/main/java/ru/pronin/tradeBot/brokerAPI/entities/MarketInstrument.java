package ru.pronin.tradeBot.brokerAPI.entities;

public class MarketInstrument {
    private String figi;
    private String ticker;
    private String isin;
    private Currency currency;
    private String name;

    public MarketInstrument(String figi, String ticker, String isin, Currency currency, String name) {
        this.figi = figi;
        this.ticker = ticker;
        this.isin = isin;
        this.currency = currency;
        this.name = name;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
