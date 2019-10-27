package homefinance.service;

import homefinance.web.exceptions.DuplicateFieldsException;
import java.util.List;
import homefinance.domain.Currency;

public interface CurrencyService {
    
    Currency add(Currency currency) throws DuplicateFieldsException;

    void update(Currency currency) throws DuplicateFieldsException;
    
    void delete(Integer id);
    
    Currency getByID(Integer id);

    List<Currency> list();
    
}
