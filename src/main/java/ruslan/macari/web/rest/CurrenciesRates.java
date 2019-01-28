package ruslan.macari.web.rest;

import java.math.BigDecimal;

public class CurrenciesRates {

    private String currency;
    private BigDecimal rate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public CurrenciesRates(String currency, BigDecimal rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public CurrenciesRates() {
    }
}
