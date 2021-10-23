package ru.pronin.tradeBot.brokerAPI;

import ru.pronin.tradeBot.brokerAPI.entities.CustomPortfolio;
import ru.pronin.tradeBot.brokerAPI.entities.CustomPortfolioPosition;

import java.util.concurrent.ExecutionException;

public interface PortfolioDAO {
    CustomPortfolio getPortfolio();
}
