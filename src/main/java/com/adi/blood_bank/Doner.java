package com.adi.blood_bank;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    private Integer age;
    private String gender ;
    private String bloodGroup;

    @NotBlank(message = "Phone cannot be empty!")
    private String phone;

    @Email(message = "Invalid email format!")
    private String email;

    private LocalDate lastDonationDate;
    private Integer bloodBankId;
}
