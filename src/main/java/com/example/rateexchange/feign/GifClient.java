package com.example.rateexchange.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gif", url = "${gifUrl}")
public interface GifClient {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getGif(@RequestParam(value = "tag") String tag);

}