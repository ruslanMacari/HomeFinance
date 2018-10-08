package ruslan.macari.repository;

import java.util.List;
import ruslan.macari.domain.Currency;

public interface CurrencyDAO {
   
    public void addCurrency(Currency currency);

    public void updateCurrency(Currency currency);

    public List<Currency> listCurrency();

}
