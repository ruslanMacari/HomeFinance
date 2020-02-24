package homefinance.money.currency.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.money.currency.Currency;
import homefinance.money.currency.CurrencyRate;
import homefinance.money.currency.CurrencyRatesService;
import homefinance.web.exceptions.DuplicateFieldsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class CurrencyServiceImplIntegrationTest {

  @Autowired
  private CurrencyServiceImpl currencyService;
  private CurrencyRatesService currencyRatesService;

  public CurrencyServiceImplIntegrationTest() {
    this.currencyRatesService = mock(CurrencyRatesService.class);
  }

  @Before
  public void setUp() {
    this.currencyService.list().forEach(currency -> this.currencyService.delete(currency.getId()));
  }

  @Test
  public void testAdd() {
    int size = this.currencyService.list().size();
    Currency currency = new Currency("USD", "840");
    this.currencyService.add(currency);
    assertEquals(this.currencyService.list().size(), size + 1);
    try {
      this.currencyService.add(new Currency("USD", "840"));
      fail("Exception must be thrown");
    } catch (Exception e) {
      if (!(e instanceof DuplicateFieldsException)) {
        fail("DuplicateFieldsException must be thrown");
      }
    }
  }

  @Test
  public void testUpdate() {
    Currency currency = new Currency("USD", "840");
    this.currencyService.add(currency);
    String newName = "updated";
    currency.setName(newName);
    this.currencyService.update(currency);
    Currency found = this.currencyService.getByID(currency.getId());
    assertEquals(newName, found.getName());
  }

  @Test
  public void testList() {
    int size = this.currencyService.list().size();
    List<Currency> list = new ArrayList<>(3);
    list.add(new Currency("test1", "1"));
    list.add(new Currency("test2", "2"));
    list.add(new Currency("test3", "3"));
    list.forEach(currency -> this.currencyService.add(currency));
    assertEquals(size + 3, this.currencyService.list().size());
  }

  @Test
  public void when_fillDistinctCurrencies_and_currencyRatesIsEmpty_thenDoNothing() {
    this.currencyService.setCurrencyRatesService(this.currencyRatesService);
    when(this.currencyRatesService.getCurrencyRatesByDate(any())).thenReturn(null);
    this.currencyService.fillDistinctCurrencies();
    assertTrue(this.currencyService.list().isEmpty());

    when(this.currencyRatesService.getCurrencyRatesByDate(any())).thenReturn(new ArrayList<>());
    this.currencyService.fillDistinctCurrencies();
    assertTrue(this.currencyService.list().isEmpty());
  }

  @Test
  public void when_fillDistinctCurrencies_and_currencyRatesIsFilled_thenAddCurrency() {
    this.currencyService.setCurrencyRatesService(this.currencyRatesService);
    CurrencyRate currencyRate = new CurrencyRate();
    currencyRate.setNumCode("test1");
    currencyRate.setCharCode("test");
    currencyRate.setCurrency("Test currency");
    when(this.currencyRatesService.getCurrencyRatesByDate(any())).thenReturn(Arrays.asList(currencyRate));
    this.currencyService.fillDistinctCurrencies();
    List<Currency> currencies = this.currencyService.list();
    assertThat(currencies.size(), is(1));
    assertThat(currencies.get(0).getCode(), is("test1"));
    assertThat(currencies.get(0).getName(), is("Test currency"));
  }

  // TODO: 24.02.2020 RMACARI: test distinct add
}
