package ruslan.macari.service;

import java.util.List;
import ruslan.macari.domain.Currency;

public interface CurrencyService {
    
    Currency add(Currency currency);

    void update(Currency currency);
    
    void delete(Integer id);
    
    Currency getByID(Integer id);

    List<Currency> list();
    
}
