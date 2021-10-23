package ru.pronin.tradeBot.brokerAPI;

import ru.pronin.tradeBot.brokerAPI.entities.CustomPortfolio;

import java.util.concurrent.ExecutionException;

public interface PortfolioDAO {
    CustomPortfolio getPortfolio() throws ExecutionException, InterruptedException;
}
