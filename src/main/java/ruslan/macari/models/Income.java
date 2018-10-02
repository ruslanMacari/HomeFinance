package ruslan.macari.models;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Income")
public class Income {
    
    @Id
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "currency_id",
            foreignKey = @ForeignKey(name = "CURRENCY_ID_FK")
    )
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
