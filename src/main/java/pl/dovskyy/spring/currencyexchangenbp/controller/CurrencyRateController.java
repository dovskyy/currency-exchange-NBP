package pl.dovskyy.spring.currencyexchangenbp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dovskyy.spring.currencyexchangenbp.service.CurrencyRateService;

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
}
