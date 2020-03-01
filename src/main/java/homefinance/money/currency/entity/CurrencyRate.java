package homefinance.money.currency.entity;

import homefinance.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "currency_rate")
public class CurrencyRate extends BaseEntity {
  private int currencyId;

  public int getCurrencyId() {
    return this.currencyId;
  }

  public CurrencyRate setCurrencyId(int currencyId) {
    this.currencyId = currencyId;
    return this;
  }
}
