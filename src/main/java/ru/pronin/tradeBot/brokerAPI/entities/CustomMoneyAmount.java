package ru.pronin.tradeBot.brokerAPI.entities;

import ru.pronin.tradeBot.brokerAPI.enums.CustomCurrency;

import java.math.BigDecimal;

public class CustomMoneyAmount {
    private CustomCurrency customCurrency = null;
    private BigDecimal value = null;

    public CustomMoneyAmount(CustomCurrency customCurrency, BigDecimal value) {
        this.customCurrency = customCurrency;
        this.value = value;
    }

    public CustomCurrency getCurrency() {
        return customCurrency;
    }

    public void setCurrency(CustomCurrency customCurrency) {
        this.customCurrency = customCurrency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
