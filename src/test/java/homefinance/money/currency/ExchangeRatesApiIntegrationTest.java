package homefinance.money.currency;

import static org.junit.Assert.assertFalse;

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
  public void test() {
    List<CurrencyRates> currencyRatesByDate = this.exchangeRatesApi
        .getCurrencyRatesByDate(LocalDate.of(2020, 2, 19));
    assertFalse(currencyRatesByDate.isEmpty());
    currencyRatesByDate.forEach(System.out::println);

  }
}