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

        //get list of CurrencyExchangeDTO from NBP API, the API returns an array of CurrencyExchangeDTO, but we need only one element of currencyExchangeDTO and then extract currencyRateDTO from it.
        CurrencyExchangeDTO[] currencyExchangeDTO = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/tables/a/", CurrencyExchangeDTO[].class);
        List<CurrencyRateDTO> ratesDTO = currencyExchangeDTO[0].rates();
        LocalDate effectiveDate = currencyExchangeDTO[0].effectiveDate();


        List<CurrencyRate> rates = new ArrayList<>();

        for (CurrencyRateDTO currencyRateDTO : ratesDTO) {
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setEffectiveDate(effectiveDate);
            currencyRate.setCode(currencyRateDTO.code());
            currencyRate.setRate(currencyRateDTO.mid());
            currencyRate.setCurrencyName(currencyRateDTO.currency());
            rates.add(currencyRate);
        }

        currencyRateRepository.saveAll(rates);
    }




}
