package com.adi.blood_bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BloodBankRepository extends JpaRepository<BloodBank,Integer >{

    List<BloodBank> findByCity(String city);

    List<BloodBank> findByCityAndActive(String city,Boolean active);

    List<BloodBank> findByNameContaining(String keyword);


    @Query("SELECT COUNT(b) FROM BloodBank b WHERE b.city = :city")
    Long countBanksByCity(@Param("city") String city);

}

