package homefinance.money.currency;

import homefinance.money.currency.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
  Currency findByCode(String code);
}
