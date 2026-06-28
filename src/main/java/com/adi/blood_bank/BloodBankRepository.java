package com.adi.blood_bank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BloodBankRepository extends JpaRepository<BloodBank,Integer >{

    List<BloodBank> findByCity(String city);
    List<BloodBank> findByName(String name);

}
