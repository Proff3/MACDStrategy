package ru.pronin.tradeBot.indicators.utils;

import ru.pronin.tradeBot.brokerAPI.entities.CustomCandle;

import java.util.ArrayList;
import java.util.List;

public class CandleArray {

    private final int DEPTH;
    private final List<CustomCandle> candles = new ArrayList<>();

    public CandleArray(int depth) {
        DEPTH = depth;
    }

    public List<CustomCandle> getCandles() {
        return candles;
    }

    public void add(CustomCandle candle) {
        if (candles.isEmpty()) {
            candles.add(candle);
            return;
        }
        int last = candles.size() - 1;
        if (candles.get(last).getTime().compareTo(candle.getTime()) == 0) {
            candles.set(last, candle);
            return;
        }
        if (candles.size() >= DEPTH) candles.remove(0);
        candles.add(candle);
    }

    public void remove(int index) {
        candles.remove(index);
    }
    
    public int getSize() {
        return candles.size();
    }
}
