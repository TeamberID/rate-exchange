package com.example.rateexchange.service;

import com.example.rateexchange.feign.GifClient;
import com.example.rateexchange.util.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
class GifServiceImplTest {

    @Autowired
    private GifService service;

    @MockBean
    private JSONMapper jsonMapper;

    @MockBean
    private GifClient client;

    @Test
    public void shouldReturnCorrectGifUrl() throws JsonProcessingException {
        when(client.getGif("rich")).thenReturn(new ResponseEntity<String>("url",HttpStatus.OK));
        when(jsonMapper.getGifUrlFromJSON(new ResponseEntity<String>("url", HttpStatus.OK))).thenReturn("gif");
        String result = service.getGif(Boolean.TRUE);
        Assert.assertEquals("<img src =\"gif\"/>", result);
    }

}