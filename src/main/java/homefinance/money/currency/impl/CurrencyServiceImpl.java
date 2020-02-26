package homefinance.money.currency.impl;

import homefinance.money.currency.Currency;
import homefinance.money.currency.CurrencyRate;
import homefinance.money.currency.CurrencyRatesService;
import homefinance.money.currency.CurrencyRepository;
import homefinance.money.currency.CurrencyService;
import homefinance.util.ConstraintPersist;
import homefinance.web.exceptions.DuplicateFieldsException;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

  private final ConstraintPersist constraintPersist;
  private final CurrencyRepository currencyRepository;
  private CurrencyRatesService currencyRatesService;
  private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

  @Autowired
  public void setCurrencyRatesService(CurrencyRatesService currencyRatesService) {
    this.currencyRatesService = currencyRatesService;
  }

  public CurrencyServiceImpl(
      ConstraintPersist constraintPersist, CurrencyRepository currencyRepository,
      CurrencyRatesService currencyRatesService) {
    this.constraintPersist = constraintPersist;
    this.currencyRepository = currencyRepository;
    this.currencyRatesService = currencyRatesService;
  }

  @Override
  public void update(Currency currency) throws DuplicateFieldsException {
      this.constraintPersist
        .update(() -> this.currencyRepository.saveAndFlush(currency), currency.getConstraintsMap());
  }

  @Override
  public List<Currency> list() {
    return this.currencyRepository.findAll();
  }

  @Override
  public void fillDistinctCurrencies() {
    LocalDate now = LocalDate.now();
    List<CurrencyRate> currencyRatesByDate = this.currencyRatesService
        .getCurrencyRatesByDate(now);
    if (CollectionUtils.isEmpty(currencyRatesByDate)) {
      logger.info("No currency rates found by date: {}", now);
      return;
    }
    currencyRatesByDate.stream()
        .filter(currencyRate -> this.getByCode(currencyRate.getNumCode()) == null)
        .forEach(this::addCurrency);
  }

  private void addCurrency(CurrencyRate currencyRate) {
    Currency currency = new Currency();
    currency.setCode(currencyRate.getNumCode());
    currency.setName(currencyRate.getCurrency());
    currency.setCharCode(currencyRate.getCharCode());
    this.add(currency);
  }

  @Override
  public Currency add(Currency currency) throws DuplicateFieldsException {
    return (Currency) this.constraintPersist
        .add(() -> this.currencyRepository.saveAndFlush(currency), currency.getConstraintsMap());
  }

  @Override
  public Currency getByCode(String code) {
    return this.currencyRepository.findByCode(code);
  }

  @Override
  public Currency getByID(Integer id) {
    return this.currencyRepository.findById(id).orElse(null);
  }

  @Override
  public void delete(Integer id) {
      this.currencyRepository.deleteById(id);
  }

}
