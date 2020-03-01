// in working
package homefinance.entity;

import homefinance.money.currency.entity.Currency;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "income")
public class Income {
    
    @Id
    private Date date;
    
    @OneToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    
    private int amount;

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

    public Income(Date date, Currency currency, int amount) {
        this.date = date;
        this.currency = currency;
        this.amount = amount;
    }

    public Income() {
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
}
