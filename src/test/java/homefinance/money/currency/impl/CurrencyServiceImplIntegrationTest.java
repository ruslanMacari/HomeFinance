package homefinance.money.currency.impl;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.AbstractSpringIntegrationTest;
import homefinance.common.exception.DuplicateFieldsException;
import homefinance.money.currency.CurrencyRateModel;
import homefinance.money.currency.CurrencyRatesService;
import homefinance.money.currency.CurrencyRepository;
import homefinance.money.currency.entity.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
// TODO: 021, 21-Nov-22 ruslan.macari: replace with test containers
public class CurrencyServiceImplIntegrationTest extends AbstractSpringIntegrationTest {

  @Autowired
  private CurrencyServiceImpl currencyService;
  private CurrencyRatesService currencyRatesService;
  @Autowired
  private CurrencyRepository currencyRepository;

  public CurrencyServiceImplIntegrationTest() {
    this.currencyRatesService = mock(CurrencyRatesService.class);
  }

  @BeforeEach
  public void setUp() {
    this.currencyRepository.deleteAll();
  }

  @Test
  public void testAdd() {
    Currency currency = new Currency("USD", "840", "usd");
    this.currencyService.add(currency);
    then(this.currencyService.getAllCurrencies().size()).isEqualTo(1);
    try {
      this.currencyService.add(new Currency("USD", "840", "usd"));
      fail("Exception must be thrown");
    } catch (Exception e) {
      if (!(e instanceof DuplicateFieldsException)) {
        fail("DuplicateFieldsException must be thrown");
      }
    }
  }

  @Test
  public void testUpdate() {
    Currency currency = new Currency("USD", "840", "usd");
    this.currencyService.add(currency);
    String newName = "updated";
    currency.setName(newName);
    this.currencyService.update(currency);
    Currency found = this.currencyService.getByID(currency.getId());
    then(found.getName()).isEqualTo(newName);
  }

  @Test
  public void getAllCurrencies_given3Currencies_thenGetAllCurrenciesSortedByCharCode() {
    // given:
    Currency currency1 = new Currency("test1", "001", "test1");
    Currency currency2 = new Currency("test2", "002", "test2");
    Currency currency3 = new Currency("test3", "003", "test3");
    List<Currency> list = new ArrayList<>(3);
    list.add(currency2);
    list.add(currency3);
    list.add(currency1);
    list.forEach(currency -> this.currencyService.add(currency));
    // when:
    List<Currency> actual = this.currencyService.getAllCurrencies();
    then(actual.size()).isEqualTo(3);
    then(actual.get(0)).isEqualTo(currency1);
    then(actual.get(1)).isEqualTo(currency2);
    then(actual.get(2)).isEqualTo(currency3);
  }

  @Test
  public void when_fillDistinctCurrencies_and_currencyRatesIsEmpty_thenDoNothing() {
    this.currencyService.setCurrencyRatesService(this.currencyRatesService);
    when(this.currencyRatesService.getCurrencyRatesByDate(any())).thenReturn(null);
    this.currencyService.fillDistinctCurrencies();
    assertTrue(this.currencyService.getAllCurrencies().isEmpty());

    when(this.currencyRatesService.getCurrencyRatesByDate(any())).thenReturn(new ArrayList<>());
    this.currencyService.fillDistinctCurrencies();
    assertTrue(this.currencyService.getAllCurrencies().isEmpty());
  }

  @Test
  public void when_fillDistinctCurrencies_and_currencyRatesIsFilled_thenAddCurrency() {
    this.currencyService.setCurrencyRatesService(this.currencyRatesService);
    CurrencyRateModel currencyRateModel = new CurrencyRateModel();
    currencyRateModel.setNumCode("test1");
    currencyRateModel.setCharCode("test");
    currencyRateModel.setCurrency("Test currency");
    currencyRateModel.setRate(BigDecimal.valueOf(10.02));
    currencyRateModel.setDate(LocalDate.now());
    when(this.currencyRatesService.getCurrencyRatesByDate(any())).thenReturn(Arrays.asList(
        currencyRateModel));
    this.currencyService.fillDistinctCurrencies();
    List<Currency> currencies = this.currencyService.getAllCurrencies();
    assertThat(currencies.size(), is(1));
    assertThat(currencies.get(0).getCode(), is("test1"));
    assertThat(currencies.get(0).getCharCode(), is("test"));
    assertThat(currencies.get(0).getName(), is("Test currency"));

    // no new currencies must be added
    this.currencyService.fillDistinctCurrencies();
    currencies = this.currencyService.getAllCurrencies();
    assertThat(currencies.size(), is(1));
    assertThat(currencies.get(0).getCode(), is("test1"));
    assertThat(currencies.get(0).getCharCode(), is("test"));
    assertThat(currencies.get(0).getName(), is("Test currency"));
  }

}
