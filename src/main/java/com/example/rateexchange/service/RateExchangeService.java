package com.example.rateexchange.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface RateExchangeService {
    Boolean isRateIncreased(String code) throws JsonProcessingException;
    void setUp() throws IOException;
}
