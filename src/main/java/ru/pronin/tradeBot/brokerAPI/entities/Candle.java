package ru.pronin.tradeBot.brokerAPI.entities;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Candle {

    private String figi = null;
    private CandleResolution interval = null;
    private BigDecimal o = null;
    private BigDecimal c = null;
    private BigDecimal h = null;
    private BigDecimal l = null;
    private Integer v = null;
    private OffsetDateTime time = null;

    public Candle(String figi, CandleResolution interval, BigDecimal o, BigDecimal c, BigDecimal h, BigDecimal l, Integer v, OffsetDateTime time) {
        this.figi = figi;
        this.interval = interval;
        this.o = o;
        this.c = c;
        this.h = h;
        this.l = l;
        this.v = v;
        this.time = time;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public CandleResolution getInterval() {
        return interval;
    }

    public void setInterval(CandleResolution interval) {
        this.interval = interval;
    }

    public BigDecimal getO() {
        return o;
    }

    public void setO(BigDecimal o) {
        this.o = o;
    }

    public BigDecimal getC() {
        return c;
    }

    public void setC(BigDecimal c) {
        this.c = c;
    }

    public BigDecimal getH() {
        return h;
    }

    public void setH(BigDecimal h) {
        this.h = h;
    }

    public BigDecimal getL() {
        return l;
    }

    public void setL(BigDecimal l) {
        this.l = l;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }
}
