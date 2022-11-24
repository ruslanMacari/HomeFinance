package homefinance.money.currency;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ExchangeRatesApiTest {

  private ExchangeRatesApi exchangeRatesApi;

  public ExchangeRatesApiTest() {
    this.exchangeRatesApi = new ExchangeRatesApi();
  }

  @Test()
  public void when_getCurrencyRatesByDate_andDateIsNull_thenExpectIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> this.exchangeRatesApi.getCurrencyRatesByDate(null));
  }

}
