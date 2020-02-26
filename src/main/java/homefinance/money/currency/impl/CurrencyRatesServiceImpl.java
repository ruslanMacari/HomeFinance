package homefinance.money.currency.impl;

import homefinance.money.currency.Currency;
import homefinance.money.currency.CurrencyRate;
import homefinance.money.currency.CurrencyRatesService;
import homefinance.money.currency.ExchangeRatesApi;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

  private final ExchangeRatesApi exchangeRatesApi;

  public CurrencyRatesServiceImpl(ExchangeRatesApi exchangeRatesApi) {
    this.exchangeRatesApi = exchangeRatesApi;
  }

  @Override
  public List<CurrencyRate> getCurrencyRatesByDate(LocalDate date) {
    return this.exchangeRatesApi.getCurrencyRatesByDate(date);
  }

  @Override
  public BigDecimal getRateByDateAndCurrency(LocalDate date, Currency currency) {
    return this.exchangeRatesApi.getRateByDateAndCurrency(date, currency);
  }
}