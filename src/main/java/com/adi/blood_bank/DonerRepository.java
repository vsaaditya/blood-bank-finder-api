package com.adi.blood_bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonerRepository extends JpaRepository<Doner, Integer> {
    List<Doner> findByBloodGroup(String bloodGroup);
    List<Doner> findByBloodBankId(Integer bloodBankId);
    //Optional<Doner> findByPhone(String phone);
    Optional<Doner> findByNameAndPhoneAndBloodGroup(String name, String phone, String bloodGroup);
}
