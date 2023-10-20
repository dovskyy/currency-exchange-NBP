package pl.dovskyy.spring.currencyexchangenbp.dto;

import java.time.LocalDate;
import java.util.List;

public record CurrencyExchangeDTO(String table, String no, LocalDate effectiveDate, List<CurrencyRateDTO> rates) {

}
