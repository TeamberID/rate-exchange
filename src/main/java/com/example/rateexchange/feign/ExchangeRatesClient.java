package com.example.rateexchange.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "rateExchange", url = "${exchangeRatesUrl}")
public interface ExchangeRatesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/latest.json")
    public ResponseEntity<String> getTodayRate(@RequestParam(value = "base") String currencyCode,
                                               @RequestParam(value = "symbols") String symbols,
                                               @RequestParam(value = "app_id") String appId);

    @RequestMapping(method = RequestMethod.GET, value = "historical/{date}")
    public ResponseEntity<String> getYesterdayRate(@PathVariable String date,
                                                   @RequestParam(value = "base") String currencyCode,
                                                   @RequestParam(value = "symbols") String symbols,
                                                   @RequestParam(value = "app_id") String appId
    );

    @RequestMapping(method = RequestMethod.GET, value = "/currencies.json")
    public String getCurrenciesList();
}
