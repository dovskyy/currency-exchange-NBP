package pl.dovskyy.spring.currencyexchangenbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dovskyy.spring.currencyexchangenbp.dto.CurrencyExchangeDTO;
import pl.dovskyy.spring.currencyexchangenbp.dto.CurrencyRateDTO;
import pl.dovskyy.spring.currencyexchangenbp.model.CurrencyRate;
import pl.dovskyy.spring.currencyexchangenbp.repository.CurrencyRateRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for fetching and saving currency data from NBP API
 */

@Service
public class CurrencyRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public void fetchAndSaveCurrencyData() {

        //get list of CurrencyExchangeDTO from NBP API, the API returns an array of CurrencyExchangeDTO, but we need only one element of currencyExchangeDTO and then extract currencyRateDTO from it.
        CurrencyExchangeDTO[] currencyExchangeDTO = restTemplate.getForObject(NbpApiUrls.ALL.getUrl(), CurrencyExchangeDTO[].class);
        List<CurrencyRateDTO> ratesDTO = currencyExchangeDTO[0].rates();
        LocalDate effectiveDate = currencyExchangeDTO[0].effectiveDate();

        //create list of CurrencyRate from list of CurrencyRateDTO
        List<CurrencyRate> rates = new ArrayList<>();

        //delete all existing CurrencyRate from database
        if (!ratesDTO.isEmpty()) currencyRateRepository.deleteAll();

        for (CurrencyRateDTO currencyRateDTO : ratesDTO) {
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setEffectiveDate(effectiveDate);
            currencyRate.setCode(currencyRateDTO.code());
            currencyRate.setRate(currencyRateDTO.mid());
            currencyRate.setCurrencyName(currencyRateDTO.currency());
            rates.add(currencyRate);
        }

        //save updated list of CurrencyRate to database
        currencyRateRepository.saveAll(rates);
    }

    public List<CurrencyRate> getAllCurrencyRates() {
        return currencyRateRepository.findAll();
    }

    public CurrencyRate getCurrencyRateByCode(String code) {
        return currencyRateRepository.findByCode(code);
    }

    public void deleteCurrencyRateByCode(String code) {
        currencyRateRepository.deleteByCode(code);
    }

    public void deleteAllCurrencyRates() {
        currencyRateRepository.deleteAll();
    }


    public BigDecimal convertCurrencyToPln(String code, BigDecimal amount) {
        BigDecimal rate = currencyRateRepository.findByCode(code).getRate();
        return rate.multiply(amount);
    }

    public BigDecimal convertPlnToCurrency(String code, BigDecimal amount) {
        BigDecimal rate = currencyRateRepository.findByCode(code).getRate();
        BigDecimal result = amount.divide(rate, 2, RoundingMode.DOWN); //rounding down to 2 decimal places
        return result;
    }

    public List<CurrencyRate> getTopFiveCurrencyRates() {
        return currencyRateRepository.findTop5ByOrderByRateDesc();
    }
}
