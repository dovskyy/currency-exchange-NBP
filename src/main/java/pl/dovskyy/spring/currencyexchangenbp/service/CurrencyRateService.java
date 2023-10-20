package pl.dovskyy.spring.currencyexchangenbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dovskyy.spring.currencyexchangenbp.dto.CurrencyExchangeDTO;
import pl.dovskyy.spring.currencyexchangenbp.dto.CurrencyRateDTO;
import pl.dovskyy.spring.currencyexchangenbp.model.CurrencyRate;
import pl.dovskyy.spring.currencyexchangenbp.repository.CurrencyRateRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public void fetchAndSaveCurrencyData() {
        CurrencyExchangeDTO currencyExchangeDTO = restTemplate.getForObject(NbpApiUrls.ALL.getUrl(), CurrencyExchangeDTO.class);
        List<CurrencyRateDTO> ratesDTO = currencyExchangeDTO.rates();
        LocalDate effectiveDate = currencyExchangeDTO.effectiveDate();

        List<CurrencyRate> rates = new ArrayList<>();

    }




}
