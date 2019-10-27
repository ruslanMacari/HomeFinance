package homefinance.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import homefinance.domain.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    
}
