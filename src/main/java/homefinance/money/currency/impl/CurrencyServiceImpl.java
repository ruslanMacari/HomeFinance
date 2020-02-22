package homefinance.money.currency.impl;

import homefinance.money.currency.Currency;
import homefinance.money.currency.CurrencyRepository;
import homefinance.money.currency.CurrencyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import homefinance.util.ConstraintPersist;
import homefinance.web.exceptions.DuplicateFieldsException;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private ConstraintPersist constraintPersist;
    private CurrencyRepository currencyRepository;
    
    @Autowired
    public void setConstraintPersist(ConstraintPersist constraintPersist) {
        this.constraintPersist = constraintPersist;
    }
    
    @Autowired
    public void setCurrencyRepository(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    
    @Override
    public Currency add(Currency currency) throws DuplicateFieldsException{
        return (Currency) constraintPersist.add(() -> currencyRepository.saveAndFlush(currency), currency.getConstraintsMap());
    }

    @Override
    public void update(Currency currency) throws DuplicateFieldsException {
        constraintPersist.update(() -> currencyRepository.saveAndFlush(currency), currency.getConstraintsMap());
    }

    @Override
    public List<Currency> list() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency getByID(Integer id) {
        return currencyRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        currencyRepository.deleteById(id);
    }

}
