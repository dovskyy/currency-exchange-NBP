package pl.dovskyy.spring.currencyexchangenbp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import pl.dovskyy.spring.currencyexchangenbp.model.CurrencyRate;
import pl.dovskyy.spring.currencyexchangenbp.repository.CurrencyRateRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyRateServiceTest {

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private CurrencyRateRepository currencyRateRepository;


    @Test
    public void testGetAllCurrencyRates() {

        //given
        int randomInt = (int) (Math.random() * 100);
        List<CurrencyRate> currencyRates = new ArrayList<>();


        for (int i = 0; i < randomInt; i++) {
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRates.add(currencyRate);
        }

        //when
        when(currencyRateRepository.findAll()).thenReturn(currencyRates);
        List<CurrencyRate> result = currencyRateService.getAllCurrencyRates();

        //then
        assertEquals(result.size(), randomInt);
        verify(currencyRateRepository, times(1)).findAll();
    }

    @Test
    public void testFetchAndSaveCurrencyData() {

        //given
        CurrencyRate[] currencyRates = new CurrencyRate[1];
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode("USD");
        currencyRate.setCurrencyName("dolar amerykański");
        currencyRate.setRate(new BigDecimal("3.7890"));
        currencyRates[0] = currencyRate;


    }

    @Test
    public void testDeleteAllCurrencyRates() {



    }

}