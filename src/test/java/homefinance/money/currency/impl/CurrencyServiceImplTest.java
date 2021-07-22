package homefinance.money.currency.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import homefinance.common.entity.ConstraintEntity;
import homefinance.common.util.ConstraintPersist;
import homefinance.money.currency.CurrencyRateModel;
import homefinance.money.currency.CurrencyRateRepository;
import homefinance.money.currency.CurrencyRatesService;
import homefinance.money.currency.CurrencyRepository;
import homefinance.money.currency.entity.Currency;
import homefinance.money.currency.entity.CurrencyRate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

  @Mock
  private ConstraintPersist constraintPersist;
  @Mock
  private CurrencyRepository repository;
  @Mock
  private CurrencyRatesService ratesService;
  private CurrencyServiceImpl service;
  @Captor
  private ArgumentCaptor<Supplier<ConstraintEntity>> lambdaCaptor;
  @Mock
  private CurrencyRateRepository currencyRateRepository;

  @Before
  public void setUp() throws Exception {
    service = new CurrencyServiceImpl(constraintPersist, repository, ratesService,
        currencyRateRepository);
  }

  @Test
  public void fillDistinctCurrencies_givenOneCurrencyRateModel_shouldAddCurrencyAndCurrencyRate() {
    //given:
    LocalDate date = LocalDate.now();
    CurrencyRateModel model = new CurrencyRateModel("111", "USD", 100.1, date);
    Currency usd = new Currency();
    usd.setCode(model.getNumCode());
    usd.setName(model.getCurrency());
    usd.setCharCode(model.getCharCode());
    given(ratesService.getCurrencyRatesByDate(date)).willReturn(Arrays.asList(model));
    //when:
    service.fillDistinctCurrencies();
    //then:
    //test add currency
    then(constraintPersist).should().add(any(), eq(usd.getConstraintsMap()));
    then(constraintPersist).should().add(lambdaCaptor.capture(), eq(usd.getConstraintsMap()));
    Supplier<ConstraintEntity> lambda = lambdaCaptor.getValue();
    lambda.get();
    then(repository).should().saveAndFlush(eq(usd));
    //test add currency rate
    then(currencyRateRepository).should().saveAndFlush(eq(new CurrencyRate(usd, new BigDecimal(
        "100.1"), date)));
  }
}
