package ru.pronin.tradeBot.brokerAPI.entities;

import ru.pronin.tradeBot.brokerAPI.enums.CustomCandleResolution;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class CustomCandle {

    //
    private Integer v = null;
    private ZonedDateTime time;
    private String figi = null;
    private CustomCandleResolution interval = null;
    private BigDecimal o = null;
    private BigDecimal c = null;
    private BigDecimal h = null;
    private BigDecimal l = null;

    public CustomCandle(String figi,
                        CustomCandleResolution interval,
                        BigDecimal o,
                        BigDecimal c,
                        BigDecimal h,
                        BigDecimal l,
                        Integer v,
                        OffsetDateTime time) {
        this.figi = figi;
        this.interval = interval;
        this.o = o;
        this.c = c;
        this.h = h;
        this.l = l;
        this.v = v;
        this.time = time.toZonedDateTime();
    }

    public CustomCandle(String figi,
                        CustomCandleResolution interval,
                        BigDecimal o,
                        BigDecimal c,
                        BigDecimal h,
                        BigDecimal l,
                        BigDecimal traidingValue,
                        ZonedDateTime dateTime) {
        this.figi = figi;
        this.interval = interval;
        this.o = o;
        this.c = c;
        this.h = h;
        this.l = l;
        this.v = traidingValue.intValue();
        this.time = dateTime;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Candle {\n");

        sb.append("    figi: ").append(toIndentedString(figi)).append("\n");
        sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
        sb.append("    o: ").append(toIndentedString(o)).append("\n");
        sb.append("    c: ").append(toIndentedString(c)).append("\n");
        sb.append("    h: ").append(toIndentedString(h)).append("\n");
        sb.append("    l: ").append(toIndentedString(l)).append("\n");
        sb.append("    v: ").append(toIndentedString(v)).append("\n");
        sb.append("    time: ").append(toIndentedString(time)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public CustomCandle(){}

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public void setV(BigDecimal v) {
        this.v = v.intValue();
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time.toZonedDateTime();
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public CustomCandleResolution getInterval() {
        return interval;
    }

    public void setInterval(CustomCandleResolution interval) {
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
}
