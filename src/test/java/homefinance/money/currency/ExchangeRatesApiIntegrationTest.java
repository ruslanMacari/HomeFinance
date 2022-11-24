package homefinance.money.currency;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import homefinance.AbstractSpringIntegrationTest;
import homefinance.money.currency.entity.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
// TODO: 021, 21-Nov-22 ruslan.macari: add test containers
public class ExchangeRatesApiIntegrationTest extends AbstractSpringIntegrationTest {

  @Autowired
  private ExchangeRatesApi exchangeRatesApi;

  @Test
  public void test_getCurrencyRatesByDate() {
    LocalDate date = LocalDate.of(2020, 2, 19);
    List<CurrencyRateModel> currencyRateByDate = this.exchangeRatesApi
        .getCurrencyRatesByDate(date);
    assertThat(currencyRateByDate.size(), is(42));
    this.assertValues(currencyRateByDate.get(5), new CurrencyRateModel("784", "AED", 4.8031, date));
    this.assertValues(currencyRateByDate.get(41), new CurrencyRateModel("960", "XDR", 24.1134, date));
    currencyRateByDate.forEach(System.out::println);
  }

  private void assertValues(CurrencyRateModel source, CurrencyRateModel destination) {
    assertThat(source.getNumCode(), is(destination.getNumCode()));
    assertThat(source.getCharCode(), is(destination.getCharCode()));
    assertThat(source.getRate(), is(destination.getRate()));
    assertThat(source.getDate(), is(destination.getDate()));
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
