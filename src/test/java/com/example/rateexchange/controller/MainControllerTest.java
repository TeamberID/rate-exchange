package com.example.rateexchange.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.rateexchange.service.GifService;
import com.example.rateexchange.service.RateExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
@AutoConfigureMockMvc
class MainControllerTest {

    @MockBean
    private RateExchangeService rateExchangeService;

    @MockBean
    private GifService gifService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnDefaultUrl() throws Exception {
        when(rateExchangeService.isRateIncreased("USD")).thenReturn(Boolean.TRUE);
        when(gifService.getGif(Boolean.TRUE)).thenReturn("default_url");
        this.mockMvc.perform(get("/status/USD")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("default_url")));
    }

    @Test
    void shouldReturnMessage() throws Exception {
        when(rateExchangeService.isRateIncreased("RUB")).thenReturn(null);
        this.mockMvc.perform(get("/status/RUB")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("The exchange rate has not changed")));
    }
}