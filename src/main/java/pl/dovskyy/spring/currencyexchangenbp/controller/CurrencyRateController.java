package pl.dovskyy.spring.currencyexchangenbp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dovskyy.spring.currencyexchangenbp.model.CurrencyRate;
import pl.dovskyy.spring.currencyexchangenbp.service.CurrencyRateService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency-exchange/api")
public class CurrencyRateController {

    @Autowired
    private CurrencyRateService currencyRateService;

    @GetMapping("/fetch")
    public ResponseEntity<String> fetchAndSaveCurrencyFromNbpApi() {
        return currencyRateService.fetchAndSaveCurrencyData();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCurrencyRates() {
        return ResponseEntity.ok(currencyRateService.getAllCurrencyRates());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAllCurrencyRates() {
        currencyRateService.deleteAllCurrencyRates();
        return ResponseEntity.ok("All data deleted.");
    }

    //example: http://localhost:8080/currency-exchange/api/getRate?code=USD
    @GetMapping("/getRate")
    public ResponseEntity<?> getCurrencyRateByCode(@RequestParam String code) {
        CurrencyRate currencyRate = currencyRateService.getCurrencyRateByCode(code);
        if (currencyRate == null) {
            return new ResponseEntity<>("Currency code not found: " + code + "\nTry calling /all request to lookup available codes", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(currencyRate);
        }
    }

    //example: http://localhost:8080/currency-exchange/api/convertToPln?code=USD&amount=100
    @GetMapping("/convertToPln")
    public ResponseEntity<?> convertCurrencyToPln(@RequestParam String code, @RequestParam BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity<>("Amount must be positive", HttpStatus.BAD_REQUEST);
        }
        BigDecimal result = currencyRateService.convertCurrencyToPln(code, amount);
        if (result == null) {
            return new ResponseEntity<>("Currency code not found: " + code + "\nTry calling /all request to lookup available codes", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    //example: http://localhost:8080/currency-exchange/api/convertFromPln?code=USD&amount=100
    @GetMapping("/convertFromPln")
    public ResponseEntity<?> convertFromPlnToCurrency(@RequestParam String code, @RequestParam BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity<>("Amount must be positive", HttpStatus.BAD_REQUEST);
        }
        BigDecimal result = currencyRateService.convertPlnToCurrency(code, amount);
        if (result == null) {
            return new ResponseEntity<>("Currency code not found: " + code + "\nTry calling /all request to lookup available codes.", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(result);
        }
    }


    @GetMapping("/getTopFive")
    public ResponseEntity<?> getTopFiveCurrencyRates() {
        if (currencyRateService.getTopFiveCurrencyRates() == null) {
            return new ResponseEntity<>("No data available. Try calling /fetch request to fetch data from NBP API.", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(currencyRateService.getTopFiveCurrencyRates());
        }
    }

    @GetMapping("/getEffectiveDate")
    public ResponseEntity<?> getLatestEffectiveDate() {
        if (currencyRateService.getLatestEffectiveDate() == null) {
            return new ResponseEntity<>("No data available. Try calling /fetch request to fetch data from NBP API.", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(currencyRateService.getLatestEffectiveDate());
        }
    }


    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().
                body(e.getMessage()
                        .concat("\nLookup Documentation at: https://github.com/dovskyy/currency-exchange-NBP"));
    }

}
