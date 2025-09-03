package com.example.payment.controller;

import com.example.payment.dto.PaymentDtos;
import com.example.payment.security.JwtAuthenticationFilter;
import com.example.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean PaymentService paymentService;
    @MockBean JwtAuthenticationFilter jwtAuthenticationFilter; // not used due to addFilters=false

    @Test
    void purchase_endpoint_returns_ok() throws Exception {
        when(paymentService.purchase(any())).thenReturn(new PaymentDtos.PurchaseResponse("o1", "t1", "CAPTURED"));

        PaymentDtos.PurchaseRequest req = new PaymentDtos.PurchaseRequest("ext-1", "a@b.com", 1000, "USD", "desc", "4111111111111111", "2030-12", "123");
        mockMvc.perform(post("/payments/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
}

