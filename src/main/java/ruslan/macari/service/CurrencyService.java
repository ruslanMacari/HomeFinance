package ruslan.macari.service;

import java.util.List;
import ruslan.macari.domain.Currency;

public interface CurrencyService {
    
    Currency addCurrency(Currency currency);

    void updateCurrency(Currency currency);

    List<Currency> listCurrency();
    
}
