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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

  private final ConstraintPersist constraintPersist;
  private final CurrencyRepository currencyRepository;
  private final CurrencyRatesService currencyRatesService;
  private final CurrencyRateRepository currencyRateRepository;
  private final LocalDateAdapter localDateAdapter;

  @Override
  public void update(Currency currency) throws DuplicateFieldsException {
    log.debug("update({})", currency);
    constraintPersist
        .update(() -> currencyRepository.saveAndFlush(currency), currency.getConstraintsMap());
  }

  @Override
  public List<Currency> getAllCurrencies() {
    return currencyRepository.findAll(Sort.by(Direction.ASC, "charCode"));
  }

  @Override
  public void fillDistinctCurrencies() {
    getCurrencyRatesByDate().stream()
        .filter(currencyRateModel -> getByCode(currencyRateModel.getNumCode()) == null)
        .map(currencyRateModel -> Pair.with(currencyRateModel, Currency.builder()
            .code(currencyRateModel.getNumCode())
            .name(currencyRateModel.getCurrency())
            .charCode(currencyRateModel.getCharCode()).build()))
        .forEach(pair -> {
            addCurrency(pair);
            addCurrencyRate(pair);
        });
  }

  private List<CurrencyRateModel> getCurrencyRatesByDate() {
    LocalDate now = localDateAdapter.now();
    List<CurrencyRateModel> currencyRatesByDate = currencyRatesService.getCurrencyRatesByDate(now);
    if (CollectionUtils.isEmpty(currencyRatesByDate)) {
      log.info("No currency rates found by date: {}", now);
    }
    return currencyRatesByDate;
  }

  private void addCurrency(Pair<CurrencyRateModel, Currency> pair) {
    add(pair.getValue1());
  }

  private void addCurrencyRate(Pair<CurrencyRateModel, Currency> pair) {
    currencyRateRepository.saveAndFlush(
        CurrencyRate.builder()
            .currency(pair.getValue1())
            .rate(pair.getValue0().getRate())
            .date(pair.getValue0().getDate())
            .build());
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
