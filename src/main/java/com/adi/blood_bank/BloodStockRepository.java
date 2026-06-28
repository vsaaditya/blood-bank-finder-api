package com.adi.blood_bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface BloodStockRepository extends JpaRepository<BloodStock, Integer> {
    List<BloodStock> findByBloodGroup(String bloodGroup);
    List<BloodStock> findByBloodBankId(Integer bloodBankId);
}
