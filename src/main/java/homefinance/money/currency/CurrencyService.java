package homefinance.money.currency;

import homefinance.web.exceptions.DuplicateFieldsException;
import java.util.List;

public interface CurrencyService {
    
    Currency add(Currency currency) throws DuplicateFieldsException;

    void update(Currency currency) throws DuplicateFieldsException;
    
    void delete(Integer id);
    
    Currency getByID(Integer id);

    List<Currency> list();

    Currency getByCode(String code);

    void fillDistinctCurrencies();
}
