package homefinance.money.currency;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

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
    List<CurrencyRate> currencyRateByDate = this.exchangeRatesApi
        .getCurrencyRatesByDate(LocalDate.of(2020, 2, 19));
    assertFalse(currencyRateByDate.isEmpty());
    currencyRateByDate.forEach(System.out::println);
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