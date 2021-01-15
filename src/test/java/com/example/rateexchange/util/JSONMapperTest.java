package com.example.rateexchange.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;

class JSONMapperTest {

    private JSONMapper jsonMapper = new JSONMapper();

    private String jsonExample = "{\n" +
            "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
            "  \"license\": \"https://openexchangerates.org/license\",\n" +
            "  \"timestamp\": 1610488800,\n" +
            "  \"base\": \"EUR\",\n" +
            "  \"rates\": {\n" +
            "    \"RUB\": 89.837944\n" +
            "  }\n" +
            "}";

    @Test
    void shouldReturnCorrectRateFromJson() throws JsonProcessingException {
        Double expected = 89.837944;
        Double result = jsonMapper.getRateFromJSON(new ResponseEntity<String>(jsonExample, HttpStatus.OK));
        Assert.assertEquals(expected, result);
    }

    @Test
    void shouldReturnMap() throws IOException {

        HashMap<String, String> result = jsonMapper.getMap("{\"USD\":\"United States Dollar\", \"EUR\":\"Euro\"}");

        HashMap<String, String> expected = new HashMap();
        expected.put("USD", "United States Dollar");
        expected.put("EUR", "Euro");

        Assert.assertEquals(result, expected);
    }
}