package ruslan.macari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruslan.macari.domain.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    
}
