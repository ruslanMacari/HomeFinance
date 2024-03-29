// in working
package homefinance.money;

import homefinance.money.currency.entity.Currency;
import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "income")
public class Income {

  @Id
  private Date date;

  @OneToOne
  @JoinColumn(name = "currency_id")
  private Currency currency;

  private int amount;

  public Income(Date date, Currency currency, int amount) {
    this.date = date;
    this.currency = currency;
    this.amount = amount;
  }

  public Income() {
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

}
