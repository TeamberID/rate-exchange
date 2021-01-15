package com.example.rateexchange.service;

import com.example.rateexchange.feign.GifClient;
import com.example.rateexchange.util.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GifServiceImpl implements GifService {

    private final GifClient client;
    private JSONMapper jsonMapper;

    @Autowired
    public void setJSONMapper(JSONMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public String getGif(Boolean isIncreased) throws JsonProcessingException {
        ResponseEntity<String> response;

        if (isIncreased) {
            response = client.getGif("rich");

        } else {
            response = client.getGif("broke");
        }

        String url = jsonMapper.getGifUrlFromJSON(response);
        return "<img src =\"" + url + "\"/>";
    }
}
