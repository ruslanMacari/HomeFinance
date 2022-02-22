package homefinance.money.currency.impl;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import homefinance.common.beans.LocalDateAdapter;
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
import java.util.Collections;
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

  private final LocalDate date;
  @Mock
  private ConstraintPersist constraintPersist;
  @Mock
  private CurrencyRepository currencyRepositoryMock;
  @Mock
  private CurrencyRatesService ratesService;
  private CurrencyServiceImpl service;
  @Captor
  private ArgumentCaptor<Supplier<ConstraintEntity>> lambdaCaptor;
  @Mock
  private CurrencyRateRepository currencyRateRepositoryMock;
  @Mock
  private LocalDateAdapter localDateAdapterMock;

  public CurrencyServiceImplTest() {
    date = LocalDate.now();
  }

  @Before
  public void setUp() throws Exception {
    service = new CurrencyServiceImpl(constraintPersist,
        currencyRepositoryMock,
        ratesService,
        currencyRateRepositoryMock,
        localDateAdapterMock);
  }

  @Test
  public void fillDistinctCurrencies_givenOneCurrencyRateModel_shouldAddCurrencyAndCurrencyRate() {
    //given:
    given(localDateAdapterMock.now()).willReturn(date);
    CurrencyRateModel model = CurrencyRateModel.builder()
        .numCode("111")
        .charCode("USD")
        .rate(100.1)
        .date(date).build();
    Currency usd = Currency.builder()
        .code(model.getNumCode())
        .name(model.getCurrency())
        .charCode(model.getCharCode())
        .build();
    given(ratesService.getCurrencyRatesByDate(date)).willReturn(Arrays.asList(model));
    //when:
    service.fillDistinctCurrencies();
    //then:
    //test add currency
    then(constraintPersist).should().add(any(), eq(usd.getConstraintsMap()));
    then(constraintPersist).should().add(lambdaCaptor.capture(), eq(usd.getConstraintsMap()));
    Supplier<ConstraintEntity> lambda = lambdaCaptor.getValue();
    lambda.get();
    then(currencyRepositoryMock).should().saveAndFlush(eq(usd));
    //test add currency rate
    then(currencyRateRepositoryMock).should().saveAndFlush(eq(CurrencyRate.builder()
        .rate(BigDecimal.valueOf(100.1))
        .currency(usd)
        .date(date)
        .build()));
  }

  @Test
  public void fillDistinctCurrencies_givenNoCurrencyRatesFound_shouldDoNothing() {
    //given:
    given(localDateAdapterMock.now()).willReturn(date);
    given(ratesService.getCurrencyRatesByDate(date)).willReturn(Collections.emptyList());
    //when:
    service.fillDistinctCurrencies();
    //then:
    then(currencyRateRepositoryMock).shouldHaveNoInteractions();
  }

  @Test
  public void fillDistinctCurrencies_givenCurrencyRatesWithUsedCurrency_shouldDoNothing() {
    //given:
    given(localDateAdapterMock.now()).willReturn(date);
    CurrencyRateModel currencyRateModel = CurrencyRateModel.builder()
        .numCode("111")
        .charCode("USD")
        .rate(100.1)
        .date(date).build();
    given(ratesService.getCurrencyRatesByDate(date)).willReturn(singletonList(currencyRateModel));
    given(currencyRepositoryMock.findByCode("111")).willReturn(new Currency());
    //when:
    service.fillDistinctCurrencies();
    //then:
    then(currencyRateRepositoryMock).shouldHaveNoInteractions();
  }
}
