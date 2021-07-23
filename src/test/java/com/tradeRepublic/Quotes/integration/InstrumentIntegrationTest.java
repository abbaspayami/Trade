package com.tradeRepublic.Quotes.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeRepublic.Quotes.common.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration Test
 *
 * @author Abbas
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class InstrumentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void status_GetInstrumentPrice_Test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(TestUtils.BASE_URL + TestUtils.INSTRUMENT_PRICE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void status_GetInstrumentPriceHistory_Test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(TestUtils.BASE_URL + TestUtils.INSTRUMENT_PRICE_HISTORY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

}
