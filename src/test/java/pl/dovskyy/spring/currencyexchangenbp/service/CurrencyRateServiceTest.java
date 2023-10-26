package pl.dovskyy.spring.currencyexchangenbp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import pl.dovskyy.spring.currencyexchangenbp.model.CurrencyRate;
import pl.dovskyy.spring.currencyexchangenbp.repository.CurrencyRateRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public void testGetLatestEffectiveDateReturnsNull() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setEffectiveDate(null);

        //when
        when(currencyRateRepository.findTopByOrderByEffectiveDateDesc()).thenReturn(currencyRate);
        LocalDate result = currencyRateService.getLatestEffectiveDate();

        //then
        assertNull(result);
    }

    @Test
    public void testGetLatestEffectiveDateReturnsDate() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setEffectiveDate(LocalDate.now());

        //when
        when(currencyRateRepository.findTopByOrderByEffectiveDateDesc()).thenReturn(currencyRate);
        when(currencyRateRepository.count()).thenReturn(1L);
        LocalDate result = currencyRateService.getLatestEffectiveDate();

        //then
        assertNotNull(result);
        assertEquals(result, LocalDate.now());
    }

    @Test
    public void testGetCurrencyRateByCodeReturnsValue() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode("USD");

        //when
        when(currencyRateRepository.findByCode("USD")).thenReturn(currencyRate);
        CurrencyRate result = currencyRateService.getCurrencyRateByCode("USD");

        //then
        assertNotNull(result);
        assertEquals(result.getCode(), "USD");
    }

    @Test
    public void testGetCurrencyRateByCodeReturnsNull() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode("USD");

        //when
        when(currencyRateRepository.findByCode("USD")).thenReturn(currencyRate);
        CurrencyRate result = currencyRateService.getCurrencyRateByCode("EUR");

        //then
        assertNull(result);
    }

    @Test
    public void testDeleteCurrencyRateByCodeReturnsTrue() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode("USD");

        //when
        when(currencyRateRepository.findByCode("USD")).thenReturn(currencyRate);
        boolean result = currencyRateService.deleteCurrencyRateByCode("USD");

        //then
        assertTrue(result);
    }

    @Test
    public void testDeleteCurrencyRateByCodeReturnsFalse() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode("USD");

        //when
        when(currencyRateRepository.findByCode("USD")).thenReturn(currencyRate);
        boolean result = currencyRateService.deleteCurrencyRateByCode("EUR");

        //then
        assertFalse(result);
    }

    @Test
    public void testDeleteAllCurrencyRates() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode("USD");

        //when
        currencyRateService.deleteAllCurrencyRates();

        //then
        verify(currencyRateRepository, times(1)).deleteAll();
    }

    @Test
    public void testConvertCurrencyToPlnReturnsValue() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode("USD");
        currencyRate.setRate(new BigDecimal("3.7890"));

        //when
        when(currencyRateRepository.findByCode("USD")).thenReturn(currencyRate);
        BigDecimal result = currencyRateService.convertCurrencyToPln("USD", new BigDecimal("100"));

        //then
        assertNotNull(result);
        assertEquals(0, result.compareTo(new BigDecimal("378.90"))); //compareTo() returns 0 if values are equal, 1 if first value is greater, -1 if first value is smaller

        //I couldn't use assertEquals(result, new BigDecimal("378.90")) here because it didn't seem to work with BigDecimal values. Actual value was 378.90, expected was 378.9000 (lol?) and test failed.
    }

    @Test
    public void testConvertCurrencyToPlnReturnsNull() {

        //given
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode("USD");
        currencyRate.setRate(new BigDecimal("3.7890"));

        //when
        when(currencyRateRepository.findByCode("USD")).thenReturn(currencyRate);
        BigDecimal result = currencyRateService.convertCurrencyToPln("EUR", new BigDecimal("100")); //EUR is not in the database

        //then
        assertNull(result);
    }

}