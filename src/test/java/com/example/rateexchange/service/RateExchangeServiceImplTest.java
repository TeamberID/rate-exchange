package com.example.rateexchange.service;

import com.example.rateexchange.feign.ExchangeRatesClient;
import com.example.rateexchange.util.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
class RateExchangeServiceImplTest {

    @Autowired
    private RateExchangeService service;

    @MockBean
    private JSONMapper jsonMapper;

    @MockBean
    private ExchangeRatesClient client;

    @Value("${currencyBaseCode}")
    private String baseCode;

    @Value("${appId}")
    private String appId;

    private ResponseEntity<String> responseToday = new ResponseEntity<String>("today", HttpStatus.OK);
    private ResponseEntity<String> responseYesterday = new ResponseEntity<String>("yesterday", HttpStatus.OK);
    private static String yesterdayDate;

    @BeforeAll
    static void init(){
        Instant now = Instant.now();
        Instant date = now.minus(1, ChronoUnit.DAYS);
        yesterdayDate = date.toString().substring(0, 10) + ".json";
    }

    @BeforeEach
    void setUp() throws IOException {
        when(client.getCurrenciesList()).thenReturn("mock");
        when(jsonMapper.getMap("mock")).thenReturn(getMap());
        service.setUp();
    }

    @Test
    void shouldThrowException() throws IOException {
        Assert.assertThrows(Exception.class, () -> service.isRateIncreased("FAKE"));
    }

    @Test
    void shouldReturnNull() throws IOException {
        when(client.getTodayRate("EUR", baseCode, appId)).thenReturn(responseToday);
        when(client.getYesterdayRate(yesterdayDate, "EUR", baseCode, appId)).thenReturn(responseYesterday);
        when(jsonMapper.getRateFromJSON(responseToday)).thenReturn(90.0);
        when(jsonMapper.getRateFromJSON(responseYesterday)).thenReturn(90.0);
        Assert.assertNull(service.isRateIncreased("EUR"));
    }

    @Test
    void shouldReturnCorrectBooleanStatus() throws JsonProcessingException {
        when(client.getTodayRate("EUR", baseCode, appId)).thenReturn(responseToday);
        when(client.getYesterdayRate(yesterdayDate, "EUR", baseCode, appId)).thenReturn(responseYesterday);
        when(jsonMapper.getRateFromJSON(responseToday)).thenReturn(91.0);
        when(jsonMapper.getRateFromJSON(responseYesterday)).thenReturn(90.0);
        Assert.assertEquals(service.isRateIncreased("EUR"),Boolean.TRUE);
    }

    private HashMap<String, String> getMap() {
        HashMap<String, String> currencies = new HashMap<>();
        currencies.put("USD", "United States Dollar");
        currencies.put("EUR", "Euro");
        return currencies;
    }

}