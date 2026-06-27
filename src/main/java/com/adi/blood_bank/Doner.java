package com.adi.blood_bank;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doner")
public class Doner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer age;
    private String gender ;
    private String bloodGroup;
    private String phone;
    private String email;
    private LocalDate lastDonationDate;
    private Integer bloodBankId;
}
