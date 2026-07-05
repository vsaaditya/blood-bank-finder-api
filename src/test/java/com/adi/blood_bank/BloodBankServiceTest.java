package com.adi.blood_bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BloodBankServiceTest {

    @Mock
    BloodBankRepository repo;

    @InjectMocks
    BloodBankService service;

    @Test
    public void testAddBank_DuplicateName_ShouldReject() {
        // Arrange — setup fake data
        BloodBank existingBank = new BloodBank(1, "Apollo Blood Bank", "Boring Road",
                "Patna", 800001, "123456", "a@a.com", true);

        BloodBank newBank = new BloodBank(null, "Apollo Blood Bank", "New Address",
                "Patna", 800002, "987654", "b@b.com", true);

        // when repo.findByCity is called, return list with existing bank
        when(repo.findByCity("Patna")).thenReturn(List.of(existingBank));

        // Act — call the actual method
        String result = service.addBank(newBank);

        // Assert — check the result
        assertEquals("Blood Bank with this name already exists in Patna!", result);
    }
}