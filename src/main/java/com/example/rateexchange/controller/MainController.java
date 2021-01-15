package com.example.rateexchange.controller;

import com.example.rateexchange.service.GifService;
import com.example.rateexchange.service.RateExchangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private RateExchangeService rateExchangeService;
    private GifService gifService;

    @Autowired
    public void setGifService(GifService gifService) {
        this.gifService = gifService;
    }

    @Autowired
    public void setRateExchangeService(RateExchangeService rateExchangeService) {
        this.rateExchangeService = rateExchangeService;
    }

    @GetMapping("/status/{code}")
    public String getRateStatus(@PathVariable String code) throws JsonProcessingException {
        Boolean isIncreased = rateExchangeService.isRateIncreased(code);
        if (isIncreased == null) {
            return "The exchange rate has not changed";
        }
        return gifService.getGif(isIncreased);
    }
}
