package pl.dovskyy.spring.currencyexchangenbp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRateServiceTest {

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
    }

    @Test
    void test(){
        assertTrue(true);
    }
}