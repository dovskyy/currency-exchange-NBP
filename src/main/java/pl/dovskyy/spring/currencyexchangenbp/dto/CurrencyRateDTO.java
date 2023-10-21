package pl.dovskyy.spring.currencyexchangenbp.dto;

import java.math.BigDecimal;

public record CurrencyRateDTO(String currency, String code, BigDecimal mid) {
}
