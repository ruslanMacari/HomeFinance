package ruslan.macari.web.rest;

import java.math.BigDecimal;
import java.util.Objects;

public class CurrenciesRates {

    private String numCode;
    private String charCode;
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

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.numCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CurrenciesRates other = (CurrenciesRates) obj;
        if (!Objects.equals(this.numCode, other.numCode)) {
            return false;
        }
        return true;
    }
    
    
    
}
