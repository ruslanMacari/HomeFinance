package ruslan.macari.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruslan.macari.domain.Currency;
import ruslan.macari.repository.CurrencyRepository;
import ruslan.macari.service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;
    
    public Currency addCurrency(Currency currency) {
        Currency savedCurrency = currencyRepository.saveAndFlush(currency);
        return savedCurrency;
    }

    public void updateCurrency(Currency currency) {
        currencyRepository.saveAndFlush(currency);
    }

    public List<Currency> listCurrency() {
        return currencyRepository.findAll();
    }
    
}
