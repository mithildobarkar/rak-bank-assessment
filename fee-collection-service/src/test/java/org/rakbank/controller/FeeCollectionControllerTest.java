package org.rakbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rakbank.model.Receipt;
import org.rakbank.service.FeeCollectionService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FeeCollectionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FeeCollectionService service;

    @Mock
    private DiscoveryClient discoveryClient;

    @Mock
    private RestClient.Builder builder;

    @InjectMocks
    private FeeCollectionController controller;

    private ObjectMapper objectMapper;

    private Receipt receipt;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        receipt = Receipt.builder()
                .receiptNumber(1L)
                .studentId("S100")
                .amountPaid(1000.0)
                .currency("AED")
                .feeType("TUITION")
                .paymentMode("CARD")
                .academicYear("2025")
                .build();
    }

    @Test
    void shouldReturnAllReceipts() throws Exception {

        when(service.getAllReceipts())
                .thenReturn(List.of(receipt));

        mockMvc.perform(get("/fees/receipts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId")
                        .value("S100"));
    }
}