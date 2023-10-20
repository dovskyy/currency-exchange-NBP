package pl.dovskyy.spring.currencyexchangenbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dovskyy.spring.currencyexchangenbp.model.CurrencyRate;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
}
