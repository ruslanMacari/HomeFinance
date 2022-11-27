package homefinance.money.currency.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "currency_rate")
public class CurrencyRate {

  private LocalDate date;
  private BigDecimal rate;
  private int id;

  @ManyToOne
  @JoinColumn(name = "currency_id", nullable = false)
  private Currency currency;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Builder
  public CurrencyRate(Currency currency, BigDecimal rate, LocalDate date) {
    this.currency = currency;
    this.rate = rate;
    this.date = date;
  }

  public CurrencyRate() {
  }

  @Column(name = "rate", nullable = false)
  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  @Column(name = "date", nullable = false)
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CurrencyRate that = (CurrencyRate) o;
    return rate.equals(that.rate) && id == that.id && date.equals(that.date) && currency
        .equals(that.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, rate, id, currency);
  }

  @Override
  public String toString() {
    return "CurrencyRate{" +
        "date=" + date +
        ", rate=" + rate +
        ", id=" + id +
        ", currency=" + currency +
        '}';
  }
}
