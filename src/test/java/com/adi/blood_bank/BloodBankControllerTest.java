package com.adi.blood_bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BloodBankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllBanks_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/bloodbank/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddBank_WithEmptyName_ShouldReturn400() throws Exception {
        String invalidJson = """
                {
                    "name": "",
                    "city": "Patna",
                    "address": "Test",
                    "pincode": 800001,
                    "phone": "123456",
                    "email": "test@test.com",
                    "active": true
                }
                """;

        mockMvc.perform(post("/api/bloodbank/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}