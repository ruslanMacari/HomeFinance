package homefinance.money.currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Builder;

public class CurrencyRateModel {

  private String numCode;
  private String charCode;
  private String currency;
  private BigDecimal rate;
  private LocalDate date;

  public CurrencyRateModel() {
  }

  @Builder
  public CurrencyRateModel(String numCode, String charCode, double rate, LocalDate date) {
    this.numCode = numCode;
    this.charCode = charCode;
    this.rate = BigDecimal.valueOf(rate);
    this.date = date;
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getRate() {
    return this.rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public String getNumCode() {
    return this.numCode;
  }

  public void setNumCode(String numCode) {
    this.numCode = numCode;
  }

  public String getCharCode() {
    return this.charCode;
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
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final CurrencyRateModel other = (CurrencyRateModel) obj;
    if (!Objects.equals(this.numCode, other.numCode)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "CurrencyRates{" +
        "numCode='" + this.numCode + '\'' +
        ", charCode='" + this.charCode + '\'' +
        ", currency='" + this.currency + '\'' +
        ", rate=" + this.rate +
        ", date=" + date +
        '}';
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}
