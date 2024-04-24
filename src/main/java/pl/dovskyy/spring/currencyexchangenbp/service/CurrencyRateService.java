package pl.dovskyy.spring.currencyexchangenbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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
 * Service class for CurrencyRate
 */

//TODO: add logger
//TODO: store historical data in database

@Service
public class CurrencyRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;




    public ResponseEntity<String> fetchAndSaveCurrencyData() {

        try {
            CurrencyExchangeDTO[] currencyExchangeDTO = restTemplate.getForObject(NbpApiUrls.ALL.getUrl(), CurrencyExchangeDTO[].class);
            List<CurrencyRateDTO> ratesDTO = currencyExchangeDTO[0].rates();
            LocalDate effectiveDate = currencyExchangeDTO[0].effectiveDate();

            if (effectiveDate.equals(getLatestEffectiveDate())) {
                return new ResponseEntity<>("Currency data in the database is already up to date", HttpStatus.OK);
            }

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

        } catch(RestClientException e){
            return new ResponseEntity<>("Error during fetching data from NBP API", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Data fetched and updated successfully", HttpStatus.OK);

    }

    public LocalDate getLatestEffectiveDate() {
        if (currencyRateRepository.count() == 0) {
            return null;
        }
        return currencyRateRepository.findTopByOrderByEffectiveDateDesc().getEffectiveDate();
    }


    public List<CurrencyRate> getAllCurrencyRates() {
        return currencyRateRepository.findAll();
    }

    //gets the latest currency rate by code, gets the rate with the latest effective date
    public CurrencyRate getCurrencyRateByCode(String code) {
        List<CurrencyRate> currencyRates = currencyRateRepository.findByCodeOrderByEffectiveDateDesc(code);
        if (currencyRates.isEmpty()) {
            return null;
        } else {
            return currencyRates.get(0);
            //returns the latest currency rate on the index 0, the list is sorted by effective date descending
        }
    }

    public boolean deleteCurrencyRateByCode(String code) {
        CurrencyRate currencyRate = currencyRateRepository.findByCode(code);
        if (currencyRate == null) {
            return false;
        }
        currencyRateRepository.delete(currencyRate);
        return true;
    }

    public void deleteAllCurrencyRates() {
        currencyRateRepository.deleteAll();
    }


    public BigDecimal convertCurrencyToPln(String code, BigDecimal amount) {
        CurrencyRate currencyRate = currencyRateRepository.findByCode(code);
        if (currencyRate == null) {
            return null;
        }
        BigDecimal rate = currencyRate.getRate();
        return rate.multiply(amount);
    }

    public BigDecimal convertPlnToCurrency(String code, BigDecimal amount) {
        BigDecimal rate = currencyRateRepository.findByCode(code).getRate();
        BigDecimal result = amount.divide(rate, 2, RoundingMode.DOWN); //rounding down to 2 decimal places
        return result;
    }

    public List<CurrencyRate> getTopFiveCurrencyRates() {
        if (currencyRateRepository.count() == 0) {
            return null;
        } else {
            return currencyRateRepository.findTop5ByOrderByRateDesc();
        }
    }

    public List<CurrencyRate> getBottomFiveCurrencyRates() {
        if (currencyRateRepository.count() == 0) {
            return null;
        } else {
            return currencyRateRepository.findTop5ByOrderByRateAsc();
        }
    }

    public BigDecimal getAverageRateOfCurrencyLastFiveDays(String code) {
        if (currencyRateRepository.count() == 0) {
            return null;
        } else {
            List<CurrencyRate> currencyRates = currencyRateRepository.findTop5ByCodeOrderByEffectiveDateDesc(code);
            BigDecimal sum = new BigDecimal("0");
            for (CurrencyRate currencyRate : currencyRates) {
                sum = sum.add(currencyRate.getRate());
            }
            return sum.divide(new BigDecimal(currencyRates.size()), RoundingMode.DOWN);
        }
    }
}
