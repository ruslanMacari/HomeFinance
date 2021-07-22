package homefinance.money.currency;

import homefinance.money.currency.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Integer> {

}
