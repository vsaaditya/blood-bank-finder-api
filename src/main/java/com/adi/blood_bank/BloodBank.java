package com.adi.blood_bank;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blood_banks")
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Address cannot be empty!")
    private String address;

    @NotBlank(message = "City cannot be empty!")
    private String city;

    @NotNull(message = "Pincode cannot be null!")
    private Integer pincode;

    @NotBlank(message = "Phone cannot be empty!")
    private String phone;

    @Email(message = "Invalid email format!")
    private String email;

    private Boolean active;
}
