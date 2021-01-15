package com.example.rateexchange.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GifService {
    public String getGif(Boolean isIncreased) throws JsonProcessingException;
}
