package ruslan.macari.dao;

import java.util.List;
import ruslan.macari.models.Currency;

public interface CurrencyDAO {
   
    public void addCurrency(Currency currency);

    public void updateCurrency(Currency currency);

    public List<Currency> listCurrency();

}
