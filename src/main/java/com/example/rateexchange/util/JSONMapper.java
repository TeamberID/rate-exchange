package com.example.rateexchange.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JSONMapper {

    ObjectMapper mapper = new ObjectMapper();

    public Double getRateFromJSON(ResponseEntity<String> response) throws JsonProcessingException {
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode todayRateNode = root.path("rates").path("RUB");
        return  todayRateNode.asDouble();

    }
    public String getGifUrlFromJSON(ResponseEntity<String> response) throws JsonProcessingException {
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode url = root.path("data").path("images").path("downsized_large").path("url");
        return url.asText();
    }

    public HashMap getMap(String response) throws IOException {
        return mapper.readValue(response, HashMap.class);
    }
}
