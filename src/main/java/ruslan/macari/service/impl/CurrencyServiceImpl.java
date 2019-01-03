package ruslan.macari.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruslan.macari.domain.Currency;
import ruslan.macari.service.repository.CurrencyRepository;
import ruslan.macari.service.CurrencyService;
import ruslan.macari.util.ConstraintPersist;
import ruslan.macari.web.exceptions.DuplicateFieldsException;

@Service
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
        return currencyRepository.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        currencyRepository.delete(id);
    }

}
