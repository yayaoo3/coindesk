package com.oswin.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="ExchangeRates")
public class ForeignCurrency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "symbol")
    private String symbol;
    @Column(name = "rate")
    private String rate;
    @Column(name = "description")
    private String description;
    @Column(name = "rate_float")
    private double rate_float;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate_float() {
        return rate_float;
    }

    public void setRate_float(double rate_float) {
        this.rate_float = rate_float;
    }

    @Override
    public String toString() {
        return "ForeignCurrency{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", symbol='" + symbol + '\'' +
                ", rate='" + rate + '\'' +
                ", description='" + description + '\'' +
                ", rate_float=" + rate_float +
                '}';
    }
}
