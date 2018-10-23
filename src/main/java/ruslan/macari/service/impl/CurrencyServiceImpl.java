package ruslan.macari.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruslan.macari.domain.Currency;
import ruslan.macari.service.repository.CurrencyRepository;
import ruslan.macari.service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;
    
    @Override
    public Currency add(Currency currency) {
        Currency savedCurrency = currencyRepository.saveAndFlush(currency);
        return savedCurrency;
    }

    @Override
    public void update(Currency currency) {
        currencyRepository.saveAndFlush(currency);
    }

    @Override
    public List<Currency> list() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency getByID(int id) {
        return currencyRepository.findOne(id);
    }
    
}
