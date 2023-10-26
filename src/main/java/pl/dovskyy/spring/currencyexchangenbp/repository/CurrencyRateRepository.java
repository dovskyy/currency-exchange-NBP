package pl.dovskyy.spring.currencyexchangenbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dovskyy.spring.currencyexchangenbp.model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    CurrencyRate findByCode(String code);

    void deleteByCode(String code);

    List<CurrencyRate> findTop5ByOrderByRateDesc();

    CurrencyRate findTopByOrderByEffectiveDateDesc();
}
