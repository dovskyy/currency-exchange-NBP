package pl.dovskyy.spring.currencyexchangenbp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dovskyy.spring.currencyexchangenbp.service.CurrencyRateService;

import java.math.BigDecimal;

//TODO: add swagger

@RestController
@RequestMapping("/currency-exchange/api")
public class CurrencyRateController {

    @Autowired
    private CurrencyRateService currencyRateService;

    @GetMapping("/fetch")
    public ResponseEntity<String> fetchAndSaveCurrencyFromNbpApi() {
        currencyRateService.fetchAndSaveCurrencyData();
        return ResponseEntity.ok("Data fetched and saved.");
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
        return ResponseEntity.ok(currencyRateService.getCurrencyRateByCode(code));
    }

    //example: http://localhost:8080/currency-exchange/api/convertToPln?code=USD&amount=100
    @GetMapping("/convertToPln")
    public ResponseEntity<?> convertCurrencyToPln (@RequestParam String code, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(currencyRateService.convertCurrencyToPln(code, amount));
    }

    //example: http://localhost:8080/currency-exchange/api/convertFromPln?code=USD&amount=100
    @GetMapping("/convertFromPln")
    public ResponseEntity<?> convertFromPlnToCurrency (@RequestParam String code, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(currencyRateService.convertPlnToCurrency(code, amount));
    }

    //example: http://localhost:8080/currency-exchange/api/getTopFive
    @GetMapping("/getTopFive")
    public ResponseEntity<?> getTopFiveCurrencyRates() {
        return ResponseEntity.ok(currencyRateService.getTopFiveCurrencyRates());
    }


    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().
                body(e.getMessage()
                        .concat("\nLookup Documentation at: https://github.com/dovskyy/currency-exchange-NBP"));
    }


}
