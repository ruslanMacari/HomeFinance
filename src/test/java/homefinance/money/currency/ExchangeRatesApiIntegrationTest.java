package homefinance.money.currency;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import homefinance.money.currency.entity.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ExchangeRatesApiIntegrationTest {

  @Autowired
  private ExchangeRatesApi exchangeRatesApi;

  @Test
  public void test_getCurrencyRatesByDate() {
    List<CurrencyRateModel> currencyRateByDate = this.exchangeRatesApi
        .getCurrencyRatesByDate(LocalDate.of(2020, 2, 19));
    assertThat(currencyRateByDate.size(), is(42));
    this.assertValues(currencyRateByDate.get(5), new CurrencyRateModel("784", "AED", 4.8031));
    this.assertValues(currencyRateByDate.get(41), new CurrencyRateModel("960", "XDR", 24.1134));
    currencyRateByDate.forEach(System.out::println);
  }

  private void assertValues(CurrencyRateModel valueSource, CurrencyRateModel valueDestination) {
    assertThat(valueSource.getNumCode(), is(valueDestination.getNumCode()));
    assertThat(valueSource.getCharCode(), is(valueDestination.getCharCode()));
    assertThat(valueSource.getRate(), is(valueDestination.getRate()));
  }

  @Test
  public void test_getRateByDateAndCurrency() {
    Currency currency = new Currency();
    currency.setCode("978");
    BigDecimal rateByDateAndCurrency = this.exchangeRatesApi
        .getRateByDateAndCurrency(LocalDate.of(2020, 2, 19), currency);
    assertThat(rateByDateAndCurrency, is(new BigDecimal("19.0986")));
    System.out.println(rateByDateAndCurrency);
  }
}