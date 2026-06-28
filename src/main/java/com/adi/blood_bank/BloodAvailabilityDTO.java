package com.adi.blood_bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodAvailabilityDTO {
    private String bloodBankName;
    private String city;
    private String phone;
    private String bloodGroup;
    private Integer units;
}