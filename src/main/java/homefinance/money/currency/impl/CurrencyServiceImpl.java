package homefinance.money.currency.impl;

import homefinance.money.currency.Currency;
import homefinance.money.currency.CurrencyRate;
import homefinance.money.currency.CurrencyRatesService;
import homefinance.money.currency.CurrencyRepository;
import homefinance.money.currency.CurrencyService;
import homefinance.money.currency.ExchangeRatesApi;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import homefinance.util.ConstraintPersist;
import homefinance.web.exceptions.DuplicateFieldsException;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final ConstraintPersist constraintPersist;
    private final CurrencyRepository currencyRepository;
    private final CurrencyRatesService currencyRatesService;

    public CurrencyServiceImpl(
        ConstraintPersist constraintPersist, CurrencyRepository currencyRepository,
        CurrencyRatesService currencyRatesService) {
        this.constraintPersist = constraintPersist;
        this.currencyRepository = currencyRepository;
        this.currencyRatesService = currencyRatesService;
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
    public Currency getByCode(String code) {
        return this.currencyRepository.findByCode(code);
    }

    @Override
    public void fillDistinctCurrencies() {
        // TODO: 23.02.2020 RMACARI: add unit test + refactor
        List<CurrencyRate> currencyRatesByDate = this.currencyRatesService
            .getCurrencyRatesByDate(LocalDate.now());
        if (!CollectionUtils.isEmpty(currencyRatesByDate)) {
            for (CurrencyRate currencyRate : currencyRatesByDate) {
                String code = currencyRate.getNumCode();
                if (this.getByCode(code) != null) {
                    continue;
                }
                Currency currency = new Currency();
                currency.setCode(code);
                currency.setName(currencyRate.getCharCode());
                this.add(currency);
            }
        }
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
