package ru.pronin.tradeBot.indicators;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;

public class SMA implements Indicator {


    @Override
    public void addCandle(CustomCandle candle) {

    }

    @Override
    public Boolean isEnoughInformation() {
        return null;
    }

    @Override
    public Boolean isOver() {
        return null;
    }

    @Override
    public int getIndicatorDepth() {
        return 0;
    }

    @Override
    public void setOver() {

    }
}
