package ruslan.macari.service;

import java.util.List;
import ruslan.macari.domain.Currency;

public interface CurrencyService {
    
    Currency add(Currency currency);

    void update(Currency currency);
    
    Currency getByID(int id);

    List<Currency> list();
    
}
