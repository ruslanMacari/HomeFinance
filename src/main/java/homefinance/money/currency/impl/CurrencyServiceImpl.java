package homefinance.money.currency.impl;

import homefinance.common.beans.LocalDateAdapter;
import homefinance.common.exception.DuplicateFieldsException;
import homefinance.common.util.ConstraintPersist;
import homefinance.money.currency.CurrencyRateModel;
import homefinance.money.currency.CurrencyRateRepository;
import homefinance.money.currency.CurrencyRatesService;
import homefinance.money.currency.CurrencyRepository;
import homefinance.money.currency.CurrencyService;
import homefinance.money.currency.entity.Currency;
import homefinance.money.currency.entity.CurrencyRate;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
  private final CurrencyRateRepository currencyRateRepository;
  private final LocalDateAdapter localDateAdapter;

  @Autowired
  public void setCurrencyRatesService(CurrencyRatesService currencyRatesService) {
    this.currencyRatesService = currencyRatesService;
  }

  public CurrencyServiceImpl(
      ConstraintPersist constraintPersist, CurrencyRepository currencyRepository,
      CurrencyRatesService currencyRatesService,
      CurrencyRateRepository currencyRateRepository, LocalDateAdapter localDateAdapter) {
    this.constraintPersist = constraintPersist;
    this.currencyRepository = currencyRepository;
    this.currencyRatesService = currencyRatesService;
    this.currencyRateRepository = currencyRateRepository;
    this.localDateAdapter = localDateAdapter;
  }

  @Override
  public void update(Currency currency) throws DuplicateFieldsException {
    logger.debug("update({})", currency);
    constraintPersist
        .update(() -> currencyRepository.saveAndFlush(currency), currency.getConstraintsMap());
  }

  @Override
  public List<Currency> getAllCurrencies() {
    return currencyRepository.findAll(Sort.by(Direction.ASC, "charCode"));
  }

  @Override
  public void fillDistinctCurrencies() {
    LocalDate now = localDateAdapter.now();
    List<CurrencyRateModel> currencyRatesByDate = currencyRatesService.getCurrencyRatesByDate(now);
    if (CollectionUtils.isEmpty(currencyRatesByDate)) {
      logger.info("No currency rates found by date: {}", now);
      return;
    }
    currencyRatesByDate.stream()
        .filter(currencyRate -> getByCode(currencyRate.getNumCode()) == null)
        .forEach(currencyRateModel -> {
          Currency currency = new Currency();
          currency.setCode(currencyRateModel.getNumCode());
          currency.setName(currencyRateModel.getCurrency());
          currency.setCharCode(currencyRateModel.getCharCode());
          add(currency);
          currencyRateRepository.saveAndFlush(
              new CurrencyRate(currency, currencyRateModel.getRate(), currencyRateModel.getDate()));
        });
  }

  @Override
  public Currency add(Currency currency) throws DuplicateFieldsException {
    return (Currency) constraintPersist
        .add(() -> currencyRepository.saveAndFlush(currency), currency.getConstraintsMap());
  }

  @Override
  public Currency getByCode(String code) {
    return currencyRepository.findByCode(code);
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
