package com.example.rateexchange.service;

import com.example.rateexchange.feign.ExchangeRatesClient;
import com.example.rateexchange.util.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RateExchangeServiceImpl implements RateExchangeService {

    private final ExchangeRatesClient client;
    private JSONMapper jsonMapper;
    private Set<String> currencies;

    @Value("${currencyBaseCode}")
    private String baseCode;

    @Value("${appId}")
    private String appId;

    @Autowired
    public void setJSONMapper(JSONMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @PostConstruct
    public void setUp() throws IOException {
        String response = client.getCurrenciesList();
        HashMap mapOfCurrencies = jsonMapper.getMap(response);
        currencies = mapOfCurrencies.keySet();
    }

    @Override
    public Boolean isRateIncreased(String code) throws JsonProcessingException {
       if (!currencies.contains(code.toUpperCase())) {
            throw new IllegalArgumentException("Incorrect currency code");
        }

        ResponseEntity<String> response = client.getTodayRate(code, baseCode, appId);
        Double todayRate = jsonMapper.getRateFromJSON(response);

        String yesterdayDate = getYesterdayDate();
        response = client.getYesterdayRate(yesterdayDate, code, baseCode, appId);
        Double yesterdayRate = jsonMapper.getRateFromJSON(response);

        if ((todayRate - yesterdayRate) > 0) {
            return true;
        } else if ((todayRate - yesterdayRate) < 0) {
            return false;
        } else {
            return null;
        }
    }

    private String getYesterdayDate() {
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        return yesterday.toString().substring(0, 10)+ ".json";
    }


}
