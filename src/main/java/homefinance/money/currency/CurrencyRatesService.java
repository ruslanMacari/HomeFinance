package homefinance.money.currency;

import homefinance.money.currency.entity.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CurrencyRatesService {

  List<CurrencyRateModel> getCurrencyRatesByDate(LocalDate date);

  BigDecimal getRateByDateAndCurrency(LocalDate date, Currency currency);
}
