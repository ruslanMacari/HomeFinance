package homefinance.money.currency;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRatesService {
  List<CurrencyRates> getCurrencyRatesByDate(LocalDate date);
}
