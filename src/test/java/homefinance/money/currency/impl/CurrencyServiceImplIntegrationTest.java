package homefinance.money.currency.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import homefinance.money.currency.CurrencyRateModel;
import homefinance.money.currency.CurrencyRatesService;
import homefinance.money.currency.entity.Currency;
import homefinance.common.exception.DuplicateFieldsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureTestDatabase
public class CurrencyServiceImplIntegrationTest {

  @Autowired
  private CurrencyServiceImpl currencyService;
  private CurrencyRatesService currencyRatesService;

  public CurrencyServiceImplIntegrationTest() {
    this.currencyRatesService = mock(CurrencyRatesService.class);
  }

  @Before
  public void setUp() {
    this.currencyService.getAllCurrencies().forEach(currency -> this.currencyService.delete(currency.getId()));
  }

  @Test
  public void testAdd() {
    int size = this.currencyService.getAllCurrencies().size();
    Currency currency = new Currency("USD", "840", "usd");
    this.currencyService.add(currency);
    assertEquals(this.currencyService.getAllCurrencies().size(), size + 1);
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
    assertEquals(newName, found.getName());
  }

  @Test
  public void testList() {
    int size = this.currencyService.getAllCurrencies().size();
    List<Currency> list = new ArrayList<>(3);
    list.add(new Currency("test1", "001", "test"));
    list.add(new Currency("test2", "002", "test"));
    list.add(new Currency("test3", "003", "test"));
    list.forEach(currency -> this.currencyService.add(currency));
    assertEquals(size + 3, this.currencyService.getAllCurrencies().size());
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
