package homefinance.money.currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CurrencyRatesService {

  List<CurrencyRate> getCurrencyRatesByDate(LocalDate date);

  BigDecimal getRateByDateAndCurrency(LocalDate date, Currency currency);
}
