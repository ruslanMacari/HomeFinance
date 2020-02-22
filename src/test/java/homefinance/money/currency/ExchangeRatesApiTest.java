package homefinance.money.currency;

import org.junit.Test;

public class ExchangeRatesApiTest {

  private ExchangeRatesApi exchangeRatesApi;

  public ExchangeRatesApiTest() {
    this.exchangeRatesApi = new ExchangeRatesApi();
  }

  @Test(expected = IllegalArgumentException.class)
  public void when_getCurrencyRatesByDate_andDateIsNull_thenExpectIllegalArgumentException() {
    this.exchangeRatesApi.getCurrencyRatesByDate(null);
  }

}