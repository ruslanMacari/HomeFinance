package ruslan.macari.service;

import java.util.List;
import ruslan.macari.domain.Currency;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

public interface CurrencyService {
    
    Currency add(Currency currency) throws DuplicateFieldsException;

    void update(Currency currency) throws DuplicateFieldsException;
    
    void delete(Integer id);
    
    Currency getByID(Integer id);

    List<Currency> list();
    
}
