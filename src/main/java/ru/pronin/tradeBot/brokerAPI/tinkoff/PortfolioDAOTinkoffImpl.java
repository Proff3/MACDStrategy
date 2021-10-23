package ru.pronin.tradeBot.brokerAPI.tinkoff;

import ru.pronin.tradeBot.brokerAPI.PortfolioDAO;
import ru.pronin.tradeBot.brokerAPI.entities.CustomMoneyAmount;
import ru.pronin.tradeBot.brokerAPI.entities.CustomPortfolio;
import ru.pronin.tradeBot.brokerAPI.entities.CustomPortfolioPosition;
import ru.pronin.tradeBot.brokerAPI.enums.CustomCurrency;
import ru.pronin.tradeBot.brokerAPI.enums.CustomInstrumentType;
import ru.tinkoff.invest.openapi.PortfolioContext;
import ru.tinkoff.invest.openapi.model.rest.Portfolio;
import ru.tinkoff.invest.openapi.model.rest.PortfolioPosition;

import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class PortfolioDAOTinkoffImpl implements PortfolioDAO {

    private String brokerAccountID;
    private PortfolioContext PORTFOLIO;

    @Override
    public CustomPortfolio getPortfolio() throws ExecutionException, InterruptedException {
        Portfolio originPortfolio = PORTFOLIO.getPortfolio(brokerAccountID).join();
        return fromOriginPortfolioToCustom(originPortfolio);
    }

    public void setBrokerAccountID(String brokerAccountID) {
        this.brokerAccountID = brokerAccountID;
    }

    public void setPORTFOLIO(PortfolioContext PORTFOLIO) {
        this.PORTFOLIO = PORTFOLIO;
    }

    private CustomPortfolio fromOriginPortfolioToCustom(Portfolio portfolio){
        CustomPortfolio customPortfolio = new CustomPortfolio();
        customPortfolio.setPositions(
                portfolio.getPositions()
                        .stream()
                        .map(PortfolioDAOTinkoffImpl::fromOriginPortfolioPositionToCustom)
                        .collect(Collectors.toList())
        );
        return customPortfolio;
    }

    private static CustomPortfolioPosition fromOriginPortfolioPositionToCustom(PortfolioPosition portfolioPosition){
        CustomPortfolioPosition customPortfolioPosition = new CustomPortfolioPosition();
        customPortfolioPosition.setName(portfolioPosition.getName());
        customPortfolioPosition.setTicker(portfolioPosition.getTicker());
        customPortfolioPosition.setFigi(portfolioPosition.getFigi());
        customPortfolioPosition.setIsin(portfolioPosition.getIsin());
        customPortfolioPosition.setLots(portfolioPosition.getLots());
        customPortfolioPosition.setBalance(portfolioPosition.getBalance());
        customPortfolioPosition.setBlocked(portfolioPosition.getBlocked());
        customPortfolioPosition.setInstrumentType(CustomInstrumentType.fromValue(portfolioPosition.getInstrumentType().toString()));
        if (portfolioPosition.getAveragePositionPrice() != null) {
            CustomMoneyAmount averagePositionPrice = new CustomMoneyAmount(
                    CustomCurrency.fromValue(portfolioPosition.getAveragePositionPrice().getCurrency().getValue()),
                    portfolioPosition.getAveragePositionPrice().getValue()
            );
            customPortfolioPosition.setAveragePositionPrice(averagePositionPrice);
        }
        if (portfolioPosition.getAveragePositionPriceNoNkd() != null){
            CustomMoneyAmount averagePositionPriceNoNkd = new CustomMoneyAmount(
                    CustomCurrency.fromValue(portfolioPosition.getAveragePositionPriceNoNkd().getCurrency().getValue()),
                    portfolioPosition.getAveragePositionPriceNoNkd().getValue()
            );
            customPortfolioPosition.setAveragePositionPriceNoNkd(averagePositionPriceNoNkd);
        }
        if (portfolioPosition.getExpectedYield() != null){
            CustomMoneyAmount expectedYield = new CustomMoneyAmount(
                    CustomCurrency.fromValue(portfolioPosition.getExpectedYield().getCurrency().getValue()),
                    portfolioPosition.getExpectedYield().getValue()
            );
            customPortfolioPosition.setExpectedYield(expectedYield);
        }
        return customPortfolioPosition;
    }

}
