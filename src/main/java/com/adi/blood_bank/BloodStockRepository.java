package com.adi.blood_bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface BloodStockRepository extends JpaRepository<BloodStock, Integer> {
    List<BloodStock> findByBloodBankId(Integer bloodBankId);

    List<BloodStock> findByBloodGroup(String bloodGroup);

    List<BloodStock> findByUnitsLessThan(Integer units);

    List<BloodStock> findByUnitsBetween(Integer min, Integer max);

    @Query("SELECT SUM(s.units) FROM BloodStock s WHERE s.bloodGroup = :bloodGroup")
    Long totalUnitsByBloodGroup(@Param("bloodGroup") String bloodGroup);
}
